package com.pgu.calendar.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;
import com.pgu.calendar.shared.AppConstants;
import com.pgu.calendar.shared.Pill;

@SuppressWarnings("serial")
public class CheckPillServlet extends HttpServlet {

    private static final String PILL_TYPE_MORNING = "morning";
    private static final String PILL_TYPE_EVENING = "evening";

    private static final Logger LOGGER = Logger.getLogger(CheckPillServlet.class.getName());

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {

        LOGGER.info("...GET request ");
        doPost(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {
        final String paramType = req.getParameter("type");

        LOGGER.info("type " + paramType);

        boolean isMorning = false;
        if (PILL_TYPE_MORNING.equalsIgnoreCase(paramType)) {
            isMorning = true;

        } else if (PILL_TYPE_EVENING.equalsIgnoreCase(paramType)) {
            isMorning = false;

        } else {
            resp.setContentType("text/plain");
            resp.getWriter().println("No valid type...");
            return;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        final Date day = new Date();

        final Objectify ofy = ObjectifyDao.ofy();
        final List<Pill> pills = ofy.query(Pill.class).filter("day", sdf.format(day)).list();

        Pill pillToCheck = null;
        for (final Pill pill : pills) {
            if (isMorning && pill.isMorning()) {
                pillToCheck = pill;
                break;
            }
            if (!isMorning && !pill.isMorning()) {
                pillToCheck = pill;
                break;
            }
        }

        if (null == pillToCheck) {
            LOGGER.info("An alert must be sent");
            // http://code.google.com/appengine/docs/java/mail/overview.html
            sendMailForPill(isMorning);
        }
    }

    private void sendMailForPill(final boolean isMorning) {
        final Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props);

        final Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(""));
        } catch (final AddressException e) {
            LOGGER.severe(e.getMessage());
        } catch (final MessagingException e) {
            LOGGER.severe(e.getMessage());
        }

        try {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("", "Eemm"));
        } catch (final UnsupportedEncodingException e) {
            LOGGER.severe(e.getMessage());
        } catch (final MessagingException e) {
            LOGGER.severe(e.getMessage());
        }
        final String msgBody = "You need to take you pill of the " + (isMorning ? "morning" : "afternoon");
    }

}

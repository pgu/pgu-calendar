package com.pgu.calendar.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.pgu.calendar.client.PillService;
import com.pgu.calendar.shared.Pill;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PillServiceImpl extends RemoteServiceServlet implements PillService {

    @Override
    public ArrayList<Pill> getPills(final String day) {

        final Objectify ofy = ObjectifyDao.ofy();
        final List<Pill> pills = ofy.query(Pill.class).filter("day", day).list();

        final ArrayList<Pill> result = new ArrayList<Pill>(pills.size());
        result.addAll(pills);
        return result;
    }

    @Override
    public void save(final Pill pill) {
        final Objectify ofy = ObjectifyDao.ofy();
        ofy.put(pill);
    }

}

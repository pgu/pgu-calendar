package com.pgu.calendar.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.pgu.calendar.shared.AppConstants;
import com.pgu.calendar.shared.Pill;

public class Pgu_calendar implements EntryPoint {

    private final PillServiceAsync pillService = GWT.create(PillService.class);

    private final DateTimeFormat dtfTime = DateTimeFormat.getFormat(AppConstants.TIME_FORMAT);
    private final DateTimeFormat dtfDay = DateTimeFormat.getFormat(AppConstants.DATE_FORMAT);
    private final DatePicker datePicker = new DatePicker();
    private final CheckBox cbPillMorning = new CheckBox("Morning pill");
    private final CheckBox cbPillEvening = new CheckBox("Evening pill");
    private final Label timeMorning = new Label();
    private final Label timeEvening = new Label();

    @Override
    public void onModuleLoad() {

        RootPanel.get().add(datePicker);

        final VerticalPanel vp = new VerticalPanel();
        addRow(vp, cbPillMorning, timeMorning);
        addRow(vp, cbPillEvening, timeEvening);

        RootPanel.get().add(vp);

        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {

            @Override
            public void onValueChange(final ValueChangeEvent<Date> event) {
                getPills(event.getValue());
            }
        });

        datePicker.setValue(new Date(), true);
        addClickPill(cbPillMorning, true, timeMorning);
        addClickPill(cbPillEvening, false, timeEvening);
    }

    private void addRow(final VerticalPanel vp, final CheckBox cb, final Label label) {
        label.getElement().getStyle().setMarginLeft(20, Unit.PX);
        label.getElement().getStyle().setMarginTop(3, Unit.PX);

        final HorizontalPanel hp = new HorizontalPanel();
        hp.getElement().getStyle().setMarginTop(20, Unit.PX);
        hp.add(cb);
        hp.add(label);
        vp.add(hp);
    }

    private void addClickPill(final CheckBox checkbox, final boolean isMorning, final Label time) {
        checkbox.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {

                checkbox.setEnabled(false);

                final Date day = new Date();

                final Pill pill = new Pill();
                pill.setMorning(isMorning);
                pill.setDay(dtfDay.format(day));
                pill.setTime(dtfTime.format(day));

                pillService.save(pill, new AsyncCallbackApp<Void>() {

                    @Override
                    public void onSuccess(final Void result) {
                        checkbox.setEnabled(false);
                        time.setText(pill.getTime());
                    }

                    @Override
                    public void onFailure(final Throwable caught) {
                        checkbox.setEnabled(true);
                        super.onFailure(caught);
                    }

                });
            }
        });
    }

    private void getPills(final Date day) {
        final String todayFmt = dtfDay.format(new Date());
        final String dayFmt = dtfDay.format(day);

        final boolean isToday = todayFmt.equals(dayFmt);
        cbPillMorning.setEnabled(isToday);
        cbPillEvening.setEnabled(isToday);

        pillService.getPills(dayFmt, new AsyncCallbackApp<ArrayList<Pill>>() {

            @Override
            public void onSuccess(final ArrayList<Pill> pills) {

                cbPillMorning.setValue(false, false);
                cbPillEvening.setValue(false, false);
                timeMorning.setText("");
                timeEvening.setText("");

                for (final Pill pill : pills) {
                    if (pill.isMorning()) {
                        updateRow(pill, cbPillMorning, timeMorning);
                    } else {
                        updateRow(pill, cbPillEvening, timeEvening);
                    }
                }
            }

            private void updateRow(final Pill pill, final CheckBox cb, final Label time) {
                cb.setEnabled(false);
                cb.setValue(true, false);
                time.setText(pill.getTime());
            }

        });
    }

}

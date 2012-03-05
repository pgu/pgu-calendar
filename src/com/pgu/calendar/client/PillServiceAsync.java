package com.pgu.calendar.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pgu.calendar.shared.Pill;

public interface PillServiceAsync {

    void getPills(String day, AsyncCallback<ArrayList<Pill>> callback);

    void save(Pill pill, AsyncCallback<Void> asyncCallbackApp);

}

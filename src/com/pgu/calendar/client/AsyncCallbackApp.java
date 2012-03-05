package com.pgu.calendar.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackApp<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(final Throwable caught) {
        Window.alert("Arf! something went wrong :-(");
        GWT.log(caught.getMessage());
    }

}

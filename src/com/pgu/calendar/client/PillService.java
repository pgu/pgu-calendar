package com.pgu.calendar.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pgu.calendar.shared.Pill;

@RemoteServiceRelativePath("pill")
public interface PillService extends RemoteService {

    ArrayList<Pill> getPills(String day);

    void save(Pill pill);

}

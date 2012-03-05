package com.pgu.calendar.server;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.pgu.calendar.shared.Pill;

public class ObjectifyDao {
    static {
        ObjectifyService.register(Pill.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.begin();
    }

}

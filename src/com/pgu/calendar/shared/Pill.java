package com.pgu.calendar.shared;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Pill implements IsSerializable {

    @Id
    private Long id;

    private String day;
    private boolean isMorning;
    private String time;

    public Pill() {
        super();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pill other = (Pill) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pill [id=" + id + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(final boolean isMorning) {
        this.isMorning = isMorning;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

}

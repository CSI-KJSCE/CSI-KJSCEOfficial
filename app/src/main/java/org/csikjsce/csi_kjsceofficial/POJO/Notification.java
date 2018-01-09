package org.csikjsce.csi_kjsceofficial.POJO;

import org.csikjsce.csi_kjsceofficial.Utils;

/**
 * Created by sziraqui on 22/12/17.
 */

public class Notification {
    private int id;
    private String title, description, time;
    private String extraUrl;
    private int type, isRead, eventId;
    public static final int GENERAL_TYPE = 0;
    public static final int WORKSHOP_TYPE = 1;
    public static final int SEMINAR_TYPE = 2;
    public static final int IS_READ = 0, NOT_READ = 1;

    public Notification(){
        id = -1;
        type = GENERAL_TYPE;
    }

    public Notification(int id, String time, String title, String description, String extraUrl, int type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.extraUrl = extraUrl;
        this.type = type;
        isRead = NOT_READ;
    }

    public Notification(int id, String time, String title, String description, String extraUrl, int type, int eventId, int isRead) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.extraUrl = extraUrl;
        this.type = type;
        this.isRead = isRead;
        this.eventId = eventId;
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return Utils.strToDate(time);
    }

    public String getExtraUrl() {
        return extraUrl;
    }

    public int getType() {
        return type;
    }

    public int isRead() { return isRead; }

    public int getEventId() { return eventId; }

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof  Notification){
            Notification n = (Notification)obj;
            if(n.getId() == this.getId())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}

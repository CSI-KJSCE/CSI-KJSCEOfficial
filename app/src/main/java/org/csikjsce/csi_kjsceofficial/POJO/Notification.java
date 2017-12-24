package org.csikjsce.csi_kjsceofficial.POJO;

/**
 * Created by sziraqui on 22/12/17.
 */

public class Notification {
    private int id;
    private String title, description, time;
    private String extraUrl;
    private int type;
    public static final int GENERAL_TYPE = 0;
    public static final int WORKSHOP_TYPE = 1;
    public static final int SEMINAR_TYPE = 2;

    Notification(){
        id = -1;
        type = GENERAL_TYPE;
    }

    public Notification(int id, String title, String description, String time, String extraUrl, int type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.extraUrl = extraUrl;
        this.type = type;
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
        return time;
    }

    public String getExtraUrl() {
        return extraUrl;
    }

    public int getType() {
        return type;
    }

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

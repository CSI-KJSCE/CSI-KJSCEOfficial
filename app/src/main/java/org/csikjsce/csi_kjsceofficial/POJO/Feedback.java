package org.csikjsce.csi_kjsceofficial.POJO;

/**
 * Created by sziraqui on 31/7/17.
 */

public class Feedback {
    private int eventid;
    private String link;
    private String message;
    private boolean acceptingResponse;
    Feedback(){}
    Feedback(int id, String url, String msg, boolean accepting){
        eventid = id;
        link = url;
        message = msg;
        acceptingResponse = accepting;
    }
    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAcceptingResponse() {
        return acceptingResponse;
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!( o instanceof Feedback))
            return false;
        else {
            Feedback f = (Feedback) o;
            if(eventid == f.getEventid())
                return true;
        }
        return false;
    }
}

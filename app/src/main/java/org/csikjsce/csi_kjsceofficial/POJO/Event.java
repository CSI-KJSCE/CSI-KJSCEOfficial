package org.csikjsce.csi_kjsceofficial.POJO;

/**
 * Created by sziraqui on 31/7/17.
 */

public class Event {
    private int eventid;
    private String title;
    private String eventdt;
    private String img_url;
    public Event(int eid,String name, String edt, String url){
        eventid = eid;
        title = name;
        eventdt = edt;
        img_url = url;
    }

    public Event(){}

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventdt() {
        return eventdt;
    }

    public void setEventdt(String eventdt) {
        this.eventdt = eventdt;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!( o instanceof Event))
            return false;

            Event f = (Event) o;
            if(this.eventid == f.getEventid())
                return true;
        return false;
    }
}

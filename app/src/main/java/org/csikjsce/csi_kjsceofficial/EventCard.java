package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 19/7/17.
 */

public class EventCard {


    String event_name,Date;
    int image_id;

    EventCard( String event_name,String Date){

        this.setImage_id(image_id);
        this.setEvent_name(event_name);
        this.setDate(Date);
    }
    public String getEvent_name() {
        return event_name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}

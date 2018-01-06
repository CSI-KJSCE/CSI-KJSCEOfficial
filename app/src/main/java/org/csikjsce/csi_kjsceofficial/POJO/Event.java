package org.csikjsce.csi_kjsceofficial.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sziraqui on 31/7/17.
 */

public class Event implements Parcelable{
    private int eventid;
    private String title;
    private String eventdt;
    private String img_url;
    private String category;
    private String audience;
    private String desc;
    private String register;
    private String feedback;
    public Event(int eid,String name, String edt, String url, String category, String description, String regLink, String fdbkLink){
        eventid = eid;
        title = name;
        eventdt = edt;
        img_url = url;
        desc = description;
        register = regLink;
        feedback = fdbkLink;
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

    public String getCategory() {
        return category;
    }

    public String getAudience(){ return audience; }

    public String getDesc() {
        return desc;
    }

    public String getRegister() {
        return register;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setCategory(String category) { this.category = category; }

    public void setAudience(String audience) { this.audience = audience; }

    public void setDesc(String desc) { this.desc = desc; }

    public void setRegister(String register) { this.register = register; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

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

    @Override
    public int hashCode() {
        return eventid;
    }
    Event(Parcel parcel){
        eventid = parcel.readInt();
        eventdt = parcel.readString();
        title = parcel.readString();
        img_url = parcel.readString();
        category = parcel.readString();
        audience = parcel.readString();
        desc = parcel.readString();
        register = parcel.readString();
        feedback = parcel.readString();
    }
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[0];
        }
    };
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventid);
        dest.writeString(eventdt);
        dest.writeString(title);
        dest.writeString(img_url);
        dest.writeString(category);
        dest.writeString(audience);
        dest.writeString(desc);
        dest.writeString(register);
        dest.writeString(feedback);
    }
}

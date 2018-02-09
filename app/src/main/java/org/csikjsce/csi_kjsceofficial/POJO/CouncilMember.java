package org.csikjsce.csi_kjsceofficial.POJO;

/**
 * Created by sziraqui on 31/7/17.
 */

public class CouncilMember {
    private String name;
    private String post;
    private String picUrl;
    private String dept;
    private String profileUrl;
    private int id;

    CouncilMember(){}
    CouncilMember(int id, String name, String post, String pic_url, String dept, String profileUrl){
        this.id = id;
        this.name = name;
        this.post = post;
        this.picUrl = pic_url;
        this.dept = dept;
        this.profileUrl = profileUrl;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDept() {
        return dept;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!( o instanceof CouncilMember))
            return false;
        else {
            CouncilMember f = (CouncilMember) o;
            if(post == f.getPost())
                return true;
        }
        return false;
    }

}

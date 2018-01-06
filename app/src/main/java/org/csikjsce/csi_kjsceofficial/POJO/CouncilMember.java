package org.csikjsce.csi_kjsceofficial.POJO;

/**
 * Created by sziraqui on 31/7/17.
 */

public class CouncilMember {
    private String name;
    private String post;
    private String pic_url;
    private String dept;
    private int id;

    CouncilMember(){}
    CouncilMember(String name, String post, String pic_url, String dept){
        this.name = name;
        this.post = post;
        this.pic_url = pic_url;
        this.dept = dept;
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

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDept() {
        return dept;
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

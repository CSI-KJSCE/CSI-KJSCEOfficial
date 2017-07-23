package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 23/7/17.
 */

public class FeedbackCard {

    public FeedbackCard(String Title){

        this.setImage_id(image_id);
        this.setTitle(Title);

    }


    int image_id;
    String Title;

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

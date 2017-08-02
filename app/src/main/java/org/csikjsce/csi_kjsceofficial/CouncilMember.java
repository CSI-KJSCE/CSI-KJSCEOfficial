package org.csikjsce.csi_kjsceofficial;

import android.content.Context;

/**
 * Created by sumit on 2/8/17.
 */

public class CouncilMember {


        private Context context;
        private String name;
        private String year;
        private String post;
        private int dp;

        CouncilMember(Context context){
            this.context = context;
            name = context.getString(R.string.app_name);
            year=context.getString(R.string.app_name);
            post = context.getString(R.string.app_name);
            dp = R.drawable.csi_ic_splash_screen;
        }
        CouncilMember(String name, String post, String year){
            this.post = post;
            this.name = name;
            this.year = year;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getDp() {
            return dp;
        }

        public void setDp(int dp) {
            this.dp = dp;
        }
    }




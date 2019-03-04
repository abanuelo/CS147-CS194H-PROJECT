package com.bignerdranch.android.pife11.SelectVideoOnProfile;

public class Comment {
        public String user;
        public String date;
        public String likes;
        public String wishes;
        public String userID;

        public Comment(String User, String Date, String likes, String wishes, String userID) {
            this.likes = likes;
            this.wishes = wishes;
            this.date = Date;
            this.user = User;
            this.userID = userID;
        }
}

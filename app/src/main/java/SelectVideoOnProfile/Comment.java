package SelectVideoOnProfile;

public class Comment {
        public String user;
        public String date;
        public String likes;
        public String wishes;

        public Comment(String User, String Date, String likes, String wishes) {
            this.likes = likes;
            this.wishes = wishes;
            this.date = Date;
            this.user = User;
        }
}

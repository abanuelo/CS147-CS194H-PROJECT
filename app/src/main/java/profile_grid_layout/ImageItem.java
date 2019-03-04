package profile_grid_layout;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;
    private String videoTitle;

    public ImageItem(Bitmap image, String title, String videoTitle) {
        super();
        this.image = image;
        this.title = title;
        this.videoTitle = videoTitle;
    }

    public void clearBitmap(){
        this.image.recycle();
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoTitle() {return  videoTitle;}

    public void setTitle(String title) {
        this.title = title;
    }
}
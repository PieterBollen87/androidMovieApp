package be.pxl.filmapp.data.bean;

/**
 * Created by Sacha on 7-11-2017.
 */

public class ReviewBean {
    private int rating;
    private String user;
    private String userName;
    private String avatar;
    private String description;

    public ReviewBean(int rating, String user, String userName, String avatar, String description) {
        this.rating = rating;
        this.user = user;
        this.userName = userName;
        this.avatar = avatar;
        this.description = description;
    }

    public ReviewBean() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

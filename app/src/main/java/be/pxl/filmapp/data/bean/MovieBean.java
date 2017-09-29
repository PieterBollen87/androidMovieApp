package be.pxl.filmapp.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sacha on 27-9-2017.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieBean implements Parcelable {
    private int id;
    private String title;
    private String director;
    private int year;
    private String genre;
    private double rating;
    public MovieBean(){
    }

    public MovieBean(int id, String title, String director, int year, String genre, double rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }



    protected MovieBean(Parcel in) {
        id = in.readInt();
        title = in.readString();
        director = in.readString();
        year = in.readInt();
        genre = in.readString();
        rating = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return String.valueOf(year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return String.valueOf(rating);
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MovieBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }

    public static final Creator<MovieBean> CREATOR = new Creator<MovieBean>() {
        @Override
        public MovieBean createFromParcel(Parcel in) {
            return new MovieBean(in);
        }

        @Override
        public MovieBean[] newArray(int size) {
            return new MovieBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(director);
        parcel.writeInt(year);
        parcel.writeString(genre);
        parcel.writeDouble(rating);
    }
}


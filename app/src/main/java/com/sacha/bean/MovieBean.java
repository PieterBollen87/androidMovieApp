package com.sacha.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieBean {
    private int id;
    private boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private List<GenreBean> genres;
    private String homepage;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    private double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("production_countries")
    private List<CompanyBean> productionCompanies;
    @JsonProperty("production_companies")
    private List<CountryBean> productionCountries;
    @JsonProperty("release_date")
    private Date releaseDate;
    private int revenue;
    private String runtime;
    @JsonProperty("spoken_languages")
    private List<LanguageBean> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;

    public MovieBean(int id, boolean adult, String backdropPath, List<GenreBean> genres, String homepage, String imdbId, String originalLanguage, String originalTitle, String overview, double popularity, String posterPath, List<CompanyBean> productionCompanies, List<CountryBean> productionCountries, Date releaseDate, int revenue, String runtime, List<LanguageBean> spokenLanguages, String status, String tagline, String title, double voteAverage, int voteCount) {
        this.id = id;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genres = genres;
        this.homepage = homepage;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public MovieBean() {
        // default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<GenreBean> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreBean> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<CompanyBean> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<CompanyBean> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<CountryBean> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<CountryBean> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<LanguageBean> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<LanguageBean> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}

package com.example.pierre.movieapplicaiton.data.dao;

import com.example.pierre.movieapplicaiton.data.bean.MovieBean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */
public class MovieaApiDao  implements IMovieDao {
    private String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public MovieaApiDao(String apiKey) {
        this.apiKey = apiKey;
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public MovieBean getMovieById(int id) {
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + this.apiKey);
            StringBuffer content = getRequest(url);
            MovieBean movie = mapper.readValue(content.toString(), MovieBean.class);
            return movie;
            //return movieResponse.getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }

    @Override
    public List<MovieBean> getPopularMovies() {
        try {
            URL url = new URL("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + this.apiKey);
            StringBuffer content = getRequest(url);
            JsonNode json = mapper.readTree(content.toString());

            List<MovieBean> movies = new ObjectMapper().readValue(json.get("results").toString(), new TypeReference<List<MovieBean>>() { });;
            return movies;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }

    @Override
    public List<MovieBean> getAllMovies() {
        try {
            URL url = new URL("http://localhost:3000/api/films");
            StringBuffer content = getRequest(url);

            List<MovieBean>  movies = new ObjectMapper().readValue(content.toString(), new TypeReference<List<MovieBean>>() { });
            return movies;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }

    private StringBuffer getRequest(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return content;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }
}

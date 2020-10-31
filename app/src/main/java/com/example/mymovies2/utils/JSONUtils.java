package com.example.mymovies2.utils;

import android.util.Log;

import com.example.mymovies2.data.Movie;
import com.example.mymovies2.data.Rewiew;
import com.example.mymovies2.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_RESULTS = "results";

    //Ключи для комментариев
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    //Ключи для трейлеров
    private static final String KEY_OF_VIDEO = "key";
    private static final String KEY_NAME = "name";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    //Ключи для фильма
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKGROND_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w185";
    private static final String BIG_POSTER_SIZE = "w780";

    //Метод возвращает массив комментариев к фильму из JSON объекта
    public static ArrayList<Rewiew> getRewievFromJSON (JSONObject jsonObject) {
        ArrayList<Rewiew> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectReview = jsonArray.getJSONObject(i);
                String author = objectReview.getString(KEY_AUTHOR);
                String content = objectReview.getString(KEY_CONTENT);
                Rewiew rewiew = new Rewiew(author, content);
                result.add(rewiew);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Метод возвращает массив трейлеров к фильму из JSON объекта
    public static ArrayList<Trailer> getTrailerFromJSON (JSONObject jsonObject) {
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectReview = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + objectReview.getString(KEY_OF_VIDEO);
                String name = objectReview.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Метод возвражает массив объектов типа Movie из JSON обьекта
    public static ArrayList<Movie> getMoviesFromJSON (JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKGROND_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

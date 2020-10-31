package com.example.mymovies2.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqId, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(uniqId, id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
    }

    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(),
                movie.getBigPosterPath(), movie.getBackdropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }
}

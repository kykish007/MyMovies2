package com.example.mymovies2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies2.adapters.ReviewAdapter;
import com.example.mymovies2.adapters.TrailerAdapter;
import com.example.mymovies2.data.FavouriteMovie;
import com.example.mymovies2.data.MainViewModel;
import com.example.mymovies2.data.Movie;
import com.example.mymovies2.data.Rewiew;
import com.example.mymovies2.data.Trailer;
import com.example.mymovies2.utils.JSONUtils;
import com.example.mymovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ImageView imageViewAddToFavourite;
    private ScrollView scrollViewInfo;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private int id;
    private MainViewModel viewModel;
    private Movie movie;
    FavouriteMovie favouriteMovie;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewRealeaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);
        recyclerViewReviews = findViewById(R.id.recyclerViewReview);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        scrollViewInfo = findViewById(R.id.scrollViewInfo);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());
        setFavourite();
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movie.getId());
        JSONObject jsonObjectReview = NetworkUtils.getJSONForReviews(movie.getId());
        ArrayList<Trailer> trailers = JSONUtils.getTrailerFromJSON(jsonObjectTrailers);
        ArrayList<Rewiew> rewiews = JSONUtils.getRewievFromJSON(jsonObjectReview);
        reviewAdapter.setRewiews(rewiews);
        trailerAdapter.setTrailers(trailers);
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });
        scrollViewInfo.smoothScrollTo(0,0);
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.toast_add_favourite_text, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.toast_delete_faurite_text, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteMovie = viewModel.getFouriteMovieById(id);
        if (favouriteMovie == null) {
            imageViewAddToFavourite.setImageResource(getResources().getIdentifier("@android:drawable/btn_star_big_off", null, null));
        } else {
            imageViewAddToFavourite.setImageResource(getResources().getIdentifier("@android:drawable/btn_star_big_on", null, null));
        }
    }
}
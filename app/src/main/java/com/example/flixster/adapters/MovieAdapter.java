package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.google.android.material.shape.RoundedCornerTreatment;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    //involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //involves populating data into item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the movie at passed in position
        Movie movie = movies.get(position);
        //bind movie data into the ViewHolder
        holder.bind(movie);
    }

    //return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackgroundPath();
            }else{
                imageUrl = movie.getPosterPath();
            }
            //int radius = 2;
            Glide.with(context).load(imageUrl).into(ivPoster);
            //Glide.with(context).load(imageUrl).transform(new RoundedCorners(radius).into(ivPoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("title", movie.getTitle());
                    //i.putExtra("overview", movie.getOverview()); //no need just pass the complete movie class to detail activity
                    i.putExtra("movie", Parcels.wrap(movie));
                    //ActivityOptionsCompat options = ActivityOptionsCompat.
                            //makeSceneTransitionAnimation((View)tvTitle, "title");
                    context.startActivity(i);

                }
            });
        }
    }
}

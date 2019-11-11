package com.meteoro.omdbarch.favorites.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.favorites.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(
    private val presentation: MovieListPresentation,
    private val listener: MovieListLitener
) : RecyclerView.Adapter<MovieListAdapter.MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.movie_list_item, parent, false)
        return MovieListHolder(view)
    }

    override fun getItemCount(): Int = presentation.movies.size

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.bind(presentation.movies[position])
    }

    inner class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: Movie) {
            itemView.run {
                movieListTitle.text = row.title
                movieListYear.text = row.year
                movieListRating.text = row.imdbRating
                Picasso.get().load(row.poster).into(movieListImage)

                iconDelete.setOnClickListener { listener.deleteMovie(row) }
                setOnClickListener { listener.navigateToMovie(row) }
            }
        }
    }

    interface MovieListLitener {
        fun navigateToMovie(movie: Movie)
        fun deleteMovie(movie: Movie)
    }
}
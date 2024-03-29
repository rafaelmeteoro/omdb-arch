package com.meteoro.omdbarch.favorites.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meteoro.omdbarch.domain.model.Movie
import com.meteoro.omdbarch.favorites.databinding.MovieListItemBinding

class MovieListAdapter(
    private val listener: MovieListListener
) : RecyclerView.Adapter<MovieListAdapter.MovieListHolder>() {

    private var data: MovieListPresentation? = null

    fun setData(presentation: MovieListPresentation) {
        data = presentation
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflate, parent, false)
        return MovieListHolder(binding)
    }

    override fun getItemCount(): Int = data?.movies?.size ?: 0

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        data?.let { presentation ->
            holder.bind(presentation.movies[position])
        }
    }

    inner class MovieListHolder(private val itemBinding: MovieListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(row: Movie) {
            itemBinding.movieListTitle.text = row.title
            itemBinding.movieListYear.text = row.year
            itemBinding.movieListRating.text = row.imdbRating
            itemBinding.movieListImage.load(row.poster) {
                crossfade(true)
            }

            itemBinding.iconDelete.setOnClickListener { listener.deleteMovie(row) }
            itemBinding.displayContainer.setOnClickListener { listener.navigateToMovie(row) }
        }
    }

    interface MovieListListener {
        fun navigateToMovie(movie: Movie)
        fun deleteMovie(movie: Movie)
    }
}

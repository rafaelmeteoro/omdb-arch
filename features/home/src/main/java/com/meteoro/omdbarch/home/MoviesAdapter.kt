package com.meteoro.omdbarch.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.meteoro.omdbarch.home.databinding.MovieItemBinding

class MoviesAdapter(
    private val presentation: HomePresentation,
    private val shareAction: (MoviePresentation) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflate, parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = presentation.movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(presentation.movies[position], shareAction)
    }

    inner class MovieHolder(private val itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(row: MoviePresentation, action: (MoviePresentation) -> Unit) {
            itemBinding.movieTitle.text = row.title
            itemBinding.movieImage.load(row.photoUrl) {
                crossfade(true)
            }
            itemBinding.displayContainer.setOnClickListener { action.invoke(row) }
        }
    }
}

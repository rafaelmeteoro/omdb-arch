package com.meteoro.omdbarch.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meteoro.omdbarch.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val presentation: HomePresentation,
    private val shareAction: (MoviePresentation) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = presentation.movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(presentation.movies[position], shareAction)
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(row: MoviePresentation, action: (MoviePresentation) -> Unit) {
            itemView.run {
                movieTitle.text = row.title
                Picasso.get().load(row.photoUrl).into(movieImage)

                setOnClickListener { action.invoke(row) }
            }
        }
    }
}
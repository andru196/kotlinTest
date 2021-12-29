package com.example.filmsearch.presentation.topFilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.contains
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsearch.R
import com.example.filmsearch.databinding.ItemFilmBinding
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.setImageUrl

class TopFilmsAdapter(
    private val onFilmClicked: (Film) -> Unit
): ListAdapter<Film, TopFilmsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
            oldItem == newItem

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemFilmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            itemFilmName.text = item.name
            itemFilmYear.text = item.year.toString()
            itemFilmRaiting.text  = root.context.getString(R.string.item_film_rating_pattern, item.rating)
            item.posterUrlPreview?.let {
                filmItemPoster.setImageUrl(it)
            }
            root.setOnClickListener { onFilmClicked(item) }
        }
    }

    class ViewHolder(val binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root)
}
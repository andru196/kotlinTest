package com.example.filmsearch.presentation.filmDetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmsearch.R
import com.example.filmsearch.databinding.FilmDetailScreenBinding
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class FilmDetailFragment: BaseFragment(R.layout.film_detail_screen) {
    companion object {
        fun newInstance(film: Film) = FilmDetailFragment().apply {
            arguments = bundleOf(FILM_DETAIL_DATA_KEY to film)
//            Bundle().putParcelable(FILM_DETAIL_DATA_KEY, film)
        }

        private const val FILM_DETAIL_DATA_KEY = "FILM_DETAIL_DATA_KEY"
    }

    private val viewBinding by viewBinding(FilmDetailScreenBinding::bind)
    private val viewModel by viewModels<FilmDetailViewModel>() {
        object :ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                FilmDetailViewModel(arguments?.getParcelable<Film>(FILM_DETAIL_DATA_KEY)!!) as T

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmState.observe(viewLifecycleOwner) { film ->
            viewBinding.filDetailYear.text = film.year.toString()
            viewBinding.filmDetailName.text = film.name
        }

        viewModel.backAction.observe(viewLifecycleOwner) {
            closeScreen()
        }
        viewBinding.filDetailBack.setOnClickListener{
            viewModel.onbackPressed()
        }
        val film = arguments?.getParcelable<Film>(FILM_DETAIL_DATA_KEY)
        viewBinding.filDetailYear.text = film?.name
    }

    private fun closeScreen() {
        parentFragmentManager.popBackStack()
    }
}
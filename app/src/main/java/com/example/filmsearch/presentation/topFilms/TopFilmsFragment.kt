package com.example.filmsearch.presentation.topFilms

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.TopFilmsScreenBinding
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.city.CityFragment
import com.example.filmsearch.presentation.common.BaseFragment
import com.example.filmsearch.presentation.common.navigate
import com.example.filmsearch.presentation.filmDetail.FilmDetailFragment
import com.example.filmsearch.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFilmsFragment : BaseFragment(R.layout.top_films_screen) {

    private val viewBinding by viewBinding(TopFilmsScreenBinding::bind)
    private val viewModel by viewModels<TopFilmsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topFilmsAdapter = TopFilmsAdapter(viewModel::onFilmClicked)
        with (viewBinding.topFilmList) {
            adapter = topFilmsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TopFilmsState.Error -> {
                    viewBinding.topFilmError.isVisible = true
                    viewBinding.topFilmProgress.isVisible = false
                    viewBinding.topFilmList.isVisible = false
                }
                is TopFilmsState.Loading -> {
                    viewBinding.topFilmError.isVisible = false
                    viewBinding.topFilmProgress.isVisible = true
                    viewBinding.topFilmList.isVisible = false
                }
                is TopFilmsState.Success -> {
                    topFilmsAdapter.submitList(state.films)
                    viewBinding.topFilmError.isVisible = false
                    viewBinding.topFilmProgress.isVisible = false
                    viewBinding.topFilmList.isVisible = true

                }
            }
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }

        viewBinding.topViewSearch.setOnClickListener {
            openSearch()
        }

        viewBinding.topFilmCity.setOnClickListener {
            openCity()
        }
    }

    private fun openDetail(film: Film) {
        parentFragmentManager.navigate(FilmDetailFragment.newInstance(film))
    }

    private fun openSearch() {
        parentFragmentManager.navigate(SearchFragment())
    }
    private fun openCity() {
        parentFragmentManager.navigate(CityFragment())

    }
}
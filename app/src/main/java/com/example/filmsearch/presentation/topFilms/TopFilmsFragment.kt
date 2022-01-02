package com.example.filmsearch.presentation.topFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.App
import com.example.filmsearch.R
import com.example.filmsearch.databinding.TopFilmsScreenBinding
import com.example.filmsearch.di.NetworkModule
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.BaseFragment
import com.example.filmsearch.presentation.filmDetail.FilmDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

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

        viewModel.filmState.observe(viewLifecycleOwner) {
            topFilmsAdapter.submitList(it)
        }

        viewModel.openDetailAction.observe(viewLifecycleOwner) {
            openDetail(it)
        }


    }

    private fun openDetail(film: Film) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_container, FilmDetailFragment.newInstance(film))
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }
}
package com.example.filmsearch.presentation.topFilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.TopFilmsScreenBinding
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.BaseFragment
import com.example.filmsearch.presentation.filmDetail.FilmDetailFragment

class TopFilmsFragment : BaseFragment(R.layout.top_films_screen) {
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.top_films_screen)
//    }

    private val viewBinding by viewBinding(TopFilmsScreenBinding::bind)
    private val viewModel by viewModels<TopFilmsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.topFilmAddCount.setOnClickListener{
            viewModel.onAdd()
        }
        viewModel.countState.observe(viewLifecycleOwner) { count ->
            viewBinding.topFilmCount.text = count.toString()
        }
        viewBinding.topFilmShowDetail.setOnClickListener{
            openDetail(Film("Джентельмены", "2020"))
        }
    }

    private fun openDetail(film: Film) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_container, FilmDetailFragment.newInstance(film))
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt(COUNTER_SAVE_KEY, count)
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        savedInstanceState?.getInt(COUNTER_SAVE_KEY)?.let {
//            count = it
//            setTextToCount()
//        }
//    }
//
//    companion object {
//        private const val COUNTER_SAVE_KEY = "COUNTER_SAVE_KEY"
//    }
}
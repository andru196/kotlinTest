package com.example.filmsearch.presentation.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.SearchScreenBinding
import com.example.filmsearch.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.search_screen) {

    private val viewBinding by viewBinding(SearchScreenBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewBinding.searchSubmit.setOnClickListener{
            search()
        }

        viewBinding.searchYearToEdit.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
            }
            true
        }

        viewModel.yearFromState.observe(viewLifecycleOwner) {
            viewBinding.searchYearFromLayout.error = it.getText()
        }

        viewModel.yearFromState.observe(viewLifecycleOwner) {
            viewBinding.searchYearToLayout.error = it.getText()
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            viewBinding.searchProgress.isVisible = it
        }

        viewBinding.searchYearFromEdit.addTextChangedListener{
            viewModel.onTearFromChange(it.toString())
        }
//        viewBinding.searchYearFromEdit.addTextChangedListener { object :TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
//            }
//
//            }
//        }
    }

    private fun search() {
        viewModel.search(
            viewBinding.searchYearFromEdit.text.toString(),
            viewBinding.searchYearToEdit.text.toString())
    }

    fun YearState.getText(): String =
        when (this) {
            YearState.EMPTY -> "Необходимо заполнить поле"
            YearState.VALID -> ""
        }


}
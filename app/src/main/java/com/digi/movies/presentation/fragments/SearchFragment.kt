package com.digi.movies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.digi.movies.R
import com.digi.movies.adapters.CategoriesAdapter
import com.digi.movies.databinding.FragmentSearchBinding
import com.digi.movies.presentation.fragments.main.MainViewModel
import com.digi.movies.utils.Status
import com.digi.movies.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels ()
    private lateinit var query : String
    private lateinit var listAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, com.digi.movies.R.layout.fragment_search, container, false)
        setupRecyclerView()
        handleSearch()
        val bundle = this.arguments
        if (bundle != null) {
            query = bundle.getString("query") as String
            binding.searchField.setText(query)
            viewModel.getSearchQuery(query)

        }



        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSearchQuery.collect{ list ->

                    when(list.status)
                    {

                        Status.SUCCESS ->{

                            if (list.data?.results!!.isNotEmpty())
                            {
                                binding.progressBar.visibility=View.GONE

                                list.data.results.apply {
                                    listAdapter.updateDayListItems(this)

                                }


                            }

                        }
                        Status.ERROR ->{
                            binding.progressBar.visibility=View.GONE
                        }
                        Status.LOADING -> {

                            binding.progressBar.visibility=View.VISIBLE

                        }
                    }

                }
            }
        }


        return binding.root
    }

    private fun searchAction()
    {
        viewModel.getSearchQuery(binding.searchField.text.toString())
        binding.searchField.hideKeyboard()
    }
    private fun handleSearch ()
    {
        binding.searchBtn.setOnClickListener {

           searchAction()
        }
        binding.searchField.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchAction()

                return@OnEditorActionListener true
            }
            false
        })
    }
    private fun setupRecyclerView()
    {
        binding.recyclerviewSearchResult.layoutManager = GridLayoutManager(context,2)
        listAdapter = CategoriesAdapter(arrayListOf()) {  movie ->

            val bundle = Bundle()
            bundle.putSerializable("movie", movie)

            findNavController().navigate(R.id.action_searchFragment_to_Movie_details,bundle)
        }
        binding.recyclerviewSearchResult.adapter =listAdapter


    }
}
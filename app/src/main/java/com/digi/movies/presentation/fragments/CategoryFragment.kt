package com.digi.movies.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.digi.movies.R
import com.digi.movies.adapters.CategoriesAdapter
import com.digi.movies.databinding.FragmentGenreBinding
import com.digi.movies.domain.models.Genre
import com.digi.movies.presentation.fragments.main.MainViewModel
import com.digi.movies.utils.Status

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentGenreBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var genreObject : Genre
    private lateinit var genreAdapter: CategoriesAdapter
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, com.digi.movies.R.layout.fragment_genre, container, false)
        setupRecyclerView()


               val bundle = this.arguments
               if (bundle != null) {
                 genreObject = bundle.getSerializable("genre") as Genre
                  val source= bundle.getInt("source")
                   if (source==1)
                   {
                       viewModel.getGenreList(genreObject.id.toString())
                   }

               }



      //

               lifecycleScope.launch{
                       viewModel.getMoviesListR.collect{

                               data ->

                           when(data.status)
                           {
                               Status.SUCCESS ->{

                                   genreAdapter.updateDayListItems(data.data!!.results)
                                   viewModel.savedMovie(data.data.results)
                                   for ( movie in data.data.results)
                                   {
                                       movie.genre_id= movie.genre_ids?.get(0)
                                   }



                               }
                               Status.LOADING -> {

                               }

                               Status.ERROR -> {



                                   viewModel.getGenreLocaleList(genreObject.id.toString())

                               }

                           }
                       }

                   }





        lifecycleScope.launch {
            viewModel.getMoviesListL.collect{
                    data ->

                when(data.status) {
                    Status.SUCCESS -> {

                        genreAdapter.updateDayListItems(data.data!!)


                    }
                    Status.LOADING -> {

                    }

                    Status.ERROR -> {



                    }
                }



            }
        }





        return binding.root
    }

    private fun setupRecyclerView()
    {
        binding.recyclerGenreList.layoutManager = GridLayoutManager(context,2)
        genreAdapter = CategoriesAdapter(arrayListOf()) {  movie ->
           // val navController = Navigation.findNavController(requireView())
           // Log.e("object","="+movie.toString())
           // val action = CategoryFragmentDirections.actionCategoryFragmentToMovieDetails(movie)
        //    bundle.putParcelable("movie",movie)




            val bundle = Bundle()
            bundle.putSerializable("movie", movie)
          //  val frag=MovieDetailsFragment()
          //  frag.arguments=bundle


          //  findNavController().navigateUp()
         //val action=   MainFragmentDirections.actionMainToMovieDetails(movie)
            findNavController().navigate(R.id.action_main_to_Movie_details,bundle)
        }
        binding.recyclerGenreList.adapter =genreAdapter


    }


}
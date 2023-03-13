package com.digi.movies.presentation.fragments.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.digi.movies.R
import com.digi.movies.adapters.CategoriesAdapter
import com.digi.movies.adapters.GenreViewPagerAdapter
import com.digi.movies.databinding.FragmentMovieDetailsBinding
import com.digi.movies.domain.models.Movie
import com.digi.movies.presentation.fragments.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {


    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var mAdapter : CategoriesAdapter
    private lateinit var gAdapter : GenreViewPagerAdapter
  //  private val args: MovieDetailsFragmentArgs by navArgs()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, com.digi.movies.R.layout.fragment_movie_details, container, false)



        val bundle = this.arguments
        if (bundle!!.getSerializable("movie") != null) {
           val movie = bundle.getSerializable("movie") as Movie
            binding.movieDetails=movie
            (requireActivity() as AppCompatActivity).supportActionBar?.title = movie.title
            binding.ratingBar.rating= movie.vote_average!!.toFloat()
            binding.raingReviews.text=getString(R.string.reviews)+" ${movie.vote_count}"
        }


        

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }





}
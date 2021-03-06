package com.manuellugodev.movie.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.manuellugodev.movie.MovieApplication
import com.manuellugodev.movie.R
import com.manuellugodev.movie.domain.model.Movie
import com.manuellugodev.movie.data.home.RepositoryMovies
import com.manuellugodev.movie.retrofit.data.requests.home.MovieRequest
import com.manuellugodev.movie.retrofit.sources.RemoteSourceMovieDb
import com.manuellugodev.movie.usecases.GetPopularMovieUseCase
import com.manuellugodev.movie.usecases.GetTopRatedMovieUseCase
import com.manuellugodev.movie.vo.DataResult
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment(), adapterListMovies.OnMovieClickListener{




   /* private val dataSource =
        RemoteSourceMovieDb(MovieRequest("https://api.themoviedb.org/3/"))

    private val repository = RepositoryMovies(dataSource)

    private val getTopRatedMoviesUseCase = GetTopRatedMovieUseCase(repository)

    private val getPopularMovieUseCase = GetPopularMovieUseCase(repository)*/

    @Inject
    lateinit var  getTopRatedMovieUseCase: GetTopRatedMovieUseCase

    @Inject
    lateinit var getPopularMovieUseCase: GetPopularMovieUseCase


    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            getTopRatedMovieUseCase,
            getPopularMovieUseCase
        )
    }




    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MovieApplication).appComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        attachObserve()



        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers();


    }

    private fun attachObserve() {

    /*    homeViewModel.fetchMovieListComedy.observe(viewLifecycleOwner, Observer { listMovie ->

            when (listMovie) {

                is DataResult.Success -> {

                    progressBar.visibility = View.GONE

                    rvComedia.adapter = adapterListMovies(requireContext(), listMovie.data, this)

                }

                is DataResult.Loading -> {
                    progressBar.visibility = View.VISIBLE

                }

                is DataResult.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Error de Tipo: ${listMovie.exception}",
                        Toast.LENGTH_LONG
                    )
                }
            }

        })*/


        homeViewModel.fetchMoviePopularList.observe(viewLifecycleOwner, Observer { listMovie ->

            when (listMovie) {

                is DataResult.Success -> {

                    progressBar.visibility = View.GONE

                    rvHome.adapter = adapterListMovies(requireContext(), listMovie.data, this)

                }

                is DataResult.Loading -> {
                    progressBar.visibility = View.VISIBLE

                }

                is DataResult.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Error de Tipo: ${listMovie.exception}",
                        Toast.LENGTH_LONG
                    )
                }
            }

        })


    }

    private fun setupRecyclers() {

        rvHome.layoutManager =
            GridLayoutManager(requireContext(), 2)


    }


    override fun onMovieClick(movie: Movie) {
        val bundle = Bundle()

        bundle.putInt("IDMOVIE", movie.id)

        findNavController().navigate(R.id.action_navigation_home_to_movieDetailFragment, bundle)
    }
}
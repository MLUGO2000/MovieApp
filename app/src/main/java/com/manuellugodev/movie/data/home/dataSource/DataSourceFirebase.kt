package com.manuellugodev.movie.data.home.dataSource

import com.manuellugodev.movie.data.home.Movie
import com.manuellugodev.movie.vo.Resource

interface DataSourceFirebase {

    suspend fun getListMovieCategory(category:String):Resource<List<Movie>>

    suspend fun getListMoviePrincipal()

}
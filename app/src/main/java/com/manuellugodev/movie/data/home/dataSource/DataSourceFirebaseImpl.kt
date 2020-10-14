package com.manuellugodev.movie.data.home.dataSource

import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.movie.model.Movie
import com.manuellugodev.movie.vo.DataResult
import kotlinx.coroutines.tasks.await

class DataSourceFirebaseImpl(val db:FirebaseFirestore) :DataSourceFirebase{

    override suspend fun getListMovieCategory(category: String) :DataResult<List<Movie>> {

        val listMovies= mutableListOf<Movie>()


        val resultMovies=db.collection("movies").whereEqualTo("Category",category).get().await()

        for(document in resultMovies){

            val movie=document.toObject(Movie::class.java)
            listMovies.add(movie)
        }

        return DataResult.Success(listMovies)

    }

    override suspend fun getListMoviePrincipal() {



    }

}
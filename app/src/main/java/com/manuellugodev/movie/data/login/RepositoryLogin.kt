package com.manuellugodev.movie.data.login

import com.manuellugodev.movie.vo.ResultLogin


class RepositoryLogin(private val dataSource:DataSourceLogin) {

    suspend fun LoginWithEmailAndPassword(email:String,password:String):ResultLogin<String>{

        return dataSource.LoginWithEmailAndPassword(email, password)

    }
}
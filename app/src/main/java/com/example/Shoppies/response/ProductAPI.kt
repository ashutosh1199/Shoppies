package com.example.Shoppies.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {

    @GET("/products")
    suspend fun getProducts():Response<List<Products>>

    @GET("/products/category/{name}")
    suspend fun searchproducts(
        @Path("name") name:String
    ):Response<List<Products>>
}
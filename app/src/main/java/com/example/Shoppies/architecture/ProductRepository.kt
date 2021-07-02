package com.example.Shoppies.architecture

import com.example.Shoppies.response.Products
import com.example.Shoppies.response.RetrofitInstance

class ProductRepository(
    private val db:Productdb
) {

    suspend fun getproducts()=
        RetrofitInstance.api.getProducts()

    suspend fun searchproducts(name:String)=
        RetrofitInstance.api.searchproducts(name)

    suspend fun upsert(products: Products)=db.getArticleDao().upsert(products)

   /* suspend fun getOrderId(map: HashMap<String, Int>)=
        RetrofitInstance2.api.getOrderId(map)

    suspend fun updateTransaction(map: HashMap<String, String>)=
        RetrofitInstance2.api.updateTransaction(map)*/

    suspend fun delete(products: Products)=db.getArticleDao().delete(products)

    fun getAllproducts()=db.getArticleDao().getallProducts()

    suspend fun checkid(Id:Int)=db.getArticleDao().checkid(Id)

    suspend fun allproducts()=db.getArticleDao().allProducts()


}
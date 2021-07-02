package com.example.Shoppies.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Shoppies.Util.Resource
import com.example.Shoppies.response.Products
import kotlinx.coroutines.*
import retrofit2.Response
import java.math.BigDecimal

class ProductViewModel(
  val ProductsRepository:ProductRepository
): ViewModel() {

   val Getproducts:MutableLiveData<Resource<List<Products>>> = MutableLiveData()

  val price:MutableLiveData<BigDecimal> = MutableLiveData()

  val SearchProducts:MutableLiveData<Resource<List<Products>>> = MutableLiveData()

/*  val getorderid:MutableLiveData<Resource<Order>> = MutableLiveData()

  val update:MutableLiveData<Resource<String>> = MutableLiveData()*/

  init{
    viewModelScope.launch {
      val list=allproducts().await()
      if(list.isNotEmpty()){
        var ans:BigDecimal=0.toBigDecimal()
        for(i in list){
          val a1=i.amt?.toInt()!!
          val a2=i.price!!
          val adder=(a1*a2).toBigDecimal()
          ans=ans.plus(adder)
        }
        price.value=ans
      }
     else{
        price.value= BigDecimal.valueOf(0.0)
      }
    }
    getProducts()
  }

  fun increase(Newprice:BigDecimal){
    price.value = price.value?.plus(Newprice)
  }

  fun decrease(Newprice:BigDecimal){
    price.value=price.value?.minus(Newprice)
  }

  fun deletePrice(Amount:Int,Newprice:Double){
    val DeletePrice:BigDecimal=(Newprice*Amount).toBigDecimal()
    price.value=price.value?.minus(DeletePrice)
  }

  /*fun getOrderId(map: HashMap<String,Int>)=viewModelScope.launch {
    val response=ProductsRepository.getOrderId(map)
    getorderid.postValue(handleGetOrderId(response))
  }

  fun updatetransaction(map: HashMap<String,String>)=viewModelScope.launch {
    val response=ProductsRepository.updateTransaction(map)
    update.postValue(handleupdatetransaction(response))
  }

  private fun handleupdatetransaction(response: Response<String>): Resource<String> {
    if(response.isSuccessful){
      response.body()?.let{
        return Resource.Success(it)
      }
    }
    return Resource.Error(response.message())
  }


  private fun handleGetOrderId(response: Response<Order>): Resource<Order> {
    if(response.isSuccessful){
      response.body()?.let{
        return Resource.Success(it)
      }
    }
    return Resource.Error(response.message())
  }*/

  fun getProducts()=viewModelScope.launch{
    Getproducts.postValue(Resource.Loading())
    val response=ProductsRepository.getproducts()
    Getproducts.postValue(handleGetProducts(response))
  }

  fun searchProducts(name:String)=viewModelScope.launch{
    SearchProducts.postValue(Resource.Loading())
    val response=ProductsRepository.searchproducts(name)
    SearchProducts.postValue(handleSearchProducts(response))

  }

  private fun handleGetProducts(response: Response<List<Products>>):Resource<List<Products>>{
    if(response.isSuccessful){
      response.body()?.let{
        return Resource.Success(it)
      }
    }
    return Resource.Error(response.message())
  }

  private fun handleSearchProducts(response: Response<List<Products>>):Resource<List<Products>>{
    if(response.isSuccessful){
      response.body()?.let{
        return Resource.Success(it)
      }
    }
    return Resource.Error(response.message())
  }


  fun upsert(products: Products)=viewModelScope.launch {
    ProductsRepository.upsert(products)
  }

  fun delete(products: Products)=viewModelScope.launch {
    ProductsRepository.delete(products)
  }

  fun getAllProducts()=ProductsRepository.getAllproducts()

  fun checkid(Id:Int)=viewModelScope.async {
    ProductsRepository.checkid(Id)
  }

  fun allproducts()=viewModelScope.async{
    ProductsRepository.allproducts()
  }



}
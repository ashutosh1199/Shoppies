package com.example.Shoppies.Models

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Shoppies.R
import com.example.Shoppies.Util.Resource
import com.example.Shoppies.adapter.ProductAdapter
import com.example.Shoppies.architecture.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment(R.layout.fragment_product) {

    lateinit var viewModel: ProductViewModel
    lateinit var Productadapter:ProductAdapter

    val TAG="ProductFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

        setRecyclerview()

        etSearch.addTextChangedListener { editable->
            val category=editable.toString()
            if(category=="electronics" || category=="jewelery" || category=="men's clothing" || category=="women's clothing"){
                viewModel.searchProducts(category)
            }

        }


        viewModel.SearchProducts.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Success->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    it.data?.let{NewProducts->
                        Productadapter.differ.submitList(NewProducts)
                    }
                }

                is Resource.Error->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    val message=it.message
                    Log.e(TAG,"an error occured $message")
                }

                is Resource.Loading->{
                    paginationProgressBar.visibility=View.VISIBLE
                }
            }
        })

        viewModel.Getproducts.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Success->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    it.data?.let{NewProducts->
                        Productadapter.differ.submitList(NewProducts)
                    }
                }

                is Resource.Error->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    val message=it.message
                    Log.e(TAG,"an error occured $message")
                }

                is Resource.Loading->{
                    paginationProgressBar.visibility=View.VISIBLE
                }
            }
        })

        fabCart.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_cartFragment)
        }
    }

    private fun setRecyclerview(){
        Productadapter= ProductAdapter(viewModel)
        rvProduct.apply{
            adapter=Productadapter
            layoutManager=LinearLayoutManager(context)
        }
    }


}
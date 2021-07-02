package com.example.Shoppies.Models

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Shoppies.R
import com.example.Shoppies.adapter.CartAdapter
import com.example.Shoppies.architecture.ProductViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import java.math.BigDecimal


class CartFragment : Fragment(R.layout.fragment_cart) {

    lateinit var viewModel: ProductViewModel
    lateinit var cartadapter:CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

        setRecyclerview()

        viewModel.getAllProducts().observe(viewLifecycleOwner, Observer {
            cartadapter.differ.submitList(it)
        })

        viewModel.price.observe(viewLifecycleOwner, Observer {
            tvTotalPrice.text = it.toString()
        })


       // Checkout.preload(context)

        btpay.setOnClickListener {

          val amount=tvTotalPrice.text.toString().toBigDecimal()
            val check= BigDecimal.valueOf(0.0)

            if(amount==check){
                Toast.makeText(context,"The Cost is 0",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

           val amt=amount.toString().toDouble()

            val newamt=amt.times(100).toInt().toString()


            /*Toast.makeText(context,"The Amount is $newamt",Toast.LENGTH_LONG).show()*/

            /*initiatePayment()*/

            navigate(newamt)



        }

    }

    private fun navigate(newamt:String) {
        val intent = Intent(context, CheckoutActivity::class.java)
        intent.putExtra("AMOUNT",newamt)
        startActivity(intent)
    }

    /* private fun Getorderid(amt:Int) {

         val map = HashMap<String, Int>()
         map["amount"] = amt


         viewModel.getOrderId(map)



        viewModel.getorderid.observe(viewLifecycleOwner, Observer {
            when(it){

            is Resource.Success->{
            if(it.data!=null){
                val order=it.data
                initiatePayment(amt.toString(),order)
            }
        }

            is Resource.Error->{
            val message=it.message!!
            Toast.makeText(context,"Error occured $message",Toast.LENGTH_LONG).show()
        }
        }
        })

     }*/

   /* private fun initiatePayment() {
        val checkout = Checkout()


        checkout.setImage(R.drawable.ic_launcher_background)

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc")
            options.put("currency","INR")
            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount","50000")//pass amount in currency subunits

          /*  val retryObj =  JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)*/
            checkout.open(activity as MainActivity,options)
        }
        catch(e:Exception){
            Toast.makeText(context,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
        }



    }*/

    private fun setRecyclerview(){
        cartadapter= CartAdapter(viewModel)
        rvCart.apply{
            adapter=cartadapter
            layoutManager= LinearLayoutManager(context)
        }
    }


}
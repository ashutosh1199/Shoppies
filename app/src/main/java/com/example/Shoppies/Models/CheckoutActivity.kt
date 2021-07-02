package com.example.Shoppies.Models

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Shoppies.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_checkout.*
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {

   // val TAG:String = com.example.foodordering.Models.CheckoutActivity::class.toString()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        Checkout.preload(applicationContext)


        val amt=intent.getStringExtra("AMOUNT")


        btclickme.setOnClickListener {

            val name=etName.text.toString()
            val email=etEmail.text.toString()
            val phoneNo=etPhone.text.toString()
            val address=etAddress.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty() && phoneNo.isNotEmpty() && address.isNotEmpty() ){
                startPayment(name,amt!!)
            }
            else if(name.isEmpty()){
                Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show()
            }
            else if(phoneNo.isEmpty()){
                Toast.makeText(this,"Please enter phone number",Toast.LENGTH_LONG).show()
            }
            else if(email.isEmpty()){
                Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show()
            }
            else if(address.isEmpty()){
                Toast.makeText(this,"Please enter Address",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startPayment(name:String,amt:String){


        val co = Checkout()

        co.setKeyID("rzp_test_sQu2tYSlwixU8B")

        val activity:Activity = this

        try {
            val options = JSONObject()
            options.put("name",name)
            options.put("description","Payement Gateway")
            //You can omit the image option to fetch the image from dashboard
            //options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
           // options.put("theme.color", "#3399cc")
            options.put("currency","INR")
           // options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount",amt)//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email","ashutosh1199@gmail.com")
            prefill.put("contact","9354758358")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?){
       Toast.makeText(this,"Payment Success your order will be delivered to address mentioned",Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failure due to $p1",Toast.LENGTH_LONG).show()

    }


}
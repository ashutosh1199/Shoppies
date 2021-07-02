package com.example.Shoppies.Models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.Shoppies.R
import com.example.Shoppies.architecture.ProductRepository
import com.example.Shoppies.architecture.ProductViewModel
import com.example.Shoppies.architecture.ProductViewModelFactory
import com.example.Shoppies.architecture.Productdb
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ProductViewModel
    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRepository=ProductRepository(Productdb(this))
        val productViewModelFactory=ProductViewModelFactory(productRepository)
        viewModel=ViewModelProvider(this,productViewModelFactory).get(ProductViewModel::class.java)

        firebaseAuth= FirebaseAuth.getInstance()



    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btlogOut ->{
                firebaseAuth.signOut()
                val intent=Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

   /* override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val map = HashMap<String, String>()

        map["order_id"] = p1!!.orderId
        map["pay_id"] = p1.paymentId
        map["signature"] = p1.signature


        viewModel.updatetransaction(map)

         viewModel.update.observe(this, Observer {
             when(it){
                 is Resource.Success->{
                     val message=it.message!!
                     Toast.makeText(this,"Error occured $message",Toast.LENGTH_LONG).show()
                 }

                 is Resource.Error->{
                     if(it.data=="success"){
                         Toast.makeText(this,"Payment Successful",Toast.LENGTH_LONG).show()
                     }
                 }
             }
         })

    }*/




}
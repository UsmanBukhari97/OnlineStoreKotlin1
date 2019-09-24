package com.example.onlinestorekotlin1


import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class AmountFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_amount, container, false)

       var fragmentView =  inflater.inflate(R.layout.fragment_amount, container, false)

        //when user enters amount and press addtocart button
        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edtEnterAmount)
        var btnAddToCart = fragmentView.findViewById<ImageButton>(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {

            //first reference to temporary place order table inside database
            //as in php file we wrote a code to get three values but here we want to get only amount value for add to cart
            //so for one value email we will refer to the person class we created to track email
            //and for product id we will write the code in eproductadapter on onclicklistener of imagebutton
            //so when we add to cart inside temporaryplaceorder the table will be created with id we tracked email we called here also tracked and amount which we will enter
            var tempOrderURL = "http://192.168.100.68/OnlineStoreApp/insert_temporary_order.php?email=${Person.email}&product_id=${Person.addToCartProductID}&amount=${edtEnterAmount.text.toString()}"
            var requestQ = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET, tempOrderURL, Response.Listener {response ->

            var intent = Intent(activity, CartProductsActivity::class.java)
                startActivity(intent)

            }, Response.ErrorListener { error ->


                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setTitle("Error Message!")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()


            })

            requestQ.add(stringRequest)
        }


        return fragmentView


    }


}

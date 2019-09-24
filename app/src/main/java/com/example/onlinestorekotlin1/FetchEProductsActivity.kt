package com.example.onlinestorekotlin1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand: String = intent.getStringExtra("BRAND")
        txtBrandName.text = "Products of $selectedBrand"
        //objects to hold eproduct
        var productsList = ArrayList<EProduct>()


       // val productsURL = "http://192.168.100.68/OnlineStoreApp/fetch_eproducts.php?brand=" + selectedBrand
        val productsURL = "http://192.168.100.68/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        //what we want to get from the server
        //we need to get json array object as shown in the browser
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, productsURL, null, Response.Listener { response ->

            //response is array
            //when for loop is done getting all objectjson it will continue the flow of program
            for (productJsonObjectIndex in 0.until(response.length())) {

                productsList.add(EProduct(response.getJSONObject(productJsonObjectIndex).getInt("id"),
                    response.getJSONObject(productJsonObjectIndex).getString("name"),
                    response.getJSONObject(productJsonObjectIndex).getInt("price"),
                    response.getJSONObject(productJsonObjectIndex).getString("picture")))

            }

            //args of constructor defined in eproductadapter class
            val productAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
            //specifying layout manager of recycler view
            productsRV.layoutManager = LinearLayoutManager(this@FetchEProductsActivity)
            productsRV.adapter = productAdapter




        }, Response.ErrorListener { error ->


            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error Message!")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()


        })

        requestQ.add(jsonArrayRequest)

    }
}

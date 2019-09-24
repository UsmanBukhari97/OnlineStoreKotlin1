package com.example.onlinestorekotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsURL = "http://192.168.100.68/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonArrayRequest = JsonArrayRequest(Request.Method.GET, cartProductsURL, null, Response.Listener { response ->
//iterate over objects of array
        for (jsonObjectIndex in 0.until(response.length())){

            //name,price,email,amount
            cartProductsList.add("${response.getJSONObject(jsonObjectIndex).getInt("id")} \n " +
                    "${response.getJSONObject(jsonObjectIndex).getString("name")} \n " +
                    "${response.getJSONObject(jsonObjectIndex).getInt("price")} \n " +
                    "${response.getJSONObject(jsonObjectIndex).getString("email")} \n " +
                    "${response.getJSONObject(jsonObjectIndex).getInt("amount")}")

        }
            var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductsList)
            cartProductsListView.adapter = cartProductsAdapter


        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error Message!")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })
        requestQ.add(jsonArrayRequest)

    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item?.itemId == R.id.continueShoppingItem){

            var intent = Intent(this@CartProductsActivity, HomeScreen::class.java)
            startActivity(intent)

        }
        //delete icon pressed
        else if (item?.itemId == R.id.declineOrderItem){

            var deleteURL = "http://192.168.100.68/OnlineStoreApp/decline_order.php?email=${Person.email}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, deleteURL, Response.Listener { response ->

                var intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)



            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error Message!")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()


            })

            requestQ.add(stringRequest)

        }
        //verify icon pressed
        else if (item?.itemId == R.id.verifyOrderItem){


            var verifyOrderURL = "http://192.168.100.68/OnlineStoreApp/verify_order.php?email=${Person.email}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, verifyOrderURL, Response.Listener { response ->

                var intent = Intent(this, FinalizedShoppingActivity::class.java)
                //response is lates invoice num
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                intent.putExtra("LATEST_INVOICE_NUMBER", response)
                startActivity(intent)



            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error Message!")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()


            })

            requestQ.add(stringRequest)
        }

        return super.onOptionsItemSelected(item)
    }



}

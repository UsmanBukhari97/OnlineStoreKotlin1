package com.example.onlinestorekotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //url after runnnig fetch_brands php file
        var brandsURL = "http://192.168.100.68/OnlineStoreApp/fetch_brands.php"

        // a list to show brands
        var brandsList = ArrayList<String>()

        var requestQ = Volley.newRequestQueue(this@HomeScreen)

        //we want to get array of json obnjects
        //args = get, url var, json obj which is null, and rsponse listener
        var jsonArrayRequest  = JsonArrayRequest(Request.Method.GET, brandsURL, null, Response.Listener { response ->

            //when we get response we can get the array list
            //for loop will be excecuted with each json object inside the array
            for (jsonObject in 0.until(response.length())) {
                //brands list with json object
                //key will add the value to brands list
                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))
            }
            //controller
            var brandsListAdapter = ArrayAdapter(this@HomeScreen, R.layout.brands_item_textview, brandsList)
            //referring to list view id and calling adapter
            brandsListView.adapter = brandsListAdapter


        }, Response.ErrorListener { error ->


            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error Message!")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()


        })
        requestQ.add(jsonArrayRequest)



        //calling on item listener in brandslistview so that we can see products of brands
        //adapterview is the adapterview where the click happened
        // view is the view in the adapterview which is clicked
        // i = index or position, final arg is the id
        brandsListView.setOnItemClickListener { adapterView, view, i, l ->

            //getting the tapped brand
            val tappedBrand = brandsList.get(i)
            val intent = Intent(this@HomeScreen, FetchEProductsActivity::class.java)

            //passing tappedbrand to new activity
            //key is brand
            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)

        }

    }
}

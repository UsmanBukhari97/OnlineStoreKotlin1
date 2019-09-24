package com.example.onlinestorekotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_finalized_shopping.*

class FinalizedShoppingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalized_shopping)

        //key same as in cartprod activity of intent
        var calculatetotalPriceURL = "http://192.168.100.68/OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        var requestQ = Volley.newRequestQueue(this@FinalizedShoppingActivity)
        var stringRequest = StringRequest(Request.Method.GET, calculatetotalPriceURL, Response.Listener { response ->


            btnPaymentProcessing.text = "Pay $response via Paypal Now!"
            btnPaymentProcessing.setOnClickListener {

                var intent = Intent(this@FinalizedShoppingActivity, ThankYouActivity::class.java)
                startActivity(intent)
            }




        }, Response.ErrorListener { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Error Message!")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()


        })
            requestQ.add(stringRequest)

    }


}

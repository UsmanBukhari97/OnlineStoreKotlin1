package com.example.onlinestorekotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_btn_login.setOnClickListener {

            val loginURL = "http://192.168.100.68/OnlineStoreApp/login_app_user.php?email=" +
                    activity_main_edtLoginEmail.text.toString() +
                    "&pass=" + activity_main_edtLoginPassword.text.toString()

            val requestQ = Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.GET, loginURL, Response.Listener { response ->

                //same message as in login user php file
            if (response.equals("The user does exist")){

                Person.email = activity_main_edtLoginEmail.text.toString()
//allowed to login
                Toast.makeText(this@MainActivity, response, Toast.LENGTH_LONG).show()
                val homeIntent = Intent(this@MainActivity, HomeScreen::class.java)
                startActivity(homeIntent)

            } else{

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error Message!")
                dialogBuilder.setMessage(response)
                dialogBuilder.create().show()

            }


            }, Response.ErrorListener { error ->

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Error Message!")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()

            })
            requestQ.add(stringRequest)



        }

        activity_main_btn_Signup.setOnClickListener {

            var signUpIntent = Intent(this@MainActivity, SignUpLayoutActivity::class.java)
            startActivity(signUpIntent)

        }

    }
}

package com.example.onlinestorekotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up_layout.*

class SignUpLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_layout)


        signup_layout_btn_signup.setOnClickListener {

            //if pass = confirm pass
            if (signup_layout_edt_password.text.toString().equals(signup_layout_edt_confirmpassword.text.toString())){

                //registration process
                //same url like how we find if user exists for php in database but url here writing in android
                val signupURL = "http://192.168.100.68/OnlineStoreApp/join_new_user.php?email=" +
                        signup_layout_edt_email.text.toString()+
                        "&username=" +
                        signup_layout_edt_username.text.toString() + "&pass=" + signup_layout_edt_password.text.toString()

                val requestQ = Volley.newRequestQueue(this@SignUpLayoutActivity)
                val stringRequest = StringRequest(Request.Method.GET, signupURL, Response.Listener { response ->

                    //must be the same message as in php code in echo ''
                    if (response.equals("A user with same email address already exists")) {

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message!")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()

                    } else {

//                        val dialogBuilder = AlertDialog.Builder(this)
//                        dialogBuilder.setTitle("Success!")
//                        dialogBuilder.setMessage(response)
//                        dialogBuilder.create().show()
                        //keeping track of user
                        Person.email = signup_layout_edt_email.text.toString()

                        Toast.makeText(this@SignUpLayoutActivity, response, Toast.LENGTH_LONG).show()
                        val homeIntent = Intent(this@SignUpLayoutActivity, HomeScreen::class.java)
                        startActivity(homeIntent)


                    }




                }, Response.ErrorListener { error ->


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Error Message!")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()


                })
                requestQ.add(stringRequest)


            } else{

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Alert!")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()

            }

        }
        signup_layout_btn_login.setOnClickListener {
            //pressing login in signup activity to switch to login/main activity
            finish()

        }


    }
}

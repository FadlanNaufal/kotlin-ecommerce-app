package com.example.usmkpstore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_signup.setOnClickListener{
            var i = Intent(this,SignupActivity::class.java);
            startActivity(i);
        }

        btn_login.setOnClickListener {
            var url = "http://192.168.100.6/usmkp-api/login.php?mobile_phone="+txt_mobile_phone.text.toString()+
                    "&password="+txt_password.text.toString();
            var rq:RequestQueue = Volley.newRequestQueue(this);
            var rs = StringRequest(Request.Method.GET,url,Response.Listener {
                response ->
                if(response.equals("1")){
                    UserInfo.mobile = txt_mobile_phone.text.toString();
                    Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show();
                    var i = Intent(this,HomeActivity::class.java);
                    startActivity(i);
                }else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener {
                error ->
                Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
            })
            rq.add(rs);
        }
    }
}

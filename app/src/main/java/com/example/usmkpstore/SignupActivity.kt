package com.example.usmkpstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        txt_login.setOnClickListener {
            var i = Intent(this,MainActivity::class.java);
            startActivity(i);
        }

        btn_signup.setOnClickListener {
           if( reg_name.text.toString().equals("") || reg_mobile_phone.text.toString().equals("")
               || reg_confirm_password.text.toString().equals("") || reg_password.text.toString().equals("")
               || reg_address.text.toString().equals("")){
               Toast.makeText(this,"Complete your form first!",Toast.LENGTH_LONG).show();
           }else{
               if(reg_password.text.toString().equals(reg_confirm_password.text.toString())){
                   var url = "http://192.168.100.6/usmkp-api/register.php?name="+reg_name.text.toString()+
                           "&mobile_phone="+reg_mobile_phone.text.toString()+"&password="+reg_password.text.toString()+
                           "&address="+reg_address.text.toString();

                   var rq:RequestQueue = Volley.newRequestQueue(this)
                   var rs = StringRequest(Request.Method.GET,url, Response.Listener {
                           response ->
                       if(response.equals("1")){
                           var i = Intent(this,MainActivity::class.java);
                           startActivity(i);
                           Toast.makeText(this,"Register Success, you can login now",Toast.LENGTH_LONG).show();
                       }else{
                           Toast.makeText(this,"Mobile phone already used",Toast.LENGTH_LONG).show();
                       }
                   },Response.ErrorListener {
                           error ->
                       Toast.makeText(this,error.message,Toast.LENGTH_LONG).show();
                   })
                   rq.add(rs);
               }else{
                   Toast.makeText(this,"Password doesnt match!",Toast.LENGTH_LONG).show();
               }
           }


        }
    }
}

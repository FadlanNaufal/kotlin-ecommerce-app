package com.example.usmkpstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_home.*

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var url = "http://192.168.100.6/usmkp-api/get_temp_order.php?mobile_phone=" + UserInfo.mobile
        var list = ArrayList<String>()
        var rq: RequestQueue = Volley.newRequestQueue(this);
        var jar = JsonArrayRequest(Request.Method.GET,url,null, Response.Listener {
                response ->
            for (x in 0..response.length()-1)
                list.add("Item name : " + response.getJSONObject(x).getString("name") + "\n" +
                        "Item Price : " + response.getJSONObject(x).getString("price") + "\n" +
                        "Item Qty : " + response.getJSONObject(x).getString("qty")
                );
            var adp = ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
            cart_lv.adapter = adp;
        }, Response.ErrorListener {
                error ->
            Toast.makeText(this,error.message, Toast.LENGTH_LONG).show();
        })
        rq.add(jar);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.item_back_menu){
            var i = Intent(this,HomeActivity::class.java);
            startActivity(i);
        }else if(item?.itemId == R.id.item_confirm){

            var url = "http://192.168.100.6/usmkp-api/confirm_order.php?mobile_phone=" + UserInfo.mobile
            var rq:RequestQueue = Volley.newRequestQueue(this);
            var rs = StringRequest(Request.Method.GET,url,Response.Listener {
                    response ->
                Toast.makeText(this,"Order Success",Toast.LENGTH_LONG).show()
                var i = Intent(this,TotalActivity::class.java);
                i.putExtra("id",response)
                startActivity(i);

            },Response.ErrorListener {
                    error ->
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show();
            });
            rq.add(rs)

        }else if(item?.itemId == R.id.item_cancel) {
            var url = "http://192.168.100.6/usmkp-api/cancel_temp_order.php?mobile_phone="+UserInfo.mobile

            var rq:RequestQueue = Volley.newRequestQueue(this);
            var rs = StringRequest(Request.Method.GET,url,Response.Listener {
                    response ->
                Toast.makeText(this,"Cancel Success",Toast.LENGTH_LONG).show()
                var i = Intent(this,HomeActivity::class.java);
                startActivity(i);

            },Response.ErrorListener {
                    error ->
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show();
            });
            rq.add(rs)
        }
        return super.onOptionsItemSelected(item)
    }

}

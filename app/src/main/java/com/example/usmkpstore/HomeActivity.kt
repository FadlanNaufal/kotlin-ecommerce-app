package com.example.usmkpstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var url = "http://192.168.100.6/usmkp-api/get_category.php"
        var list = ArrayList<String>()
        var rq:RequestQueue = Volley.newRequestQueue(this);
        var jar = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener {
            response ->
            for (x in 0..response.length()-1)
                list.add(response.getJSONObject(x).getString("category"));
                var adp = ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
                listview_category.adapter = adp;
        }, Response.ErrorListener {
            error ->
            Toast.makeText(this,error.message,Toast.LENGTH_LONG).show();
        })
        rq.add(jar);

        listview_category.setOnItemClickListener { adapterView, view, i, l ->
            var cat:String = list[i]
            var obj = Intent(this,ItemActivity::class.java);
            obj.putExtra("cat",cat);
            startActivity(obj);
        }
    }
}

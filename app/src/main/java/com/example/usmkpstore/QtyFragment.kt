package com.example.usmkpstore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * A simple [Fragment] subclass.
 */
class QtyFragment : android.app.DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater!!.inflate(R.layout.fragment_qty, container, false)

        var et = v.findViewById<EditText>(R.id.et_qty);
        var bt = v.findViewById<Button>(R.id.btn_qty);

        bt.setOnClickListener {

            var url = "http://192.168.100.6/usmkp-api/add_temp_order.php?mobile_phone="+UserInfo.mobile+
            "&itemid="+UserInfo.itemid+"&qty="+et.text.toString();

            var rq:RequestQueue = Volley.newRequestQueue(activity);
            var rs = StringRequest(Request.Method.GET,url,Response.Listener {
                response ->

                var i = Intent(activity,CartActivity::class.java);
                startActivity(i);

            },Response.ErrorListener {
                error ->
                Toast.makeText(activity,error.message,Toast.LENGTH_LONG).show();
            });
            rq.add(rs)
        }

        return v;
    }

}

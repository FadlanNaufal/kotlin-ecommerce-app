package com.example.usmkpstore

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_total.*
import java.math.BigDecimal

class TotalActivity : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var amount:Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        var url = "http://192.168.100.6/usmkp-api/get_total_order.php?id=" + intent.getStringExtra("id")
        var rq: RequestQueue = Volley.newRequestQueue(this);
        var rs = StringRequest(Request.Method.GET,url, Response.Listener {
                response ->
            txt_total_order.text=response.toString();
        }, Response.ErrorListener {
                error ->
            Toast.makeText(this,error.message, Toast.LENGTH_LONG).show();
        });
        rq.add(rs)

        config=PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.clinet_id)

        var i = Intent(this,PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
        startService(i)

        btn_paypal.setOnClickListener {
            amount = txt_total_order.text.toString().toDouble();
            var payment = PayPalPayment(BigDecimal.valueOf(amount),"USD", "usmkp" , PayPalPayment.PAYMENT_INTENT_SALE );
            var intent = Intent(this,PaymentActivity::class.java);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
            startActivityForResult(intent,123)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            if(resultCode==Activity.RESULT_OK){
                var obj = Intent(this,ConfirmActivity::class.java)
                startActivity(obj)
            }
        }else{
            Toast.makeText(this,"Gagal",Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }
}

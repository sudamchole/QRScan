package com.example.sudam.qrscan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import org.json.JSONException
import com.example.sudam.qrscan.R.id.textViewName
import org.json.JSONObject
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler


class MainActivity : AppCompatActivity() {
    //View Objects
    private lateinit var mp: MediaPlayer
    private var qrScan: IntentIntegrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //intializing scan object
        mp = MediaPlayer.create (this, R.raw.audio_assist)
        qrScan = IntentIntegrator(this);

        //View objects

        buttonScan.setOnClickListener(View.OnClickListener {
            mp.stop()
            qrScan!!.initiateScan();
        })
        Handler().postDelayed({
            mp.start ()

        }, 3000)
    }
    fun web_page_open(urls: String) { // for more than one url
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }

    //Getting the scan results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            //if qrcode has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    val obj = JSONObject(result.contents)
                    //setting values to textviews
                  /*  web_page_open(obj.getString("url"))*/

                    val intent=Intent(this@MainActivity,GitActivity::class.java)
                    intent.putExtra("url",obj.getString("url"))
                    startActivity(intent)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

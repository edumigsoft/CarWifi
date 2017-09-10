package com.edumigrafa.carwifi

import android.app.Activity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)


        switch1.setOnClickListener {

            var led: String = if (switch1.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L1" + led).response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println(bytes)
                }

                text.setText("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }

        switch2.setOnClickListener {

            var led: String = if (switch2.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L2" + led).response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println(bytes)
                }

                text.setText("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }

        switch3.setOnClickListener {

            var led: String = if (switch3.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L3" + led).response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println(bytes)
                }

                text.setText("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }

    }

}

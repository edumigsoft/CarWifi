package com.edumigrafa.carwifi

import android.app.Activity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        text.setText("Lorem ipsum")


        button.setOnClickListener {

            var led: String = if (button.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/" + led).response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println(bytes)
                }

                text.setText(response.toString())

            }
        }


    }

}

package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)


        led1.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    action("L1", "1")
                }
                KeyEvent.ACTION_UP -> {
                    action("L1", "0")
                }
            }

            true
        }

        led2.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    action("L2", "1")
                }
                KeyEvent.ACTION_UP -> {
                    action("L2", "0")
                }
            }

            true
        }

        switch1.setOnClickListener {

            var led: String = if (switch1.isChecked) "1" else "0"

            Fuel.get("http://10.0.1.1/L1" + led).response { request, response, result ->
                println(request)
                println(response)
                println(result)
                val (bytes, error) = result
                if (bytes != null) {
                    println(bytes)
                }

                //text.text("Result = " + result.success
                        //+ "\n"
                        //+ "Response = " + response.httpResponseMessage.toString()
                        //+ "\n"
                        //+ "Request = " + request.toString()
                //)
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

                //text.text("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
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

                //text.text("Result = " + result.toString() + "\r" + "Response.Body = " + request.httpBody.toString())
            }
        }

    }

    fun action(led: String, act: String) {

        Fuel.get("http://10.0.1.1/" + led + act).response { request, response, result ->
            //println(request)
            //println(response)
            //println(result)
            //val (bytes, error) = result
            //if (bytes != null) {
            //    println(bytes)
            //}

            //text.text("Result = " + result.success
            //+ "\n"
            //+ "Response = " + response.httpResponseMessage.toString()
            //+ "\n"
            //+ "Request = " + request.toString()
            //)
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    var stable: Boolean = true;
    override fun onSensorChanged(event: SensorEvent?) {

        text.text = event!!.values.zip("XYZ".toList()).fold("") { acc, pair ->
            "$acc${pair.second}: ${pair.first}\n"
        }

        //text2.text = event!!.values.zip("Y".toList()).fold("Y") { acc, pair ->
        //    "$acc${pair.second}: ${pair.first}\n"
        //}

        //text.text = event!!.values.zip("Y".toList()).fold("") { acc, pair ->
        //    "$acc: ${pair.first}\n"
        //}

        //var str_ey: String = event!!.values.zip("X".toList()).fold("") { acc, pair ->
        //    "$acc: ${pair.first}\n"
        //}

        //println(str_ey)

        var float_ey: Float = event!!.values.get(1)
        //println(float_ey)
        text2.text = float_ey.toString()


        if (float_ey > -1.0 && float_ey < 1.0) {
            if (!stable) {
                action("L1", "0")
                action("L2", "0")
                stable = true
            }
        }

        if (float_ey > 1.0) {
            action("L1", "1")
            stable = false
        }

        if (float_ey < -1.0) {
            action("L2", "1")
            stable = false
        }

    }
}

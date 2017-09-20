package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.RadioButton
import com.edumigrafa.carwifi.logic.CarWiFi
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    var carWifi: CarWiFi? = null
    var breakFlag: Boolean = true
    var frontBack: Boolean = true
    var pwmSpeed: Int = 0

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    //Move to front or back
    fun onClickRB(rb: RadioButton) {

        //Não aciona outra marcha se não tiver apertado o pedal de freio, exceto a marcha P
        if (breakFlag && rb != radio_button_gear_P) {
            radio_button_gear_P.isChecked = true
            radio_button_gear_P.callOnClick()
            return
        }

        when(rb) {
            radio_button_gear_1 -> {
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false
                radio_button_gear_P.isChecked = false

                frontBack = true
                pwmSpeed = 1
            }
            radio_button_gear_2 -> {
                //if (radio_button_gear_1.isChecked) {
                    radio_button_gear_1.isChecked = false
                    radio_button_gear_3.isChecked = false
                    radio_button_gear_4.isChecked = false
                    radio_button_gear_R.isChecked = false
                    radio_button_gear_P.isChecked = false

                    frontBack = true
                    pwmSpeed = 2
                    //} else {
                    //    radio_button_gear_2.isChecked = false
                    //    radio_button_gear_2.callOnClick()
                    //}
            }
            radio_button_gear_3 -> {
                //if (radio_button_gear_1.isChecked) {
                    radio_button_gear_1.isChecked = false
                    radio_button_gear_2.isChecked = false
                    radio_button_gear_4.isChecked = false
                    radio_button_gear_R.isChecked = false
                    radio_button_gear_P.isChecked = false

                    frontBack = true
                    pwmSpeed = 3
                    //} else {
                    //    radio_button_gear_3.isChecked = false
                    //    radio_button_gear_3.callOnClick()
                    //}
            }
            radio_button_gear_4 -> {
                //if (radio_button_gear_1.isChecked) {
                    radio_button_gear_1.isChecked = false
                    radio_button_gear_2.isChecked = false
                    radio_button_gear_3.isChecked = false
                    radio_button_gear_R.isChecked = false
                    radio_button_gear_P.isChecked = false

                    frontBack = true
                    pwmSpeed = 4
                    //} else {
                    //    radio_button_gear_4.isChecked = false
                    //    radio_button_gear_4.callOnClick()
                    //}
            }
            radio_button_gear_R -> {
                //if (radio_button_gear_1.isChecked || radio_button_gear_P.isChecked) {
                    radio_button_gear_1.isChecked = false
                    radio_button_gear_2.isChecked = false
                    radio_button_gear_3.isChecked = false
                    radio_button_gear_4.isChecked = false
                    radio_button_gear_P.isChecked = false

                    // Limit 2
                    frontBack = false
                    pwmSpeed = 1


                //} else {
                    //    radio_button_gear_R.isChecked = false
                    //    radio_button_gear_R.callOnClick()
                    //}
            }
            radio_button_gear_P -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false

                breakFlag = true
                frontBack = true
                pwmSpeed = 0

                carWifi!!.actionBack(0)
                // Or
                //carWifi!!.actionFront(0)


            }
            //
            //
            //
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //setContentView(R.layout.fragment_blank)

        carWifi = CarWiFi(this)

        carWifi!!.resetActionDirection()

        //radio_button_gear_1.setOnClickListener({ view -> onClickRB(radio_button_gear_1) })
        radio_button_gear_1.setOnClickListener({ onClickRB(radio_button_gear_1) })
        //radio_button_gear_2.setOnClickListener({ view -> onClickRB(radio_button_gear_2) })
        radio_button_gear_2.setOnClickListener({ onClickRB(radio_button_gear_2) })
        //radio_button_gear_3.setOnClickListener({ view -> onClickRB(radio_button_gear_3) })
        radio_button_gear_3.setOnClickListener({ onClickRB(radio_button_gear_3) })
        //radio_button_gear_4.setOnClickListener({ view -> onClickRB(radio_button_gear_4) })
        radio_button_gear_4.setOnClickListener({ onClickRB(radio_button_gear_4) })
        //radio_button_gear_R.setOnClickListener({ view -> onClickRB(radio_button_gear_R) })
        radio_button_gear_R.setOnClickListener({ onClickRB(radio_button_gear_R) })
        //radio_button_gear_P.setOnClickListener({ view -> onClickRB(radio_button_gear_P) })
        radio_button_gear_P.setOnClickListener({ onClickRB(radio_button_gear_P) })

        btn_gyroflex.setOnClickListener {
            carWifi!!.flasherGyroflex()
        }

        switch_buzzer.setOnClickListener {
            carWifi!!.onOffBuzzer()
        }

        img_button_car_headlight_left.setOnClickListener {
            carWifi!!.onOffCarHeadlight()
        }

        img_button_car_headlight_right.setOnClickListener {
            carWifi!!.onOffCarHeadlight()
        }

        image_button_break.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!radio_button_gear_P.isChecked) {
                        radio_button_gear_P.isChecked = true
                        radio_button_gear_P.callOnClick()
                    } else {
                        breakFlag = false
                    }
                }
                KeyEvent.ACTION_UP -> {
                    //
                    if (radio_button_gear_P.isChecked) {
                        breakFlag = true
                    }
                }
            }

            true
        }

        image_button_accelerator.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!radio_button_gear_P.isChecked) {
                        if (frontBack) {
                            carWifi!!.actionFront(pwmSpeed)
                        } else {
                            carWifi!!.actionBack(pwmSpeed)
                        }
                    }
                }
                KeyEvent.ACTION_UP -> {
                    pwmSpeed = 0
                    carWifi!!.actionBack(0)
                    // Or
                    //carWifi!!.actionFront(0)
                }
            }

            true
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL

                //SENSOR_DELAY_FASTEST: retorna os dados do sensor o mais rápido possível;
                //SENSOR_DELAY_GAME: utiliza uma taxa adequada para jogos;
                //SENSOR_DELAY_NORMAL: taxa adequada para mudanças na orientação da tela;
                //SENSOR_DELAY_UI: taxa adequada para a interface de usuário.
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        carWifi!!.actionDirection(event)
    }
}

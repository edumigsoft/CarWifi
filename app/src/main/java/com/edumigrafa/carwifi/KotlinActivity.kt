package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageButton
import com.edumigrafa.carwifi.logic.CarWiFi
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    var carWifi: CarWiFi? = null
    var breakFlag: Boolean = true
    var frontBack: Boolean = true
    var pwmSpeed: Int = 0
    var selectedCarGear: ImageButton? = null

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    //Move to front or back
    fun onClickIB(btn: ImageButton) {

        //Não aciona outra marcha se não tiver apertado o pedal de freio, exceto a marcha P
        if (breakFlag && !selectedCarGear?.equals(imgBtnGearP)!!) {
            selectedCarGear = imgBtnGearP
            imgBtnGearP.callOnClick()
            return
        }

        selectedCarGear?.isSelected = false
        when(btn) {
            imgBtnGearP -> {
                selectedCarGear = imgBtnGearP
                selectedCarGear?.isSelected = true
//                radio_button_gear_1.isChecked = false
//                radio_button_gear_2.isChecked = false
//                radio_button_gear_3.isChecked = false
//                radio_button_gear_4.isChecked = false
//                radio_button_gear_R.isChecked = false

//                breakFlag = true
//                frontBack = true
//                pwmSpeed = 0

//                carWifi!!.actionBack(0)
        // Or
        //carWifi!!.actionFront(0)
            }
            imgBtnGear1 -> {
                selectedCarGear = imgBtnGear1
                selectedCarGear?.isSelected = true
                //if (radio_button_gear_P.isChecked || radio_button_gear_2.isChecked) {
//                    radio_button_gear_2.isChecked = false
//                    radio_button_gear_3.isChecked = false
//                    radio_button_gear_4.isChecked = false
//                    radio_button_gear_R.isChecked = false
//                    radio_button_gear_P.isChecked = false

//                    frontBack = true
//                    pwmSpeed = 1
                //}
            }
//            radio_button_gear_2 -> {
//                if (radio_button_gear_1.isChecked || radio_button_gear_3.isChecked) {
//                    radio_button_gear_1.isChecked = false
//                    radio_button_gear_3.isChecked = false
//                    radio_button_gear_4.isChecked = false
//                    radio_button_gear_R.isChecked = false
//                    radio_button_gear_P.isChecked = false

//                    frontBack = true
//                    pwmSpeed = 2
//                }
                    //} else {
                    //    radio_button_gear_2.isChecked = false
                    //    radio_button_gear_2.callOnClick()
                    //}
//            }
//            radio_button_gear_3 -> {
                //if (radio_button_gear_2.isChecked || radio_button_gear_4.isChecked) {
//                    radio_button_gear_1.isChecked = false
//                    radio_button_gear_2.isChecked = false
//                    radio_button_gear_4.isChecked = false
//                    radio_button_gear_R.isChecked = false
//                    radio_button_gear_P.isChecked = false

//                    frontBack = true
//                    pwmSpeed = 3
                //}
                    //} else {
                    //    radio_button_gear_3.isChecked = false
                    //    radio_button_gear_3.callOnClick()
                    //}
//            }
//            radio_button_gear_4 -> {
                //if (radio_button_gear_3.isChecked) {
//                    radio_button_gear_1.isChecked = false
//                    radio_button_gear_2.isChecked = false
//                    radio_button_gear_3.isChecked = false
//                    radio_button_gear_R.isChecked = false
//                    radio_button_gear_P.isChecked = false

//                    frontBack = true
//                    pwmSpeed = 4
                //}
                    //} else {
                    //    radio_button_gear_4.isChecked = false
                    //    radio_button_gear_4.callOnClick()
                    //}
//            }
//            radio_button_gear_R -> {
//                if (radio_button_gear_P.isChecked) {
//                    radio_button_gear_1.isChecked = false
//                    radio_button_gear_2.isChecked = false
//                    radio_button_gear_3.isChecked = false
//                    radio_button_gear_4.isChecked = false
//                    radio_button_gear_P.isChecked = false

                    // Limit 2
//                    frontBack = false
//                    pwmSpeed = 1
//                } else {
//                    radio_button_gear_P.isChecked = true
//                    radio_button_gear_P.callOnClick()
//                }
//            }
            //
        }

        //selectedCarGear?.isSelected = true
    }

    //    var selected: Boolean = false
//    var selected2: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //setContentView(R.layout.fragment_blank)

//        imgBtnGearP.setOnClickListener {
//            imgBtnGearP.isSelected = !selected
//            selected = !selected
//        }

//        imgBtnGear1.setOnClickListener {
//            imgBtnGear1.isSelected = !selected2
//            selected2 = !selected2
//        }

        carWifi = CarWiFi(this)

        carWifi?.resetActionDirection()

        imgBtnGearP.setOnClickListener({ onClickIB(imgBtnGearP) })
        imgBtnGear1.setOnClickListener({ onClickIB(imgBtnGear1) })
        imgBtnGear2.setOnClickListener({ onClickIB(imgBtnGear2) })
        imgBtnGear3.setOnClickListener({ onClickIB(imgBtnGear3) })
        imgBtnGear4.setOnClickListener({ onClickIB(imgBtnGear4) })
        imgBtnGearR.setOnClickListener({ onClickIB(imgBtnGearR) })

        selectedCarGear = imgBtnGearP
        imgBtnGearP.callOnClick()

        btnGyroflex.setOnClickListener {
            carWifi?.flasherGyroflex()
        }

        shBuzzer.setOnClickListener {
            carWifi?.onOffBuzzer()
        }

        imgBtnCarHeadlightLeft.setOnClickListener {
            carWifi?.onOffCarHeadlight()
        }

        imgBtnCarHeadlightRight.setOnClickListener {
            carWifi?.onOffCarHeadlight()
        }

        imgBtnBreak.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!selectedCarGear?.equals(imgBtnGearP)!!) {
                        selectedCarGear?.isSelected = false
                        selectedCarGear = imgBtnGearP
                        imgBtnGearP.callOnClick()

                    } else {
                        breakFlag = false
                    }
                }
                KeyEvent.ACTION_UP -> {
                    if (selectedCarGear?.equals(imgBtnGearP)!!) {
                        breakFlag = true
                    }
                }
            }

            true
        }

        imgBtnAccelerator.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!selectedCarGear?.equals(imgBtnGearP)!!) {
                        if (frontBack) {
                            carWifi?.actionFront(pwmSpeed)
                        } else {
                            carWifi?.actionBack(pwmSpeed)
                        }
                    }
                }
                KeyEvent.ACTION_UP -> {
                    pwmSpeed = 0
                    carWifi?.actionBack(0)
                    // Or
                    //carWifi?.actionFront(0)
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
        carWifi?.actionDirection(event)
    }
}

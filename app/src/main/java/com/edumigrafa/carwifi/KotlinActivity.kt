package com.edumigrafa.carwifi

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    //var carWifi: CarWiFi? = null

    //val sensorManager: SensorManager by lazy {
    //    getSystemService(Context.SENSOR_SERVICE) as SensorManager
    //}


    //Move to front or back
    fun onClickRB(rb: RadioButton) {
        when(rb) {
            radio_button_gear_1 -> {
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false
                radio_button_gear_P.isChecked = false
                //
                //
                //
            }
            radio_button_gear_2 -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false
                radio_button_gear_P.isChecked = false
                //
                //
                //
            }
            radio_button_gear_3 -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_2.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false
                radio_button_gear_P.isChecked = false
                //
                //
                //
            }
            radio_button_gear_4 -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_R.isChecked = false
                radio_button_gear_P.isChecked = false
                //
                //
                //
            }
            radio_button_gear_R -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_P.isChecked = false
                //
                //
                //
            }
            radio_button_gear_P -> {
                radio_button_gear_1.isChecked = false
                radio_button_gear_2.isChecked = false
                radio_button_gear_3.isChecked = false
                radio_button_gear_4.isChecked = false
                radio_button_gear_R.isChecked = false
                //
                //
                //
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

        radio_button_gear_1.setOnClickListener({ view -> onClickRB(radio_button_gear_1) })
        radio_button_gear_2.setOnClickListener({ view -> onClickRB(radio_button_gear_2) })
        radio_button_gear_3.setOnClickListener({ view -> onClickRB(radio_button_gear_3) })
        radio_button_gear_4.setOnClickListener({ view -> onClickRB(radio_button_gear_4) })
        radio_button_gear_R.setOnClickListener({ view -> onClickRB(radio_button_gear_R) })
        radio_button_gear_P.setOnClickListener({ view -> onClickRB(radio_button_gear_P) })


/*
        carWifi = CarWiFi(this)

        carWifi!!.resetActionDirection()

        btn_gyroflex.setOnClickListener{
            carWifi!!.flasherGyroflex()
        }

        switch_buzzer.setOnClickListener{
            carWifi!!.onOffBuzzer()
        }

        img_button_car_headlight_left.setOnClickListener{
            carWifi!!.onOffCarHeadlight()
        }

        img_button_car_headlight_right.setOnClickListener{
            carWifi!!.onOffCarHeadlight()
        }

        image_button_accelerator.setOnClickListener{
            when (radio_group_car_gear.checkedRadioButtonId) {
                //toggle_button_gear_1 -> {

                //}
            }

        }

        image_button_break.setOnClickListener{
        }

/*
        //@TODO Quando tiver mais opções de velocidade, implementar como exclusivo ou seekBar
        button_back_1.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    carWifi!!.actionBack(1)
                }
                KeyEvent.ACTION_UP -> {
                    carWifi!!.actionBack(0)
                }
            }

            true
        }

        seekBar_accelerator.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            var seekBarProgress: Int = 0

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //toast("Progress: " + seekBarProgress + " / " + seekBar.getMax())
                carWifi!!.actionFront(seekBarProgress)

                //Quando solta o componente
                seekBar_accelerator.progress = 0
                seekBarProgress = 0
                carWifi!!.actionFront(seekBarProgress)
            }
        })*/*/
    }

    override fun onResume() {
        super.onResume()
        /*sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL

                //SENSOR_DELAY_FASTEST: retorna os dados do sensor o mais rápido possível;
                //SENSOR_DELAY_GAME: utiliza uma taxa adequada para jogos;
                //SENSOR_DELAY_NORMAL: taxa adequada para mudanças na orientação da tela;
                //SENSOR_DELAY_UI: taxa adequada para a interface de usuário.
        )*/
    }

    override fun onPause() {
        super.onPause()
        //sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //carWifi!!.actionDirection(event)
    }
}

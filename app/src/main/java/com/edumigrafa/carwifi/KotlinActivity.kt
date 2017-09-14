package com.edumigrafa.carwifi

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.SeekBar
import com.edumigrafa.carwifi.logic.CarWiFi
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity() : Activity(), SensorEventListener {

    var carWifi: CarWiFi? = null

    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        carWifi = CarWiFi(this)

        carWifi!!.resetActionDirection()

        btn_gyroflex.setOnClickListener{
            carWifi!!.flasherGyroflex()
        }

        switch_buzzer.setOnClickListener{
            carWifi!!.onOffBuzzer()
        }

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
        })
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

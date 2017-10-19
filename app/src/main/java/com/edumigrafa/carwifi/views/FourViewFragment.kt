package com.edumigrafa.carwifi.views

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.carwifi.*
import kotlinx.android.synthetic.main.fragment_view_four.*

/**
 * Created by anderson on 12/10/17.
 */
class FourViewFragment : Fragment(), SensorEventListener, View.OnTouchListener {

    val TAG = "Four View Fragment"
    val sensorManager: SensorManager by lazy {
        activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    var carWifi: CarWiFi? = null
    var stable: Boolean = true

    //override fun onAttach(context: Context?) {
    //    Log.d(TAG, "onAttach")
    //    super.onAttach(context)
    //}

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        carWifi = CarWiFi(activity as AppActivity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater!!.inflate(R.layout.fragment_view_four, container, false)
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    Log.d(TAG, "onActivityCreated")
    //    super.onActivityCreated(savedInstanceState)
    //}

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()

        ivFront.setOnTouchListener(this)
        ivFront2.setOnTouchListener(this)
        ivFront3.setOnTouchListener(this)
        ivFront4.setOnTouchListener(this)
        ivBack.setOnTouchListener(this)

        tgbBuzzer.isSelected = false
        tgbGyroflex.isSelected = false
        tgbHeadlight.isSelected = false

        tgbBuzzer.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            carWifi!!.onOffBuzzer(b)
        })

        tgbGyroflex.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            carWifi!!.onOffGyroflex(b)
        })

        tgbHeadlight.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            carWifi!!.onOffHeadlight(b)
        })

    }

    override fun onResume() {
        Log.d(TAG, "onResume")
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
        Log.d(TAG, "onPause")
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    //override fun onStop() {
    //    Log.d(TAG, "onStop")
    //    super.onStop()
    //}

    //override fun onDestroyView() {
    //    Log.d(TAG, "onDestroyView")
    //    super.onDestroyView()
    //}

    //override fun onDestroy() {
    //    Log.d(TAG, "onDestroy")
    //    super.onDestroy()
    //}

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var float_ex: Float = event!!.values.get(0)
        var float_ey: Float = event!!.values.get(1)
        //println(float_ey)
        var float_ez: Float = event!!.values.get(2)

        var str: String = ""
        str += "Posição X: " + float_ex.toString()
        str += "\n"
        str += "Posição Y: " + float_ey.toString()
        str += "\n"
        str += "Posição Z: " + float_ez.toString()
        //text.setText(str);
        /*
        if(float_ey < 0) { // O dispositivo esta de cabeça pra baixo
            if(float_ex > 0)
                activity!!.text2.setText("Virando para ESQUERDA ficando INVERTIDO");
            if(float_ex < 0)
                activity!!.text2.setText("Virando para DIREITA ficando INVERTIDO");
        } else {
            if(float_ex > 0)
                activity!!.text2.setText("Virando para ESQUERDA ");
            if(float_ex < 0)
                activity!!.text2.setText("Virando para DIREITA ");
        }
        */
        //text2.text = float_ey.toString()

        if (float_ex < LIMIT_X_1 || float_ex > LIMIT_X_2 && !stable) {
            resetActionDirection()
            stable = true
            return
        }

        if (float_ey > LIMIT_Y_1 && float_ey < LIMIT_Y_2) {
            if (!stable) {
                resetActionDirection()
                stable = true
            }
        }

        if (float_ey > LIMIT_Y_2) {
            carWifi!!.directionLeftRight(DIRECTION_RIGHT)
            ivRight.visibility = View.VISIBLE
            stable = false
        }

        if (float_ey < LIMIT_Y_1) {
            carWifi!!.directionLeftRight(DIRECTION_LEFT)
            ivLeft.visibility = View.VISIBLE
            stable = false
        }
    }

    fun resetActionDirection() {
        carWifi!!.directionLeftRight(DIRECTION)

        ivLeft.visibility = View.INVISIBLE
        ivRight.visibility = View.INVISIBLE
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
            when (motionEvent!!.action) {
                KeyEvent.ACTION_DOWN -> {
                    when(view!!) {
                        ivFront -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION_FRONT_1)
                            }
                        }
                        ivFront2 -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION_FRONT_2)
                            }
                        }
                        ivFront3 -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION_FRONT_3)
                            }
                        }
                        ivBack -> {
                            if (!ivFront.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION_BACK)
                            }
                        }
                    }
                }
                KeyEvent.ACTION_UP -> {
                    when(view!!) {
                        ivFront -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION)
                            }
                        }
                        ivFront2 -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION)
                            }
                        }
                        ivFront3 -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION)
                            }
                        }
                        ivBack -> {
                            if (!ivFront.isSelected) {
                                carWifi!!.directionFrontBack(DIRECTION)
                            }
                        }
                    }
                }
            }

        return true
    }
}
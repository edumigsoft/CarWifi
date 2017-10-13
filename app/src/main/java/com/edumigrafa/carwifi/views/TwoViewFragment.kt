package com.edumigrafa.carwifi.views

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.ImageView
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.logic.*
import kotlinx.android.synthetic.main.fragment_view_two.*

/**
 * Created by anderson on 12/10/17.
 */
class TwoViewFragment() : Fragment(), SensorEventListener, View.OnClickListener {

    val TAG = "Two View Fragment"
    val sensorManager: SensorManager by lazy {
        activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    var carWifi: CarWiFi? = null
    var stable: Boolean = true
    var flashlight: Boolean = false
    var light_gyroflex: Boolean = false
    var car_headlight: Boolean = false
    var selectedCarGear: ImageButton? = null
    var breakFlag: Boolean = false
    var direction_speed: String = DIRECTION

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
        return inflater!!.inflate(R.layout.fragment_view_two, container, false)
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    Log.d(TAG, "onActivityCreated")
    //    super.onActivityCreated(savedInstanceState)
    //}

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()

        imgBtnGearP.setOnClickListener(this)
        imgBtnGear1.setOnClickListener(this)
        imgBtnGear2.setOnClickListener(this)
        imgBtnGear3.setOnClickListener(this)
        imgBtnGear4.setOnClickListener(this)
        imgBtnGearR.setOnClickListener(this)

        imgBtnGearP.callOnClick()

        imgBtnBreak.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!selectedCarGear?.equals(imgBtnGearP)!!) {
                        imgBtnGearP.callOnClick()
                    } else {
                        breakFlag = true
                    }
                }
                KeyEvent.ACTION_UP -> {
                    breakFlag = false
                }
            }

            true
        }

        imgBtnAccelerator.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (!selectedCarGear?.equals(imgBtnGearP)!!) {
                        carWifi!!.directionFrontBack(direction_speed)
                        text2.text = direction_speed
                    }
                }
                KeyEvent.ACTION_UP -> {
                    if (!selectedCarGear?.equals(imgBtnGearP)!!) {
                        imgBtnGearP.callOnClick()
                    }
                }
            }

            true
        }

        btnGyroflex.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(activity, R.anim.blink)

            if (!light_gyroflex) {
                imgLightGyroflex.startAnimation(animation)
            } else {
                imgLightGyroflex.clearAnimation()
            }

            light_gyroflex = light_gyroflex.not()
            carWifi!!.onOffGyroflex(light_gyroflex)
        }

        shBuzzer.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            carWifi!!.onOffBuzzer(b)
        })

        imgBtnCarHeadlightLeft.setOnClickListener {
            onOffCarHeadlight()
        }

        imgBtnCarHeadlightRight.setOnClickListener {
            onOffCarHeadlight()
        }
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

        if (float_ey > LIMITNEGATIVE_Y && float_ey < LIMITPOSITIVE_Y) {
            if (!stable) {
                resetActionDirection()
                stable = true
            }
        }

        if (float_ey > LIMITPOSITIVE_Y) {
            carWifi!!.directionLeftRight("1")
            imgRight.visibility = View.VISIBLE
            flasherFlashlight(imgFlashlightRight, true)
            stable = false
        }

        if (float_ey < LIMITNEGATIVE_Y) {
            carWifi!!.directionLeftRight("-1")
            imgLeft.visibility = View.VISIBLE
            flasherFlashlight(imgFlashelightLeft, true)
            stable = false
        }
    }

    fun resetActionDirection() {
        carWifi!!.directionLeftRight("0")

        imgLeft.visibility = View.INVISIBLE
        imgRight.visibility = View.INVISIBLE

        flasherFlashlight(imgFlashelightLeft, false)
        flasherFlashlight(imgFlashlightRight, false)
    }

    //@TODO Implementar vibração
    // act = true >> liga || act = false >> desliga
    fun flasherFlashlight(img: ImageView, act: Boolean) {
        if (flashlight == act && act) {return}

        val animation = AnimationUtils.loadAnimation(activity, R.anim.blink_2)

        if (act) {
            img.startAnimation(animation)
        } else {
            img.clearAnimation()
        }

        flashlight = act
    }

    fun onOffCarHeadlight() {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.blink)

        if (!car_headlight) {
            imgBtnCarHeadlightLeft.startAnimation(animation)
            imgBtnCarHeadlightRight.startAnimation(animation)
        } else {
            imgBtnCarHeadlightLeft.clearAnimation()
            imgBtnCarHeadlightRight.clearAnimation()
        }

        carWifi!!.onOffHeadlight(car_headlight)
        car_headlight = car_headlight.not()
    }

    //Move to front or back
    override fun onClick(view: View?) {

        when(view) {
            imgBtnGearP -> {
                selectedCarGear?.isSelected = false
                selectedCarGear = view as ImageButton?
                selectedCarGear?.isSelected = true

                breakFlag = false

                direction_speed = "0"
                carWifi!!.directionFrontBack(direction_speed)
                text2.text = direction_speed
            }
            imgBtnGear1 -> {
                if (breakFlag || selectedCarGear?.equals(imgBtnGear2)!!) {
                    selectedCarGear?.isSelected = false
                    selectedCarGear = view as ImageButton?
                    selectedCarGear?.isSelected = true

                    if (!direction_speed.equals("0") && !breakFlag) {
                        direction_speed = "1"
                        carWifi!!.directionFrontBack(direction_speed)
                        text2.text = direction_speed
                    } else {
                        direction_speed = "1"
                    }
                }
            }
            imgBtnGear2 -> {
                if ((selectedCarGear?.equals(imgBtnGear1)!! ||
                        selectedCarGear?.equals(imgBtnGear3)!!) &&
                            !direction_speed.equals("0") &&
                                !breakFlag) {
                    selectedCarGear?.isSelected = false
                    selectedCarGear = view as ImageButton?
                    selectedCarGear?.isSelected = true

                    direction_speed = "2"
                    carWifi!!.directionFrontBack(direction_speed)
                    text2.text = direction_speed
                }
            }
            imgBtnGear3 -> {
                if ((selectedCarGear?.equals(imgBtnGear2)!! ||
                        selectedCarGear?.equals(imgBtnGear4)!!) &&
                            !direction_speed.equals("0") &&
                                !breakFlag) {
                    selectedCarGear?.isSelected = false
                    selectedCarGear = view as ImageButton?
                    selectedCarGear?.isSelected = true

                    direction_speed = "3"
                    carWifi!!.directionFrontBack(direction_speed)
                    text2.text = direction_speed
                }
            }
            imgBtnGear4 -> {
                if (selectedCarGear?.equals(imgBtnGear3)!! &&
                        !direction_speed.equals("0") &&
                            !breakFlag) {
                    selectedCarGear?.isSelected = false
                    selectedCarGear = view as ImageButton?
                    selectedCarGear?.isSelected = true

                    direction_speed = "4"
                    carWifi!!.directionFrontBack(direction_speed)
                    text2.text = direction_speed
                }
            }
            imgBtnGearR -> {
                if (breakFlag) {
                    selectedCarGear?.isSelected = false
                    selectedCarGear = view as ImageButton?
                    selectedCarGear?.isSelected = true

                    if (!direction_speed.equals("0")) {
                        direction_speed = "-1"
                        carWifi!!.directionFrontBack(direction_speed)
                        text2.text = direction_speed
                    } else {
                        direction_speed = "-1"
                    }
                }
            }
        }
    }

}

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
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.ImageView
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.logic.*
import kotlinx.android.synthetic.main.fragment_view_two.*

/**
 * Created by anderson on 12/10/17.
 */
class TwoViewFragment() : Fragment(), View.OnTouchListener, SensorEventListener {

    val TAG = "Two View Fragment"
    val sensorManager: SensorManager by lazy {
        activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    var carWifi: CarWiFi? = null
    var stable: Boolean = true
    var flashlight: Boolean = false
    var light_gyroflex: Boolean = false
    var car_headlight: Boolean = false

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

        //ivLeft.setOnTouchListener(this)
        //ivRight.setOnTouchListener(this)
        //ivFront.setOnTouchListener(this)
        //ivBack.setOnTouchListener(this)

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

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
            when (motionEvent!!.action) {
                KeyEvent.ACTION_DOWN -> {
                    when(view!!) {
                        //ivLeft -> {
                        //    if (!ivRight.isSelected) {
                        //        carWifi!!.directionLeftRight(DIRECTION_LEFT)
                        //    }
                        //}
                        //ivRight -> {
                        //    if (!ivLeft.isSelected) {
                        //        carWifi!!.directionLeftRight(DIRECTION_RIGHT)
                        //    }
                        //}
                        //ivFront -> {
                        //    if (!ivBack.isSelected) {
                        //        carWifi!!.directionFrontBack("1")
                        //    }
                        //}
                        //ivBack -> {
                        //    if (!ivFront.isSelected) {
                        //        carWifi!!.directionFrontBack("-1")
                        //    }
                        //}
                    }
                }
                KeyEvent.ACTION_UP -> {
                    when(view!!) {
                        //ivLeft -> {
                        //    if (!ivRight.isSelected) {
                        //        carWifi!!.directionLeftRight(DIRECTION)
                        //    }
                        //}
                        //ivRight -> {
                        //    if (!ivLeft.isSelected) {
                        //        carWifi!!.directionLeftRight(DIRECTION)
                        //    }
                        //}
                        //ivFront -> {
                        //    if (!ivBack.isSelected) {
                        //        carWifi!!.directionFrontBack(DIRECTION)
                        //    }
                        //}
                        //ivBack -> {
                        //    if (!ivFront.isSelected) {
                        //        carWifi!!.directionFrontBack(DIRECTION)
                        //    }
                        //}
                    }
                }
            }

        return true
    }

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
        text.setText(str);
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
        text2.text = float_ey.toString()

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

}


/*
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

//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_app_2)

//    ShowOneViewFragment()


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


*/
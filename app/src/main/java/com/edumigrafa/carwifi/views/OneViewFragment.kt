package com.edumigrafa.carwifi.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.logic.CarWiFi
import com.edumigrafa.carwifi.logic.PIN_DIRECTION
import kotlinx.android.synthetic.main.fragment_view_one.*

/**
 * Created by anderson on 12/10/17.
 */
class OneViewFragment() : Fragment(), OnTouchListener {

    val TAG = "One View Fragment"
    var carWifi: CarWiFi? = null

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
        return inflater!!.inflate(R.layout.fragment_view_one, container, false)
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    Log.d(TAG, "onActivityCreated")
    //    super.onActivityCreated(savedInstanceState)
    //}

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()

        ivLeft.setOnTouchListener(this)
        ivRight.setOnTouchListener(this)
        ivFront.setOnTouchListener(this)
        ivBack.setOnTouchListener(this)


    }

    //override fun onResume() {
    //    Log.d(TAG, "onResume")
    //    super.onResume()
    //}

    //override fun onPause() {
    //    Log.d(TAG, "onPause")
    //    super.onPause()
    //}

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
                        ivLeft -> {
                            if (!ivRight.isSelected) {
                                carWifi!!.directionLeftRight(true)
                            }
                        }
                        ivRight -> {
                            if (!ivLeft.isSelected) {
                                carWifi!!.directionLeftRight(false)
                            }
                        }
                        ivFront -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.execHttp(PIN_DIRECTION + "1")
                            }
                        }
                        ivBack -> {
                            if (!ivFront.isSelected) {
                                carWifi!!.execHttp(PIN_DIRECTION + "-1")
                            }
                        }
                    }
                }
                KeyEvent.ACTION_UP -> {
                    when(view!!) {
                        ivLeft -> {
                            if (!ivRight.isSelected) {
                                carWifi!!.directionLeftRight(true, true)
                            }
                        }
                        ivRight -> {
                            if (!ivLeft.isSelected) {
                                carWifi!!.directionLeftRight(false, true)
                            }
                        }
                        ivFront -> {
                            if (!ivBack.isSelected) {
                                carWifi!!.execHttp(PIN_DIRECTION + "0")
                            }
                        }
                        ivBack -> {
                            if (!ivFront.isSelected) {
                                carWifi!!.execHttp(PIN_DIRECTION + "0")
                            }
                        }
                    }
                }
            }

        return true
    }
}

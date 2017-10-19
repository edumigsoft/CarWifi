package com.edumigrafa.carwifi.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.carwifi.*
import kotlinx.android.synthetic.main.fragment_view_tree.*

/**
 * Created by anderson on 12/10/17.
 */
class TreeViewFragment : Fragment(), View.OnTouchListener {

    val TAG = "Tree View Fragment"
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
        return inflater!!.inflate(R.layout.fragment_view_tree, container, false)
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
        ivFront2.setOnTouchListener(this)
        ivFront3.setOnTouchListener(this)
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
                                carWifi!!.directionLeftRight(DIRECTION_LEFT)
                            }
                        }
                        ivRight -> {
                            if (!ivLeft.isSelected) {
                                carWifi!!.directionLeftRight(DIRECTION_RIGHT)
                            }
                        }
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
                        ivLeft -> {
                            if (!ivRight.isSelected) {
                                carWifi!!.directionLeftRight(DIRECTION)
                            }
                        }
                        ivRight -> {
                            if (!ivLeft.isSelected) {
                                carWifi!!.directionLeftRight(DIRECTION)
                            }
                        }
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
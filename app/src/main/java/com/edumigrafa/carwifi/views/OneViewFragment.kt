package com.edumigrafa.carwifi.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.edumigrafa.carwifi.AppActivity
import com.edumigrafa.carwifi.R
import com.edumigrafa.carwifi.logic.CarWiFi
import com.edumigrafa.carwifi.logic.PIN_DIRECTION
import kotlinx.android.synthetic.main.fragment_view_one.*

/**
 * Created by anderson on 12/10/17.
 */
class OneViewFragment : Fragment() {

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

        seekBar.progress = 1
        textView.text = "0"

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            var seekBarProgress: Int = 0

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress - 1
                textView.text = seekBarProgress.toString()
                carWifi!!.execHttp(PIN_DIRECTION + seekBarProgress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //toast("Progress: " + seekBarProgress + " / " + seekBar.getMax())
                //carWifi!!.actionFront(seekBarProgress)

                //Quando solta o componente
                //seekBar_accelerator.progress = 0
                //seekBarProgress = 0
                //carWifi!!.actionFront(seekBarProgress)

            }
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

    //override fun onDetach() {
    //    Log.d(TAG, "onDetach")
    //    super.onDetach()
    //}
}

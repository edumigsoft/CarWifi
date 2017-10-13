package com.edumigrafa.carwifi.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edumigrafa.carwifi.R

/**
 * Created by anderson on 13/10/17.
 */
class AboutFragment: Fragment() {

    val TAG = "About Fragment"

    //override fun onAttach(context: Context?) {
    //    Log.d(TAG, "onAttach")
    //    super.onAttach(context)
    //}

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    Log.d(TAG, "onCreate")
    //    super.onCreate(savedInstanceState)
    //}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater!!.inflate(R.layout.fragment_about, container, false)
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
    //    Log.d(TAG, "onActivityCreated")
    //    super.onActivityCreated(savedInstanceState)
    //}

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()

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

}

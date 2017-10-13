package com.edumigrafa.carwifi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.edumigrafa.carwifi.views.OneViewFragment

class AppActivity() : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_2)

        ShowOneViewFragment()
    }

    fun ShowOneViewFragment() {
        val transaction = manager.beginTransaction()
        val fragment = OneViewFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //override fun onResume() {
    //    super.onResume()
    //}

    //override fun onPause() {
    //    super.onPause()
    //}
}

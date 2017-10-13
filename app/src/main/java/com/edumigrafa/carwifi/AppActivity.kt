package com.edumigrafa.carwifi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.edumigrafa.carwifi.views.OneViewFragment
import com.edumigrafa.carwifi.views.TwoViewFragment

class AppActivity() : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        //Preference

        //ShowOneViewFragment()
        ShowTwoViewFragment()
    }

    fun ShowOneViewFragment() {
        val transaction = manager.beginTransaction()
        val fragment = OneViewFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun ShowTwoViewFragment() {
        val transaction = manager.beginTransaction()
        val fragment = TwoViewFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.miOneView -> {
                ShowOneViewFragment()
                return true
            }
            R.id.miTwoView -> {
                ShowTwoViewFragment()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    //override fun onResume() {
    //    super.onResume()
    //}

    //override fun onPause() {
    //    super.onPause()
    //}
}

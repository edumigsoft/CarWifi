package com.edumigrafa.carwifi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.edumigrafa.carwifi.carwifi.*
import com.edumigrafa.carwifi.views.*

class AppActivity: AppCompatActivity() {

    val manager = supportFragmentManager

    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        var view: String = prefs!!.getString(PREFS_VIEW, PREFS_VIEW_ONE)
        when(view) {
            PREFS_VIEW_ONE -> {
                ShowOneViewFragment()
            }
            PREFS_VIEW_TWO -> {
                ShowTwoViewFragment()
            }
        }
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

    fun ShowTreeViewFragment() {
        val transaction = manager.beginTransaction()
        val fragment = TreeViewFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun ShowFourViewFragment() {
        val transaction = manager.beginTransaction()
        val fragment = FourViewFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun ShowAboutFragment() {
        val transaction = manager.beginTransaction()
        val fragment = AboutFragment()
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
                prefs!!.edit().putString(PREFS_VIEW, PREFS_VIEW_ONE).apply()
                ShowOneViewFragment()
                return true
            }
            R.id.miTwoView -> {
                prefs!!.edit().putString(PREFS_VIEW, PREFS_VIEW_TWO).apply()
                ShowTwoViewFragment()
                return true
            }
            R.id.miTreeView -> {
                prefs!!.edit().putString(PREFS_VIEW, PREFS_VIEW_TREE).apply()
                ShowTreeViewFragment()
                return true
            }
            R.id.miFourView -> {
                prefs!!.edit().putString(PREFS_VIEW, PREFS_VIEW_FOUR).apply()
                ShowFourViewFragment()
                return true
            }R.id.miAbout -> {
                ShowAboutFragment()
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

    override fun onDestroy() {
        super.onDestroy()
        prefs = null
    }
}

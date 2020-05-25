package com.example.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_add.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_books, R.id.navigation_movies, R.id.navigation_games))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        fab_add.setOnClickListener{
            val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_add,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //.setTitle("Add City")
            val mAlertDialog = mBuilder.show()
            dialogView.btn_add.setOnClickListener{
                var noErrors = true
                if(dialogView.add_city_text.text!!.isEmpty())
                {
                    dialogView.add_city_layout.error = "Field must not be empty "
                    noErrors=false
                }else{
                    dialogView.add_city_layout.error = null
                }
                if(noErrors)
                {



                        try {
                            mAlertDialog.dismiss()
                        }catch (e: IOException){
                            //activityViewModel.delete(cityInsert)
                            dialogView.add_city_layout.error="Wrong city name"
                        }



                }
            }
            dialogView.btn_cancel.setOnClickListener{
                mAlertDialog.dismiss()
            }

        }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.logout -> true
            else -> super.onOptionsItemSelected(item)

        }
    }

    }


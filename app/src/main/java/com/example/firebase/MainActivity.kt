package com.example.firebase
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firebase.database.Database
import com.example.firebase.ui.login.LoginActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_add.*
import kotlinx.android.synthetic.main.layout_add.view.*
import java.io.IOException
import kotlin.math.log

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
        val firebase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!.uid
        val myRef = firebase.getReference("ArrayData")
        fab_add.setOnClickListener{
            val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_add,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
            val currentFragment =navController.currentDestination?.label.toString()
            val mAlertDialog = mBuilder.show()
            dialogView.btn_add.setOnClickListener{
                var noErrors = true
                val hobbyText = dialogView.add_city_text.text.toString()
                val hobbychb = dialogView.checkbox.isChecked
                val firebaseInput = Database(hobbyText,hobbychb)
                Log.d("CurrentUser",currentUser)
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
                            myRef.setValue("hello word")
                            Log.d("Hobbytext",firebaseInput.name)
                            Log.d("hobbychb",firebaseInput.finished.toString())
                            Toast.makeText(this,"${currentFragment.dropLast(1)} added",Toast.LENGTH_SHORT).show()
                            mAlertDialog.dismiss()
                        }catch (e: IOException){
                            dialogView.add_city_layout.error="Wrong name"
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

            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                val options =
                    ActivityOptions.makeCustomAnimation(this, R.anim.fade_out, R.anim.fade_in)
                startActivity(intent,options.toBundle())
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    }


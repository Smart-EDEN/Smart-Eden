package com.example.smarteden

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.smarteden.data.FireStoreViewModel
import com.example.smarteden.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val fireStoreViewModel: FireStoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


/*
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            */


        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.HomeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        /*val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_offers,
            R.id.navigation_my_bookings, R.id.navigation_my_account))*/

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_signout -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val currentFragment = navController.currentDestination?.id
        if(currentFragment.toString() == R.id.HomeFragment.toString())
            onAlertDialog(this)
        super.onBackPressed()
    }

    private fun signOut() {
        fireStoreViewModel.logUserOut()
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()
        navController.navigate(startDestination, null, navOptions)
    }

    // When User cilcks on dialog button, call this method
    private fun onAlertDialog(context: Context) {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(context)

        // set title
        builder.setTitle("Warnung")

        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //set content area
        builder.setMessage("Wollen Sie die App wirklich schließen?")

        //set positive button
        builder.setPositiveButton(
            "Ja") { _, _ ->
            // User clicked Update Now button
            finish()
        }

        //set negative button
        builder.setNegativeButton(
            "Nein") { _, _ ->
            // User cancelled the dialog
        }

        builder.show()
    }
}

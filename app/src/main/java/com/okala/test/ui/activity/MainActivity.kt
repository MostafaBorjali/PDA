package com.okala.test.ui.activity

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.okala.test.R
import com.okala.test.databinding.ActivityMainBinding
import com.okala.test.utils.base.BaseActivity
import com.okala.test.utils.extention.setupWithNavController

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentNavController: LiveData<NavController>? = null

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun oncreate(savedInstanceState: Bundle?) {
        if (savedInstanceState==null){
            setupBottomNavigationBar()
            binding.navView.selectedItemId = R.id.list_navigation
        }
        mainViewModel.scannedQr.observe(this, Observer {
            if (it){
                binding.navView.selectedItemId = R.id.list_navigation
            }

        })

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.login_navigation,
            R.navigation.scan_navigation,
            R.navigation.list_navigation
        )
        val controller = binding.navView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

    }

    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            finish()
        }
    }


}

package org.example.mjworkspace.myapplication.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.example.mjworkspace.myapplication.R


abstract class BaseActivity <B: ViewDataBinding>(
    @LayoutRes val layoutResId: Int
): AppCompatActivity() {

    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    fun goToFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
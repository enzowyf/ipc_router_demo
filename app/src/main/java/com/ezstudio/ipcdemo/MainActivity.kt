package com.ezstudio.ipcdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ezstudio.module_b.ModuleBActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call_module_b.setOnClickListener {
            val intent = Intent(this@MainActivity, ModuleBActivity::class.java)
            startActivity(intent)
        }
    }
}

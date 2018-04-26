package com.example.gustavomurad.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import com.example.gustavomurad.servicetest.MyService.LocalBinder
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mService: MyService? = null
    private var mBound = false
    private var message = ""

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {

            val binder = service as LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService()

        button.setOnClickListener {
            message = editText.text.toString()
            if (mBound) {
                mService?.setToast(message, 3000, 3000)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, MyService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        unbindService(mConnection)
        mBound = false
    }

    private fun startService() {
        val serviceIntent = Intent(this, MyService::class.java)
        startService(serviceIntent)
    }
}

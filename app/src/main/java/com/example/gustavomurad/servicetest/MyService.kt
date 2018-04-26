package com.example.gustavomurad.servicetest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast

class MyService: Service(){
    private lateinit var countDownTimer: CountDownTimer
    private var mBinder: IBinder = getServiceBinder()

    private fun getServiceBinder(): LocalBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        toast(this, "Float like a butterfly, sting like a bee.", 3000, 3000)
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    override fun onDestroy() {
        countDownTimer.cancel()
    }

    private fun toast(context: Context, message: String, millisInFuture: Long, countDownInterval: Long){

        countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                countDownTimer.start()
            }
        }.start()
    }

    fun setToast(message: String,  millisInFuture: Long, countDownInterval: Long){
        countDownTimer.cancel()

        toast(this, message, millisInFuture, countDownInterval)
    }

}
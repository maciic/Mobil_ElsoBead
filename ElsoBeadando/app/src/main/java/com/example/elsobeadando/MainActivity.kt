package com.example.elsobeadando

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var negyzet: TextView

    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        negyzet = findViewById(R.id.tv_negyzet)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            val upDown = event.values[1]

            if(negyzet.translationX.toInt()-sides.toInt() < 350 && negyzet.translationX.toInt()-sides.toInt() > -350)
                negyzet.translationX -= sides.toInt()

            if(negyzet.translationY.toInt()+upDown.toInt() < 700 && negyzet.translationY.toInt()+upDown.toInt() > -700)
                negyzet.translationY += upDown.toInt()

            /*negyzet.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3
                rotation = -sides
                translationX = sides * -10
                translationY = sides * 10
            }*/

            negyzet.text = "${negyzet.translationX}"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}

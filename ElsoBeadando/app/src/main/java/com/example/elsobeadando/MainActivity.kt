package com.example.elsobeadando

import android.content.pm.ActivityInfo
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.widget.ImageView
import android.view.WindowInsets

import android.view.WindowMetrics
import android.widget.TextView
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var cube: ImageView
    lateinit var textView: TextView
    lateinit var sensorManager: SensorManager
    var width = 0f
    var height = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels / displayMetrics.density
        height = displayMetrics.heightPixels / displayMetrics.density

        println(width)
        println(height)

        cube = findViewById(R.id.im_cube)
        textView = findViewById(R.id.textView)

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
            val sides = (event.values[0] * 100).toInt() / 100f
            val upDown = (event.values[1] * 100).toInt() / 100f

            if(cube.translationX-sides < width+20 && cube.translationX-sides > 0-width-20){
                cube.translationX -= sides
            }

            if(cube.translationY+upDown < height+50 && cube.translationY+upDown > 0-height-50){
                cube.translationY += upDown
            }

            //textView.text = "${cube.translationX}\n${sides}"

            /*negyzet.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = sides * 10
            }*/
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

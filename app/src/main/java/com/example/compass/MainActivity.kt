package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * имплементим интерфейс Слушатель сенсора, для работы с сенсорами телефона
 */

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var manager: SensorManager? = null
    private var current_degree: Int = 0// для сохранения градусов в момент

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // для регистрации сенсора при работе приложения
    override fun onResume() {
        super.onResume()
        manager?.registerListener(
            this,
            manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )  //нужно понимать Какой сенсор мы используем
    }

    // для регистрации сенсора при работе приложения
    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val tvDegree = findViewById<TextView>(R.id.tvGrade)
        val imDinamic = findViewById<ImageView>(R.id.im_dinamic)

        val degree: Int = event?.values?.get(0)?.toInt()!!
        tvDegree.text = degree.toString()  //передаём число граудсов как строку в текстовое поле

        // создаём Анимацию поворота
        val rotationAnim = RotateAnimation(
            current_degree.toFloat(),
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        current_degree = -degree  //присваиваем градусы  из начальной позиции в конечную

        imDinamic.startAnimation(rotationAnim)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
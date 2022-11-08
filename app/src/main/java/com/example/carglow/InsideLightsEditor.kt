package com.example.carglow

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity


class InsideLightsEditor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inside_lights_editor)

        val led1 = findViewById<Button>(R.id.led_1)
        led1.setBackgroundColor(0xFFBEBEBE.toInt())

        val led2 = findViewById<Button>(R.id.led_2)
        led2.setBackgroundColor(0xFF949494.toInt())

        val led3 = findViewById<Button>(R.id.led_3)
        led3.setBackgroundColor(0xFF949494.toInt())

        val led4 = findViewById<Button>(R.id.led_4)
        led4.setBackgroundColor(0xFF949494.toInt())

        val led5 = findViewById<Button>(R.id.led_5)
        led5.setBackgroundColor(0xFF949494.toInt())

        val saveButton = findViewById<Button>(R.id.savebutton)

        val led1Remove = findViewById<Button>(R.id.led_1_remove)
        val led2Remove = findViewById<Button>(R.id.led_2_remove)
        val led3Remove = findViewById<Button>(R.id.led_3_remove)
        val led4Remove = findViewById<Button>(R.id.led_4_remove)
        val led5Remove = findViewById<Button>(R.id.led_5_remove)

        var selectedLed = 1 // keeps track of which LED is currently selected for seekbars
        var led1IsActive = 1
        var led2IsActive = 0
        var led3IsActive = 0
        var led4IsActive = 0
        var led5IsActive = 0

        val sbR = findViewById<SeekBar>(R.id.r_seekbar)
        val sbG = findViewById<SeekBar>(R.id.g_seekbar)
        val sbB = findViewById<SeekBar>(R.id.b_seekbar)
        val sbBright = findViewById<SeekBar>(R.id.bright_seekbar)

        val step = 1
        val max = 255
        val min = 0

        sbR.max = (max-min) / step
        sbG.max = (max-min) / step
        sbB.max = (max-min) / step
        sbBright.max = (max-min) / step

        var seekR = 0
        var seekG = 0
        var seekB = 0
        var seekBright : Int

        var color : Int
        var seekRString : String
        var seekGString : String
        var seekBString : String

        var led1ColorR = "0"
        var led1ColorG = "0"
        var led1ColorB = "0"
        var led2ColorR = "z"
        var led2ColorG = "z"
        var led2ColorB = "z"
        var led3ColorR = "z"
        var led3ColorG = "z"
        var led3ColorB = "z"
        var led4ColorR = "z"
        var led4ColorG = "z"
        var led4ColorB = "z"
        var led5ColorR = "z"
        var led5ColorG = "z"
        var led5ColorB = "z"

        var numColors = 1
        var numColorsString = "1"

        var sendCounter = 0
        var sendData = ""
        val mainActivity = MainActivity()
        var currentTime = System.currentTimeMillis()
        var elapsedTime = System.currentTimeMillis()
        var previousSendTime = System.currentTimeMillis()
        val updateCooldown = 50 // in ms
        var selectedLightPattern = 1

        val inOrOut = "i"

        val lightPatternSpinner: Spinner = findViewById(R.id.light_pattern_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.light_pattern_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            lightPatternSpinner.adapter = adapter
        }

        lightPatternSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // p2 is the position of the selected item in the list of items, starting from 0
                selectedLightPattern = p2
                sendData = "l" + (selectedLightPattern+1).toString() + "x" + inOrOut + "x"
                mainActivity.sendCommand(sendData)
            }
        }

        led1.setOnClickListener {
            selectedLed = 1
            if (led1IsActive == 0){
                led1.setBackgroundColor(0xFFBEBEBE.toInt())
                led1ColorR = "0"
                led1ColorG = "0"
                led1ColorB = "0"
            }
            led1IsActive = 1
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led2.setOnClickListener {
            selectedLed = 2
            if (led2IsActive == 0){
                led2.setBackgroundColor(0xFFBEBEBE.toInt())
                led2ColorR = "0"
                led2ColorG = "0"
                led2ColorB = "0"
            }
            led2IsActive = 1
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led3.setOnClickListener {
            selectedLed = 3
            if (led3IsActive == 0){
                led3.setBackgroundColor(0xFFBEBEBE.toInt())
                led3ColorR = "0"
                led3ColorG = "0"
                led3ColorB = "0"
            }
            led3IsActive = 1
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led4.setOnClickListener {
            selectedLed = 4
            if (led4IsActive == 0){
                led4.setBackgroundColor(0xFFBEBEBE.toInt())
                led4ColorR = "0"
                led4ColorG = "0"
                led4ColorB = "0"
            }
            led4IsActive = 1
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led5.setOnClickListener {
            selectedLed = 5
            if (led5IsActive == 0){
                led5.setBackgroundColor(0xFFBEBEBE.toInt())
                led5ColorR = "0"
                led5ColorG = "0"
                led5ColorB = "0"
            }
            led5IsActive = 1
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led1Remove.setOnClickListener {
            led1IsActive = 0
            led1.setBackgroundColor(0xFF949494.toInt())
            if (selectedLed == 1){
                selectedLed = 0
            }
            led1ColorR = "z"
            led1ColorG = "z"
            led1ColorB = "z"
            if ((led1IsActive == 0) && (led2IsActive == 0) && (led3IsActive == 0) &&
                (led4IsActive == 0) && (led5IsActive == 0)){
                selectedLed = 0
            }
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led2Remove.setOnClickListener {
            led2IsActive = 0
            led2.setBackgroundColor(0xFF949494.toInt())
            if (selectedLed == 2){
                selectedLed = 0
            }
            led2ColorR = "z"
            led2ColorG = "z"
            led2ColorB = "z"
            if ((led1IsActive == 0) && (led2IsActive == 0) && (led3IsActive == 0) &&
                (led4IsActive == 0) && (led5IsActive == 0)){
                selectedLed = 0
            }
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led3Remove.setOnClickListener {
            led3IsActive = 0
            led3.setBackgroundColor(0xFF949494.toInt())
            if (selectedLed == 3){
                selectedLed = 0
            }
            led3ColorR = "z"
            led3ColorG = "z"
            led3ColorB = "z"
            if ((led1IsActive == 0) && (led2IsActive == 0) && (led3IsActive == 0) &&
                (led4IsActive == 0) && (led5IsActive == 0)){
                selectedLed = 0
            }
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led4Remove.setOnClickListener {
            led4IsActive = 0
            led4.setBackgroundColor(0xFF949494.toInt())
            if (selectedLed == 4){
                selectedLed = 0
            }
            led4ColorR = "z"
            led4ColorG = "z"
            led4ColorB = "z"
            if ((led1IsActive == 0) && (led2IsActive == 0) && (led3IsActive == 0) &&
                (led4IsActive == 0) && (led5IsActive == 0)){
                selectedLed = 0
            }
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        led5Remove.setOnClickListener {
            led5IsActive = 0
            led5.setBackgroundColor(0xFF949494.toInt())
            if (selectedLed == 5){
                selectedLed = 0
            }
            led5ColorR = "z"
            led5ColorG = "z"
            led5ColorB = "z"
            if ((led1IsActive == 0) && (led2IsActive == 0) && (led3IsActive == 0) &&
                (led4IsActive == 0) && (led5IsActive == 0)){
                selectedLed = 0
            }
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            mainActivity.sendCommand(sendData)
        }

        sbR.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekR = progress
                seekRString = seekR.toString(16)
                seekGString = seekG.toString(16)
                seekBString = seekB.toString(16)

                color = Color.rgb(seekR, seekG, seekB)
                if (selectedLed == 1) {
                    led1ColorR = seekRString
                    led1ColorG = seekGString
                    led1ColorB = seekBString
                    led1.setBackgroundColor(color)
                }
                else if (selectedLed == 2) {
                    led2ColorR = seekRString
                    led2ColorG = seekGString
                    led2ColorB = seekBString
                    led2.setBackgroundColor(color)
                }
                else if (selectedLed == 3) {
                    led3ColorR = seekRString
                    led3ColorG = seekGString
                    led3ColorB = seekBString
                    led3.setBackgroundColor(color)
                }
                else if (selectedLed == 4) {
                    led4ColorR = seekRString
                    led4ColorG = seekGString
                    led4ColorB = seekBString
                    led4.setBackgroundColor(color)
                }
                else if (selectedLed == 5) {
                    led5ColorR = seekRString
                    led5ColorG = seekGString
                    led5ColorB = seekBString
                    led5.setBackgroundColor(color)
                }

                // send the color pattern to the arduino
                currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - previousSendTime
                if (selectedLed != 0 && elapsedTime > updateCooldown) {
                    sendCounter += 1
                    Log.i("InsideLightsEditor", "Send counter updated: $sendCounter times")
                    numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
                    numColorsString = numColors.toString()
                    sendData = "m" + numColorsString + "x" +
                            led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                            led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                            led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                            led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                            led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                            inOrOut + "x"
                    mainActivity.sendCommand(sendData)
                    previousSendTime = System.currentTimeMillis()
                }
            }
        })

        sbG.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekG = progress
                seekRString = seekR.toString(16)
                seekGString = seekG.toString(16)
                seekBString = seekB.toString(16)

                color = Color.rgb(seekR, seekG, seekB)
                if (selectedLed == 1) {
                    led1ColorR = seekRString
                    led1ColorG = seekGString
                    led1ColorB = seekBString
                    led1.setBackgroundColor(color)
                }
                else if (selectedLed == 2) {
                    led2ColorR = seekRString
                    led2ColorG = seekGString
                    led2ColorB = seekBString
                    led2.setBackgroundColor(color)
                }
                else if (selectedLed == 3) {
                    led3ColorR = seekRString
                    led3ColorG = seekGString
                    led3ColorB = seekBString
                    led3.setBackgroundColor(color)
                }
                else if (selectedLed == 4) {
                    led4ColorR = seekRString
                    led4ColorG = seekGString
                    led4ColorB = seekBString
                    led4.setBackgroundColor(color)
                }
                else if (selectedLed == 5) {
                    led5ColorR = seekRString
                    led5ColorG = seekGString
                    led5ColorB = seekBString
                    led5.setBackgroundColor(color)
                }

                // send the color pattern to the arduino
                currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - previousSendTime
                if (selectedLed != 0 && elapsedTime > updateCooldown) {
                    numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
                    numColorsString = numColors.toString()
                    sendData = "m" + numColorsString + "x" +
                            led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                            led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                            led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                            led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                            led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                            inOrOut + "x"
                    mainActivity.sendCommand(sendData)
                    previousSendTime = System.currentTimeMillis()
                }
            }
        })

        sbB.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekB = progress
                seekRString = seekR.toString(16)
                seekGString = seekG.toString(16)
                seekBString = seekB.toString(16)

                // update the color on the app
                color = Color.rgb(seekR, seekG, seekB)
                if (selectedLed == 1) {
                    led1ColorR = seekRString
                    led1ColorG = seekGString
                    led1ColorB = seekBString
                    led1.setBackgroundColor(color)
                }
                else if (selectedLed == 2) {
                    led2ColorR = seekRString
                    led2ColorG = seekGString
                    led2ColorB = seekBString
                    led2.setBackgroundColor(color)
                }
                else if (selectedLed == 3) {
                    led3ColorR = seekRString
                    led3ColorG = seekGString
                    led3ColorB = seekBString
                    led3.setBackgroundColor(color)
                }
                else if (selectedLed == 4) {
                    led4ColorR = seekRString
                    led4ColorG = seekGString
                    led4ColorB = seekBString
                    led4.setBackgroundColor(color)
                }
                else if (selectedLed == 5) {
                    led5ColorR = seekRString
                    led5ColorG = seekGString
                    led5ColorB = seekBString
                    led5.setBackgroundColor(color)
                }

                // send the color pattern to the arduino
                currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - previousSendTime
                if (selectedLed != 0 && elapsedTime > updateCooldown) {
                    numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
                    numColorsString = numColors.toString()
                    sendData = "m" + numColorsString + "x" +
                            led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                            led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                            led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                            led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                            led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                            inOrOut + "x"
                    mainActivity.sendCommand(sendData)
                    previousSendTime = System.currentTimeMillis()
                }
            }
        })

        var seekBrightString = ""
        sbBright.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBright = progress
                seekBrightString = seekBright.toString(16)
                sendData = "n" + seekBrightString + "x" + inOrOut + "x"
                mainActivity.sendCommand(sendData)
            }
        })

        saveButton.setOnClickListener{
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            var numberOfPreferences = preferences.all.size
            // val preferenceIndex = (numberOfPreferences / 4) + 1
            val preferenceCount = (numberOfPreferences / 4)
            var preferenceItem: String?
            var insidePreferenceCounter = 0
            for (i in 1..preferenceCount){
                preferenceItem = preferences.getString("Inside Entry $i: Name", null)
                if (preferenceItem != null) {
                    insidePreferenceCounter += 1
                }
            }
            editor.putString("Inside Entry ${insidePreferenceCounter+1}: Name", "Custom pattern ${insidePreferenceCounter}")
            numColors = led1IsActive + led2IsActive + led3IsActive + led4IsActive + led5IsActive
            numColorsString = numColors.toString()
            sendData = "m" + numColorsString + "x" +
                    led1ColorR + "x" + led1ColorG + "x" + led1ColorB + "x" +
                    led2ColorR + "x" + led2ColorG + "x" + led2ColorB + "x" +
                    led3ColorR + "x" + led3ColorG + "x" + led3ColorB + "x" +
                    led4ColorR + "x" + led4ColorG + "x" + led4ColorB + "x" +
                    led5ColorR + "x" + led5ColorG + "x" + led5ColorB + "x" +
                    inOrOut + "x"
            editor.putString("Inside Entry ${insidePreferenceCounter+1}: Color Pattern", sendData)
            sendData = "l" + (selectedLightPattern+1).toString() + "x" + inOrOut + "x"
            editor.putString("Inside Entry ${insidePreferenceCounter+1}: Light Pattern", sendData)
            sendData = "n" + "ff" + "x" + inOrOut + "x"
            editor.putString("Inside Entry ${insidePreferenceCounter+1}: Brightness", sendData)
            editor.apply()

            numberOfPreferences = preferences.all.size
            Log.i("InsideLightsEditor", "Number of shared preferences entries: $numberOfPreferences")
            super.onBackPressed()
        }
    }
}
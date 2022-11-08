package com.example.carglow

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BLUETOOTH = 1
    private val REQUEST_CODE = 1001

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check if the device supports bluetooth
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device Doesn't support BT", Toast.LENGTH_SHORT).show()
        }
        else{
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH)
            }
        }

        // check if the app has bluetooth permissions, and if not, request them
        if ((ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            || (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)) {

            // Permission is not granted: request the permission with no explanation
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN), REQUEST_CODE)
            // REQUEST_CODE is an app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            // Permission has already been granted
        }

        val connectButton = findViewById<Button>(R.id.connectbutton)
        connectButton.setOnClickListener {
            ConnectToDevice(this).execute()
        }

        val insideButton = findViewById<Button>(R.id.insidebutton)
        insideButton.setOnClickListener {
            val intent = Intent(this, InsideLights::class.java).apply {}
            startActivity(intent)
        }

        val outsideButton = findViewById<Button>(R.id.outsidebutton)
        outsideButton.setOnClickListener {
            val intent = Intent(this, OutsideLights::class.java).apply {}
            startActivity(intent)
        }

        val disconnectButton = findViewById<Button>(R.id.disconnectbutton)
        disconnectButton.setOnClickListener {
            disconnect()
        }

        // put the rainbow option in shared preferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Outside Entry 1: Name", "Rainbow")
        var send_data = "r" + "x" + "o" + "x"
        editor.putString("Outside Entry 1: Color Pattern", send_data)
        editor.putString("Outside Entry 1: Light Pattern", send_data)
        send_data = "n" + "ff" + "x" + "o" + "x"
        editor.putString("Outside Entry 1: Brightness", send_data)

        editor.putString("Inside Entry 1: Name", "Rainbow")
        send_data = "r" + "x" + "i" + "x"
        editor.putString("Inside Entry 1: Color Pattern", send_data)
        editor.putString("Inside Entry 1: Light Pattern", send_data)
        send_data = "n" + "ff" + "x" + "i" + "x"
        editor.putString("Inside Entry 1: Brightness", send_data)
        editor.apply()
    }

    class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {

        private var connectSuccess: Boolean = true
        private val context: Context
        init{
            this.context = c
        }

        override fun onPreExecute(){
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "Please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try{
                if(m_bluetoothSocket == null || !m_isConnected){
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException){
                connectSuccess = false
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess){
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }
            m_progress.dismiss()
        }
    }

    // send data to the arduino via bluetooth
    // pass in a string called "input"
    fun sendCommand(input: String){
        if (m_bluetoothSocket != null){
            try{
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch(e: IOException){
                Toast.makeText(this, "Failed to send data", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity", e.toString())
            }
        }
    }

    // called whenever the disconnect button is clicked
    private fun disconnect(){
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException){
                Toast.makeText(this, "Failed to disconnect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        var m_address: String = "98:D3:11:FC:3C:28"
    }
}
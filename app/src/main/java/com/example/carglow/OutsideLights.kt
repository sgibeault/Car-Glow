package com.example.carglow

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class OutsideLights : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outside_lights)

        val createNewPattern = findViewById<Button>(R.id.create_new_pattern)
        createNewPattern.setOnClickListener {
            val intent = Intent(this, OutsideLightsEditor::class.java).apply {}
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = MainActivity()
        val itemList = ArrayList<String>()
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        val listView = findViewById<ListView>(R.id.select_saved_pattern_list)
        listView.adapter = adapter
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val numberOfPreferences = preferences!!.all.size
        val preferenceCount = (numberOfPreferences / 4)
        Log.i("OutsideLights", "Preference count: $preferenceCount")
        var preferenceItem: String?
        for (i in 1..preferenceCount){
            preferenceItem = preferences.getString("Outside Entry $i: Name", null)
            if (preferenceItem != null) {
                itemList.add(preferenceItem)
            }
        }
        adapter.notifyDataSetChanged()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Log.i("OutsideLights", "Tapped position: $position")
            val position1 = position + 1
            preferenceItem = preferences.getString("Outside Entry $position1: Color Pattern", null)
            if (preferenceItem != null) {
                mainActivity.sendCommand(preferenceItem!!)
            } else {
                Log.i("OutsideLights", "No saved data at position $position1")
            }
            preferenceItem = preferences.getString("Outside Entry $position1: Light Pattern", null)
            if (preferenceItem != null) {
                mainActivity.sendCommand(preferenceItem!!)
            } else {
                Log.i("OutsideLights", "No saved data at position $position1")
            }
            preferenceItem = preferences.getString("Outside Entry $position1: Brightness", null)
            if (preferenceItem != null) {
                mainActivity.sendCommand(preferenceItem!!)
            } else {
                Log.i("OutsideLights", "No saved data at position $position1")
            }
        }
    }
}
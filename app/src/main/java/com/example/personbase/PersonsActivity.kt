package com.example.personbase

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonsActivity : AppCompatActivity() {
    private val db = DBHelper(this, null)
    lateinit var nameET: EditText
    lateinit var positionS: Spinner
    lateinit var phoneET: EditText
    lateinit var nameTV: TextView
    lateinit var positionTV: TextView
    lateinit var phoneTV: TextView
    lateinit var saveBTN: Button
    lateinit var getBTN: Button
    lateinit var delBTN: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_persons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        nameTV = findViewById(R.id.nameTV)
        phoneTV = findViewById(R.id.phoneTV)
        saveBTN = findViewById(R.id.saveBTN)
        getBTN = findViewById(R.id.getBTN)
        delBTN = findViewById(R.id.delBTN)
        positionTV = findViewById(R.id.positionTV)
        positionS = findViewById(R.id.positionS)
        val positions = listOf("Менеджер", "Разработчик", "Дизайнер")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, positions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionS.adapter = adapter
        saveBTN.setOnClickListener {
            val name = nameET.text.toString()
            val position = positionS.selectedItem.toString()
            val phone = phoneET.text.toString()
            db.addName(name, position, phone)
            Toast.makeText(this, "$name $position добавлен", Toast.LENGTH_SHORT).show()
            nameET.text.clear()
            phoneET.text.clear()
        }
        getBTN.setOnClickListener {
            val cursor = db.getInfo()
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                positionTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POS)) + "\n")
                phoneTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
            }
            while (cursor!!.moveToNext()) {
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                positionTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_POS)) + "\n")
                phoneTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE)) + "\n")
            }
            cursor.close()
        }
        delBTN.setOnClickListener {
            db.removeAll()
            nameTV.text=""
            positionTV.text=""
            phoneTV.text=" "
        }
    }
}
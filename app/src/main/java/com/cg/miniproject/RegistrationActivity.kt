package com.cg.miniproject

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import java.text.SimpleDateFormat
import java.util.*

class  RegistrationActivity: AppCompatActivity() {
    private val sharedPrefFile: String="User_Data"
    var formate = SimpleDateFormat("dd/MM/YYYY", Locale.US)
    var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val editFirstName = findViewById<EditText>(R.id.eT_FirstName)
        val editLastName = findViewById<EditText>(R.id.eT_LastName)
        val editEmail = findViewById<EditText>(R.id.eT_UserID)
        val btnSubmit = findViewById<Button>(R.id.submitButton)
        val phone=findViewById<EditText>(R.id.eT_Mobile)
        val checkB = findViewById<CheckBox>(R.id.checkBox)
        val pass=findViewById<EditText>(R.id.eT_Password)
        val confirmPass=findViewById<EditText>(R.id.eT_ConfirmPassword)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)


        val states = resources.getStringArray(R.array.States)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this@RegistrationActivity,
                android.R.layout.simple_spinner_item, states
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
        form {
            input(editFirstName, name = "Optional name") {
                isNotEmpty().description("Please Enter First Name")
            }
            input(editLastName, name = "Optional Name") {
                isNotEmpty().description("Please Enter Last Name")
            }
            input(editEmail, name = "Optional Name") {
                isNotEmpty().description("Please Enter Email")
                isEmail()
            }
            input(pass, name = "Optional Name") {
                isNotEmpty().description("Please Enter a Valid Password")
                length().atLeast(8)
                matches("^[A-Za-z1-9]+$")
            }
            /* input(confirmPass, name = "Optional Name") {
                 isNotEmpty().description("Password must be matched")
             }*/
            checkable(checkB,name = "Select"){
                isChecked()
            }

            input(phone, name = "Optional Name") {
                isNotEmpty().description("Please Enter a valid Number")
                length().exactly(10)}
            submitWith(btnSubmit) { result ->
                val emailId: String = editEmail?.text.toString()
                val pass: String = pass?.text.toString()
                val confirmpassword: String = confirmPass?.text.toString()
                if (pass!=confirmpassword) {
                    Toast.makeText(this@RegistrationActivity, R.string.paswword_error, Toast.LENGTH_SHORT)
                        .show()
                }
                else {

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("emailID_key", emailId)
                    editor.putString("password_key", pass)
                    editor.apply()
                    editor.commit()
                    finish()
                }
            }
        }
    }
}






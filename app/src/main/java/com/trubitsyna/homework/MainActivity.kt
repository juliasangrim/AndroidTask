package com.trubitsyna.homework

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAge: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonDelete: Button
    private lateinit var textViewInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val peopleInfoList = ArrayList<Info>()

        initView()
        buttonAdd.setOnClickListener {
            addInfoToList(peopleInfoList)
            showList(peopleInfoList)
        }

        buttonDelete.setOnClickListener {
            deleteInfoFromList(peopleInfoList)
            showList(peopleInfoList)
        }

    }

    private fun initView() {
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAge = findViewById(R.id.editTextAge)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonDelete = findViewById(R.id.buttonDelete)
        textViewInfo = findViewById(R.id.textViewInfo)
    }

    private fun getInfoFromEditText(): Info? {
        val name = editTextName.text.toString()
        val surname = editTextSurname.text.toString()
        val age = editTextAge.text.toString()
        if (name.isNotBlank() && surname.isNotBlank() && age.isNotBlank()) {
            return Info(name, surname, age.toInt())
        }
        if (name.isBlank()) {
            showToastError(EMPTY_NAME_ERROR_MESSAGE)
        }
        if (surname.isBlank()) {
            showToastError(EMPTY_SURNAME_ERROR_MESSAGE)
        }
        if (age.isBlank()) {
            showToastError(EMPTY_AGE_ERROR_MESSAGE)
        }
        return null
    }

    private fun addInfoToList(list: ArrayList<Info>) {
        val info = getInfoFromEditText()
        if (info != null && list.size < MAX_INFO_LIST_SIZE) {
            list.add(info)
        }
        if (list.size >= MAX_INFO_LIST_SIZE) {
            showSnackbarError(TOO_MANY_ELEM_ERROR)
        }
    }

    private fun deleteInfoFromList(list: ArrayList<Info>) {
        if (list.isNotEmpty()) {
            list.removeLast()
        }
        if (list.isEmpty()) {
            showSnackbarError(EMPTY_LIST_ERROR)
        }
    }

    private fun fromListToFormatString(list: List<Info>): String {
        var count = 0
        return list.joinToString(separator = "") {
            "${count++}: $it\n"
        }
    }

    private fun showList(list: List<Info>) {
        textViewInfo.text = fromListToFormatString(list)
    }

    private fun showToastError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showSnackbarError(message: String) {
        Snackbar.make(buttonDelete, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val MAX_INFO_LIST_SIZE = 10
        const val EMPTY_NAME_ERROR_MESSAGE = "Поле \"Имя\" не должно быть пустым!"
        const val EMPTY_SURNAME_ERROR_MESSAGE = "Поле \"Фамилия\" не должно быть пустым"
        const val EMPTY_AGE_ERROR_MESSAGE = "Поле \"Возраст\" не должно быть пустым!"
        const val EMPTY_LIST_ERROR = "Лист пустой!"
        const val TOO_MANY_ELEM_ERROR = "Лист заполнен! " +
                "Максимальное количество элементов $MAX_INFO_LIST_SIZE"
    }
}

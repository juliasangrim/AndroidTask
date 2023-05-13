package com.trubitsyna.homework

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.trubitsyna.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val peopleInfoList = ArrayList<Info>()
        viewBinding.buttonAdd.setOnClickListener {
            addInfoToList(peopleInfoList)
            showList(peopleInfoList)
        }

        viewBinding.buttonDelete.setOnClickListener {
            deleteInfoFromList(peopleInfoList)
            showList(peopleInfoList)
        }

    }

    private fun getInfoFromEditText(): Info? {
        val name = viewBinding.editTextName.text.toString()
        val surname = viewBinding.editTextSurname.text.toString()
        val age = viewBinding.editTextAge.text.toString()
        return when {
            name.isBlank() -> {
                showToastError(EMPTY_NAME_ERROR_MESSAGE)
                null
            }
            surname.isBlank() -> {
                showToastError(EMPTY_SURNAME_ERROR_MESSAGE)
                null
            }
            age.isBlank() -> {
                showToastError(EMPTY_AGE_ERROR_MESSAGE)
                null
            }
            age.toIntOrNull() == null -> {
                showToastError(INVALID_AGE_ERROR)
                null
            }
            else -> {
                Info(name, surname, age.toInt())
            }
        }
    }

    private fun addInfoToList(list: ArrayList<Info>) {
        val info = getInfoFromEditText()
        if (list.size >= MAX_INFO_LIST_SIZE) {
            showSnackbarError(TOO_MANY_ELEM_ERROR)
        } else {
            info?.let { list.add(it) }
        }
    }

    private fun deleteInfoFromList(list: ArrayList<Info>) {
        if (list.isNotEmpty()) {
            list.removeLast()
        } else {
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
        viewBinding.textViewInfo.text = fromListToFormatString(list)
    }

    private fun showToastError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showSnackbarError(message: String) {
        Snackbar.make(viewBinding.buttonDelete, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val MAX_INFO_LIST_SIZE = 10
        const val EMPTY_NAME_ERROR_MESSAGE = "Поле \"Имя\" не должно быть пустым!"
        const val EMPTY_SURNAME_ERROR_MESSAGE = "Поле \"Фамилия\" не должно быть пустым"
        const val EMPTY_AGE_ERROR_MESSAGE = "Поле \"Возраст\" не должно быть пустым!"
        const val INVALID_AGE_ERROR = "Поле \"Возраст\" должно быть целым числом!"
        const val EMPTY_LIST_ERROR = "Лист пустой!"
        const val TOO_MANY_ELEM_ERROR = "Лист заполнен! " +
                "Максимальное количество элементов $MAX_INFO_LIST_SIZE"
    }
}

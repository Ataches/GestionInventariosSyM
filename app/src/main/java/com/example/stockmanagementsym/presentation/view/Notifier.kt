package com.example.stockmanagementsym.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.example.stockmanagementsym.data.MESSAGES
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.uiThread

class Notifier {

    private lateinit var context: Context

    fun showToastMessage(message: String) {
        context.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showToastMessage(int: Int) {
        context.runOnUiThread {
            Toast.makeText(context, context.getString(int), Toast.LENGTH_SHORT).show()
        }
    }

    fun showResultTransaction(resultTransaction: Boolean) {
        if (resultTransaction)
            showToastMessage(MESSAGES.TRANSACTION_SUCCESS)
        else
            showToastMessage(MESSAGES.TRANSACTION_FAILURE)
    }

    fun showAlertMessage(title: String, message: String) {
        context.runOnUiThread {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setNeutralButton("Cerrar") { _, _ -> }
            builder.create()
            builder.show()

        }
    }

    fun showAlertMessage(int: Int, message: String) {
        context.runOnUiThread {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(int))
            builder.setMessage(message)
            builder.setNeutralButton("Cerrar") { _, _ -> }
            builder.create()
            builder.show()

        }
    }

    fun showAlertMessage(tittle: Int, message: Int) {
        context.runOnUiThread {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(tittle))
            builder.setMessage(context.getString(message))
            builder.setNeutralButton("Cerrar") { _, _ -> }
            builder.create()
            builder.show()

        }
    }

    fun setNotifierContext(context: Context) {
        this.context = context
    }
}
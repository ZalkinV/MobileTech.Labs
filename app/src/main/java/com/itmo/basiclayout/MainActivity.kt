package com.itmo.basiclayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val switcher: Switch by lazy { findViewById(R.id.switch1) }
    val textView: TextView by lazy { findViewById(R.id.textView) }

    val buttonHideList: Button by lazy { findViewById(R.id.button_hidelist) }
    val listView: ListView by lazy { findViewById(R.id.listview) }

    val buttonToast: Button by lazy { findViewById(R.id.button_toast) }

    val fab: FloatingActionButton by lazy { findViewById(R.id.floatingActionButton) }
    val editText: EditText by lazy { findViewById(R.id.editText) }

    companion object {
        private const val logInfoTag = "TASK1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromIntent = intent.getStringExtra("Info")

        switcher.setOnCheckedChangeListener { _, isChecked ->
            val colorResource =
                if (isChecked) R.color.label_color_switch_true
                else R.color.label_color_switch_false

            textView.setBackgroundResource(colorResource)
        }

        buttonHideList.setOnClickListener {
            listView.visibility = when(listView.visibility) {
                View.INVISIBLE -> View.VISIBLE
                View.VISIBLE -> View.INVISIBLE
                else -> View.VISIBLE
            }

            Log.d(logInfoTag, "Button to hide list was clicked")
            Log.i(logInfoTag, fromIntent.orEmpty())
        }

        buttonToast.setOnClickListener {
            Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
            Log.d(logInfoTag, "Toast button was clicked")
        }

        fab.setOnClickListener {
            textView.text = editText.text
        }
    }
}
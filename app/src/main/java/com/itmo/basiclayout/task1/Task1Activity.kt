package com.itmo.basiclayout.task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.itmo.basiclayout.R
import com.itmo.basiclayout.task1.dataSources.InMemoryDataSource
import com.itmo.basiclayout.databinding.ActivityTask1Binding
import com.itmo.basiclayout.task1.keysEnums.BundleKeysEnum
import com.itmo.basiclayout.task1.keysEnums.IntentKeysEnum

class Task1Activity : AppCompatActivity() {

    companion object {
        private const val logTag = "TASK1"
    }

    private lateinit var binding: ActivityTask1Binding

    private var listViewItems = listOf<ListItemDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTask1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponents(savedInstanceState)

        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.apply {
            outState.apply {
                putParcelableArrayList(BundleKeysEnum.LIST_ELEMENTS.name, ArrayList(listViewItems))
                putInt(BundleKeysEnum.LIST_VISIBILITY.name, listView.visibility)
                putString(BundleKeysEnum.TEXTVIEW_TEXT.name, textView.text.toString())
                putBoolean(BundleKeysEnum.SWITCHER_STATE.name, switcher.isChecked)
            }
        }
    }

    private fun initializeComponents(state: Bundle?) {
        if (state != null) with(state) {
            binding.apply {
                listViewItems = getParcelableArrayList<ListItemDetails>(BundleKeysEnum.LIST_ELEMENTS.name)?.toList() ?: emptyList()
                listView.visibility = getInt(BundleKeysEnum.LIST_VISIBILITY.name)
                textView.text = getString(BundleKeysEnum.TEXTVIEW_TEXT.name)
                switcher.isChecked = getBoolean(BundleKeysEnum.SWITCHER_STATE.name)
            }
        } else {
            listViewItems = InMemoryDataSource().fetchData(10)
        }

        setTextViewColor(binding.switcher.isChecked)
        populateListView()
    }

    private fun setListeners() = binding.apply {
        switcher.setOnCheckedChangeListener { _, isChecked -> setTextViewColor(isChecked) }

        buttonHideList.setOnClickListener {
            listView.visibility = when(listView.visibility) {
                View.INVISIBLE -> View.VISIBLE
                View.VISIBLE -> View.INVISIBLE
                else -> View.VISIBLE
            }

            Log.d(logTag, "Button to hide list was clicked")
        }

        buttonToast.setOnClickListener {
            Toast.makeText(baseContext, getString(R.string.toast_text), Toast.LENGTH_SHORT).show()
            Log.d(logTag, "Toast button was clicked")
        }

        floatingActionButton.setOnClickListener {
            textView.text = editText.text
            Snackbar.make(it, getString(R.string.task1_snackBar_text), Snackbar.LENGTH_SHORT).show()
        }

        listView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(baseContext, DetailsActivity::class.java).apply {
                putExtra(IntentKeysEnum.DETAILS.name, listViewItems[i])
            }

            startActivity(intent)
        }
    }

    private fun populateListView() = binding.listView.apply {
        adapter = ArrayAdapter(
            baseContext,
            R.layout.support_simple_spinner_dropdown_item,
            listViewItems.map { it.title })
    }

    private fun setTextViewColor(isSwitcherChecked: Boolean) = binding.textView.apply {
        setBackgroundResource(
            if (isSwitcherChecked) R.color.green else R.color.red
        )
    }
}

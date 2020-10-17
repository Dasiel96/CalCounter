package com.das.calcount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val itemCount by lazy {
        findViewById<TextView>(R.id.inputCount)
    }

    private val foodField by lazy {
        findViewById<AutoCompleteTextView>(R.id.foodField)
    }

    private val calField by lazy {
        findViewById<EditText>(R.id.calField)
    }

    private val list by lazy {
        findViewById<RecyclerView>(R.id.foodList)
    }

    private val foodManager by lazy {
        LinearLayoutManager(this)
    }

    private val foodAdapter by lazy {
        FoodItemAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemCount.text = "1"

        itemCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val lowerCap = 1
                val upperCap = 300

                val hasChars = s?.isNotEmpty()
                if (hasChars != null && hasChars) {
                    val countNum = s.toString().toInt()

                    if (countNum < lowerCap) {
                        s.clear()
                        s.append(lowerCap.toString())
                    } else if (countNum > upperCap) {
                        s.clear()
                        s.append(upperCap.toString())
                    }
                }
            }

        })

        list.apply {
            adapter = foodAdapter
            layoutManager = foodManager
        }
    }

    fun addCount(view: View) {
        val incNum = itemCount.text.toString().toInt() + 1
        itemCount.text = incNum.toString()
    }

    fun subtractCount(view: View) {
        val decNum = itemCount.text.toString().toInt() - 1
        itemCount.text = decNum.toString()
    }

    fun addItem(view: View) {
        val food = getString(R.string.food_item_name, foodField.text)
        val cal = getString(R.string.cal_item_amt, calField.text)

        if (foodField.text.isNotEmpty() && calField.text.isNotEmpty()) {
            val numOfItems = itemCount.text.toString().toInt()
            for (i in 0 until  numOfItems) {
                foodAdapter.add(food, cal)
            }
            foodField.setText("")
            calField.setText("")
            itemCount.text = "1"
        }
    }
}
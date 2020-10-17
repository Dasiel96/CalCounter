package com.das.calcount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemView (view: View, click: (pos: Int) -> Unit, pos: Int): RecyclerView.ViewHolder(view) {
    val foodName: TextView = view.findViewById(R.id.foodName)
    val calAmount: TextView = view.findViewById(R.id.calAmount)
    private val cancelButton: ImageButton = view.findViewById(R.id.cancel)

    init {
        cancelButton.setOnClickListener {
            click(pos)
        }
    }


}

class FoodItemAdapter(activity: MainActivity) : RecyclerView.Adapter<ItemView>() {

    private val dataList = ArrayList<Array<String>>()
    private val display = DisplayText(activity)
    private val save = Save(activity)
    private var calTally = 0f
    private var shouldSave = false

    init {
        val readData = save.read()
        if (readData.has(LIST_SAVE)) {
            val data = readData.getJSONArray(LIST_SAVE)
            for (i in 1 until data.length() step 2) {
                val food = activity.getString(R.string.food_item_name, data[i-1])
                val cal = activity.getString(R.string.cal_item_amt, data[i])

                add(food, cal)
            }
        }
        shouldSave = true
    }

    private fun getCalAmount(pos: Int): Float {
        return dataList[pos][1].split(":")[1].substring(1).toFloat()
    }

    private fun remove(pos: Int) {
        calTally -= getCalAmount(pos)
        dataList.removeAt(pos)
        notifyDataSetChanged()
        display.setText(calTally)

        if (shouldSave) {
            save.save(dataList)
        }
    }

    fun add(foodName: String, calAmount: String) {
        dataList.add(arrayOf(foodName, calAmount))
        calTally += getCalAmount(dataList.size - 1)
        notifyDataSetChanged()
        display.setText(calTally)

        if (shouldSave) {
            save.save(dataList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_view, parent, false)
        return  ItemView(view, ::remove, viewType)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.foodName.text = dataList[position][0]
        holder.calAmount.text = dataList[position][1]
    }

    override fun getItemCount(): Int = dataList.size

}
package com.das.calcount

import org.json.JSONObject
import java.io.File

class Save (activity: MainActivity) {
    private val filePath = "info/save.json"
    private val folder = File(activity.filesDir, filePath.split("/")[0])
    private val file = File(folder, filePath.split("/")[1])

    init {
        if (!folder.exists()) {
            folder.mkdir()
        }

        if (!file.exists()) {
            file.createNewFile()
            file.writeText("{}")
        }
    }

    private fun getData(data: String): String {
        return data.split(":")[1].substring(1)
    }

    fun save(list: ArrayList<Array<String>>) {
        val json = JSONObject()

        for (arr in list) {
            json.accumulate(LIST_SAVE, getData(arr[0]))
            json.accumulate(LIST_SAVE, getData(arr[1]))
        }

        file.writeText(json.toString())
    }

    fun read(): JSONObject {
        return JSONObject(file.readText())
    }

    fun reset() {
        file.writeText("{}")
    }
}
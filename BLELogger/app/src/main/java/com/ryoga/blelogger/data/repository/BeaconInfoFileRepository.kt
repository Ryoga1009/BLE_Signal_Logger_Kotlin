package com.ryoga.blelogger.data.repository

import android.content.Context
import com.ryoga.blelogger.data.model.BeaconInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class BeaconInfoFileRepository(private val context: Context) {

    private val filePath = context.filesDir.path
    private val fileName = "BeaconInfoList.txt"


    fun writeBeaconInfo(beaconInfo: BeaconInfo): Boolean {
        val beaconInfoList = loadBeaconInfoLost()

        beaconInfoList.add(beaconInfo)

        val json = Json.encodeToString(beaconInfoList)

        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }

        return true
    }

    fun writeBeaconInfoList(beaconInfoList: List<BeaconInfo>): Boolean {
        val json = Json.encodeToString(beaconInfoList)

        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
        return true
    }

    fun updateBeaconInfo(beaconInfo: BeaconInfo, position: Int): Boolean {
        val beaconInfoList = loadBeaconInfoLost()

        beaconInfoList[position] = beaconInfo
        return writeBeaconInfoList(beaconInfoList)
    }

    fun loadBeaconInfoLost(): ArrayList<BeaconInfo> {

        var beaconInfoList = ArrayList<BeaconInfo>()

        val file = File(filePath + "/" + fileName)
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        if (!file.exists()) {
            return beaconInfoList
        }

        var data: String = ""
        bufferedReader.forEachLine {
            data += it
        }
        bufferedReader.close()

        if (data.isNullOrEmpty()) {
            return beaconInfoList
        }

        beaconInfoList.addAll(Json.decodeFromString<List<BeaconInfo>>(data))
        return beaconInfoList
    }


}
package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayEight : Challenge {

    override fun partOne() {
        val lines = File("data/d8_p1.txt").readLines()

        var count = 0

        lines.forEach { it.split(" | ")[1].split(" ").forEach { x -> if(
            x.length == 2 ||
            x.length == 4 ||
            x.length == 3 ||
            x.length == 7
        ) count++ } }

        println("Numbers counted: $count")
    }

    override fun partTwo() {

        val lines = File("data/d8_p1.txt").readLines()

        var sum = 0

        lines.forEach { sum += analyseNumber(it) }

        println("Sum: $sum")

    }

/*    1
      |
     _____
6-> |     |  <- 2
    |     |
     -----
5-> |  ^  |  <- 3
    |  7  |
     -----
      ^
      |
      4         */

    private fun analyseNumber(input: String): Int{


        val stringMap = mutableMapOf(
            0 to "",
            1 to "",
            2 to "",
            3 to "",
            4 to "",
            5 to "",
            6 to "",
            7 to "",
            8 to ""
        )


        val data = input.split(" | ")[0].split(" ")

        // Isolate 1, 4, 7, 8
        data.forEach { when(it.length){
            2 -> stringMap[1] = it
            3 -> stringMap[7] = it
            4 -> stringMap[4] = it
            7 -> stringMap[8] = it
        } }

        // Isolate 0, 6 and 9
        data.filter { it.length == 6 }.forEach { if(it.toList().minus(stringMap[1]!!.toList().toSet()).size == 5) stringMap[6] = it else
        if(it.toList().minus(stringMap[4]!!.toList().toSet()).size == 2) stringMap[9] = it else stringMap[0] = it}

        // Isolate 2, 3 and 5
        data.filter { it.length == 5 }.forEach {
            if(it.toList().minus(stringMap[4]!!.toList().toSet()).size == 3) stringMap[2] = it else
            if(it.toList().minus(stringMap[6]!!.toList().toSet()).size == 1) stringMap[3] = it else
            stringMap[5] = it
        }

        // Decode using the map
        val encoded = input.split(" | ")[1].split(" ")
        var decoded = ""

        encoded.forEach { stringMap.forEach { x -> if(x.value.toList().sorted() == it.toList().sorted()) decoded += x.key} }
        return decoded.toInt()
    }

}
package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayOne : Challenge {

    override fun partOne() {
        var prevMes = -1

        // the number of times a depth measurement increases from the previous measurement
        var count = 0
        for (x in File("data/d1_p1.txt").readLines()){
            if(prevMes == -1){
                println("$x (PrevMes N/A)")
                prevMes = x.toInt()
                continue
            }
            // Check Increase or decrease
            if(prevMes < x.toInt()){
                println("$x (Increased)")
                count++
            }else{
                println("$x (Decreased or not Changed)")
            }

            prevMes = x.toInt()
        }
        // Print res
        println("Count: $count")

    }

    override fun partTwo() {
        val lines = File("data/d1_p1.txt").readLines()

        var prevWindow = -1

        var count = 0

        for((index, x) in lines.withIndex()){

            // Validate current index
            if(index + 2 >= lines.size){
                println("$x (N/A: Cant create window)")
                continue
            }

            // Current windows a + b + c
            val currentWindow = x.toInt() + lines[index + 1].toInt() + lines[index + 2].toInt()

            if(prevWindow == -1){
                println("$x (window N/A)")
                prevWindow = currentWindow
                continue
            }

            // Check window
            if(prevWindow < currentWindow){
                println("$x (Increased)")
                count++
            }else{
                println("$x (Decreased or not changed)")
            }

            prevWindow = currentWindow
        }

        // Print res
        println("Count: $count")
    }
}
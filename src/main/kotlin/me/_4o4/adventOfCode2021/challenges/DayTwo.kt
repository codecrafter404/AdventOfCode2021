package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayTwo : Challenge {
    override fun partOne() {
        // horizontal
        var x = 0

        var depth = 0

        val lines = File("data/d2_p1.txt").readLines()

        for(line in lines){
            val steps = line.split(" ")[1].toInt()

            with(line){
                when{
                    startsWith("forward") -> x += steps
                    startsWith("down") -> depth += steps
                    startsWith("up") -> depth -= steps
                }
            }
        }
        println("X: $x \nDepth: $depth")
        println("Result(x * depth): ${x * depth}")
    }

    override fun partTwo() {
        // horizontal
        var x = 0

        var depth = 0

        var aim = 0

        val lines = File("data/d2_p1.txt").readLines()

        for(line in lines){
            val steps = line.split(" ")[1].toInt()

            with(line){
                when{
                    startsWith("forward") -> {x += steps; depth += aim * steps}
                    startsWith("down") -> aim += steps
                    startsWith("up") -> aim -= steps
                }
            }
        }
        println("X: $x\nDepth: $depth\nResult(x * depth): ${x * depth}")
    }
}
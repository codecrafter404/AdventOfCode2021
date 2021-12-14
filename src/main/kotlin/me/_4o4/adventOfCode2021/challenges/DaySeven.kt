package me._4o4.adventOfCode2021.challenges

import java.io.File
import kotlin.math.abs


class DaySeven : Challenge {
    override fun partOne() {
        val lines = File("data/d7_p1.txt").readLines()
        val crabs = lines[0].split(",").map { it.toInt() }

        var count = Int.MAX_VALUE

        for(x in 1..crabs.size){
            val y = calculateFuel(x, crabs)
            if(y < count) count = y
        }

        println("Minimal Fuel: $count")
    }

    override fun partTwo() {
        val lines = File("data/d7_p1.txt").readLines()
        val crabs = lines[0].split(",").map { it.toInt() }

        var count = Int.MAX_VALUE

        for(x in 1..crabs.size){
            val y = calculateFuelNew(x, crabs)
            if(y < count) count = y
        }

        println("Minimal Fuel: $count")


    }

    private fun calculateFuelNew(pos: Int, crabs: List<Int>): Int{
        var fuel = 0
        crabs.forEachIndexed { index, i -> fuel += ((abs(i - pos))  * (abs(i - pos)) + (abs(i - pos)) ) / 2}
        return fuel
    }

    private fun calculateFuel(pos: Int, crabs: List<Int>): Int{
        var fuel = 0
        crabs.forEachIndexed { index, i -> fuel += abs(i - pos) }
        return fuel
    }
}
package me._4o4.adventOfCode2021.challenges

import java.io.File

class DaySix : Challenge {
    override fun partOne() {
        val lines = File("data/d6_p1.txt").readLines()

        val fishes = mutableListOf<Int>()

        lines[0].split(",").forEach { fishes.add(it.toInt()) }

        println("Initial state: ${fishes.size}")
        for(x in 1..80){
            simulateDaySlow(fishes)
            println("After $x days(${fishes.size}): ${fishes.joinToString(",")}")
        }
        println("Final count of fishes: ${fishes.size}")

    }

    override fun partTwo() {
        val lines = File("data/d6_p1.txt").readLines()

        val fishes = mutableMapOf<Int, Long>(
            0 to 0, 1 to 0, 2 to 0, 3 to 0, 4 to 0,
            5 to 0, 6 to 0, 7 to 0, 8 to 0
        )

        lines[0].split(",").forEach {
            fishes[it.toInt()] = fishes[it.toInt()]!!.plus(1)
        }
        println("Initial state: $fishes")
        for(x in 1..256){
            simulateDayFast(fishes)
            println("After $x days(${fishes.values.sum()}): $fishes")
        }

        println("Final count of fishes: ${fishes.values.sum()}")


    }

    // O(n^n)
    private fun simulateDaySlow(fishes: MutableList<Int>){
        var spawnCount = 0
        val reset = mutableListOf<Int>()

        fishes.forEachIndexed {idx, it -> if(it == 0) {spawnCount++; reset.add(idx)} }

        fishes.forEachIndexed { idx, _ -> fishes[idx]-- }

        for(y in 1 .. spawnCount) fishes.add(8)
        reset.forEach { i -> fishes[i] = 6 }
    }

    // O(n)
    private fun simulateDayFast(fishes: MutableMap<Int, Long>){
        val tmp = fishes[0]!!

        // Move all one down
        for(x in 0..7){
            fishes[x] = fishes[x + 1]!!
        }

        fishes[6] = fishes[6]!! + tmp
        fishes[8] = tmp
    }
}
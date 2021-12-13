package me._4o4.adventOfCode2021

import me._4o4.adventOfCode2021.challenges.*
import kotlin.system.exitProcess

// [1-25]
const val day = 4

// [1;2]
const val part = 2

val challenges = listOf(
    DayOne(),
    DayTwo(),
    DayThree(),
    DayFour()
)

fun main() {
    println("AdventOfCode 2021 - https://adventofcode.com/ - Solved by Codecrafter_404")
    println("-------------------------------------------------------------------------")
    // Check if input is valid
    if(day < 1 || day > 25 || part < 1 || part > 2){
        println("Error: Invalid parameters.")
        exitProcess(-1)
    }

    if(day > challenges.size){
        println("Error: Challenge isn't implemented.")
        exitProcess(-1)
    }
    // Print Info
    println("Day: $day | Part $part/2")
    println("-------------------------------------------------------------------------")

    // Run Challenge
    if(part == 1){
        challenges[day - 1].partOne()
    }else{
        challenges[day - 1].partTwo()
    }

}
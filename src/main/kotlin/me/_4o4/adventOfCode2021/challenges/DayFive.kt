package me._4o4.adventOfCode2021.challenges

import java.io.File
import kotlin.math.abs

data class Position(val x: Int, val y: Int)


class DayFive : Challenge{
    override fun partOne() {
        var lines = File("data/d5_p1.txt").readLines()
        lines = lines.distinct()

        val parsed = parse(lines, onlyHV = true)

        // Calculate max X and Y
        var maxX = 0
        var maxY = 0

        parsed.forEach { x -> x.toList().forEach { y -> if(y.x > maxX) maxX = y.x else  if(y.y > maxY) maxY = y.y}}

        maxX++
        maxY++

        println("Max X: $maxX\nMax Y: $maxY")

        val field = Array(maxX) {Array(maxY) {0} }

        // Iterate through all instructions
        for(instruction in parsed){
            drawLineHV(
                instruction.first,
                instruction.second,
                1,
                field
            )
        }

        // Detect all overlaps
        var overlaps = 0

        field.forEach { x -> x.forEach { y -> if (y > 1) overlaps++ } }

        // Print the stuff
        printField(field)
        print("\nOverlaps: $overlaps")
    }

    override fun partTwo() {
        var lines = File("data/d5_p1.txt").readLines()
        lines = lines.distinct()

        val parsed = parse(lines, onlyHV = false)

        // Calculate max X and Y
        var maxX = 0
        var maxY = 0

        parsed.forEach { x -> x.toList().forEach { y -> if(y.x > maxX) maxX = y.x else  if(y.y > maxY) maxY = y.y}}

        maxX++
        maxY++

        println("Max X: $maxX\nMax Y: $maxY")

        val field = Array(maxX) {Array(maxY) {0} }

        // Iterate through all instructions

        for(instruction in parsed){
            drawLine(
                instruction.first,
                instruction.second,
                1,
                field
            )
        }



        // Detect all overlaps
        var overlaps = 0

        field.forEach { x -> x.forEach { y -> if (y > 1) overlaps++ } }

        // Print the stuff
        printField(field)
        print("\nOverlaps: $overlaps")
    }

    private fun parse(lines: List<String>, onlyHV: Boolean): List<Pair<Position, Position>>{
        val list = mutableListOf<Pair<Position, Position>>()

        for(line in lines){
            val block1 = line.replace(" ", "").split("->")[0]
            val block2 = line.replace(" ", "").split("->")[1]

            list.add(
                Pair(
                    Position(block1.split(",")[0].toInt(), block1.split(",")[1].toInt()),
                    Position(block2.split(",")[0].toInt(), block2.split(",")[1].toInt())
                )
            )
        }

        if(onlyHV){
            list.removeAll { it -> !(it.first.x == it.second.x || it.first.y == it.second.y)}
        }
        return list.toList()
    }

    private fun printField(field: Array<Array<Int>>){
        val lines = mutableListOf<String>()

        for(col in field){
            for((idx, c) in col.withIndex()){
                if(lines.size <= idx){
                    lines.add(if(c == 0)  "." else c.toString())
                }else{
                    lines[idx] += if(c == 0)  "." else c.toString()
                }
            }
        }

        lines.forEach { it -> println(it) }
    }

    // Wouldn't prevent Array out of bounce
    private fun draw(pos: Position, value: Int,  field: Array<Array<Int>>){
        field[pos.x][pos.y] += value
    }

    // only horizontal and vertical lines
    private fun drawLineHV(pos1: Position, pos2: Position, value: Int, field: Array<Array<Int>>){
        val diffX = pos2.x - pos1.x
        val diffY = pos2.y - pos1.y

        val isNegative = diffX < 0 || diffY < 0

        for(x in if(isNegative) (if(diffX != 0) diffX else diffY) .. 0 else 0 .. (if(diffX != 0) diffX else diffY)){
            draw(
                Position(
                    if(diffX != 0) pos1.x + x else pos1.x,
                    if(diffX != 0) pos1.y else pos1.y + x
                ),
                value,
                field
            )
        }
    }

    // Wouldn't prevent Array out of bounce
    private fun drawLine(pos1: Position, pos2: Position, value: Int, field: Array<Array<Int>>){
        // Check if it's  a horizontal or vertical line, if it is draw normally
        if((pos1.x == pos2.x) || (pos1.y == pos2.y)){
            drawLineHV(pos1, pos2, value, field)
            return
        }

        val diff = pos2.x - pos1.x

        val isXNegative = diff < 0

        val isYNegative = pos2.y - pos1.y < 0

        for(inc in (if (diff < 0 ) diff .. 0 else 0 .. diff)){
            draw(
                Position(
                    if(!isXNegative) pos1.x + abs(inc) else pos1.x - abs(inc),
                    if(!isYNegative) pos1.y + abs(inc) else pos1.y - abs(inc)
                ),
                value, field
            )
        }

    }
}
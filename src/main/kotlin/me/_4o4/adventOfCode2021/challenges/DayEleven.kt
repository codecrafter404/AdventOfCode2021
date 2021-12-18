package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayEleven : Challenge {
    override fun partOne() {
        val lines = File("data/d11_p1.txt").readLines()
        val board = parseInput(lines)
        var flashTotal = 0

        for(x in 1..100){
            flashTotal += simulateStep(board)
            println("After step $x($flashTotal Flashes)")
            printBord(board)
            println()

        }
        println("Total flashes: $flashTotal")
    }

    override fun partTwo() {
        val lines = File("data/d11_p1.txt").readLines()
        val board = parseInput(lines)
        var flashTotal = 0

        var counter = 0
        while (!wasFlashBomb(board)){
            flashTotal += simulateStep(board)
            println("After step $counter($flashTotal Flashes)")
            printBord(board)
            println()
            counter++
        }

        println("Total flashes: $flashTotal")
        println("Total simulations: $counter")
    }

    private fun wasFlashBomb(board: MutableList<MutableList<Int>>): Boolean{
        var wasFlashBomb = true
        for((y, yItem) in board.withIndex()){
            for((x, _) in yItem.withIndex()){
                if(board[x][y] != 0) wasFlashBomb = false
            }
        }
        return wasFlashBomb
    }

    private fun printBord(board: MutableList<MutableList<Int>>){
        val lines = mutableListOf<String>()

        for(col in board){
            for((idx, c) in col.withIndex()){
                if(lines.size <= idx){
                    lines.add(c.toString())
                }else{
                    lines[idx] += c.toString()
                }
            }
        }

        lines.forEach { it -> println(it) }
    }

    private fun simulateStep(board: MutableList<MutableList<Int>>): Int{

        // Increase all by 1
        for((y, yItem) in board.withIndex()){
            for((x, _) in yItem.withIndex()){
                board[x][y]++
            }
        }
        // Simulate flashes


        return simulateFlashes(board)
    }
    private fun simulateFlashes(board: MutableList<MutableList<Int>>, alreadyFlashed: MutableList<Pair<Int, Int>> = mutableListOf()): Int{
        var flashes = 0

        for((y, yItem) in board.withIndex()){
            for((x, _) in yItem.withIndex()){
                if(board[x][y] > 9){
                    if(alreadyFlashed.contains(Pair(x, y))) continue
                    flashes++
                    board[x][y] = 0
                    alreadyFlashed.add(Pair(x, y))
                    for(neighbor in findNeighbours(x, y, board)){
                        if(!alreadyFlashed.contains(Pair(neighbor.first, neighbor.second))) board[neighbor.first][neighbor.second]++
                        flashes += simulateFlashes(board, alreadyFlashed)
                    }
                }
            }
        }
        return flashes
    }

    private fun findNeighbours(x: Int, y: Int, board: MutableList<MutableList<Int>>): List<Pair<Int, Int>>{
        val directions = listOf(
            Pair(0, -1),
            Pair(1, 0),
            Pair(0, 1),
            Pair(-1, 0),
            Pair(-1, -1),
            Pair(1, -1),
            Pair(1, 1),
            Pair(-1, 1)
        )

        val neighbours = mutableListOf<Pair<Int, Int>>()


        for(direction in directions){
            if(x + direction.first < board.size && x + direction.first >= 0 && y + direction.second < board[0].size && y + direction.second >= 0){
                neighbours.add(Pair(x + direction.first, y + direction.second))
            }
        }
        return neighbours
    }

    private fun parseInput(lines: List<String>): MutableList<MutableList<Int>>{
        val res = mutableListOf<MutableList<Int>>()
        lines.forEachIndexed { x, xItem ->
            xItem.forEachIndexed { y, yItem ->
                if(res.size <= y){
                    res.add(mutableListOf(yItem.digitToInt()))
                }else{
                    res[y].add(yItem.digitToInt())
                }
            }
        }
        return res
    }
}
package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayNine : Challenge {

    override fun partOne() {
        val lines = File("data/d9_p1.txt").readLines()
        val heightMap = parseInput(lines)
        val lows = getLows(heightMap)

        println("Lows: ${lows.size}")
        var risk = 0
        lows.forEach { risk += heightMap[it.first][it.second] + 1 }
        println("Risk level: $risk")

    }

    override fun partTwo() {
        val lines = File("data/d9_p1.txt").readLines()
        val heightMap = parseInput(lines)
        val lows = getLows(heightMap)

        val basins = mutableListOf<List<Pair<Int, Int>>>()

        lows.forEach { basins.add(getBasin(it.first, it.second, heightMap)) }

        basins.sortByDescending { it.size }


        println("Basins count: ${basins.size}")
        println("Size of the:")
        println("- Largest basin: ${basins[0].size}")
        println("- Second largest basin: ${basins[1].size}")
        println("- 3rd largest basin: ${basins[2].size}")

        println("Multiplied sizes: ${basins[0].size * basins[1].size * basins[2].size}")

    }

    private fun getBasin(x: Int, y: Int, map: List<List<Int>>, basinList: MutableList<Pair<Int, Int>> = mutableListOf()): List<Pair<Int, Int>>{

        if(basinList.contains(Pair(x, y))) return basinList

        basinList.add(Pair(x, y))

        for(neighbour in findNeighboursBasin(x, y, map)){
            basinList.addAll(getBasin(neighbour.first, neighbour.second, map, basinList) - basinList.toSet())
        }

        return basinList

    }

    private fun findNeighboursBasin(x: Int, y: Int, map: List<List<Int>>): List<Pair<Int, Int>>{
        val neighbours = findNeighbours(x, y, map).toMutableList()
        neighbours.removeAll { map[it.first][it.second] == 9 }
        return neighbours
    }

    private fun parseInput(lines: List<String>): List<List<Int>>{
        // [x][y]
        val res = mutableListOf<MutableList<Int>>()

        lines.forEachIndexed { index, line -> line.forEachIndexed { idx, char -> if(res.size <= idx)
            res.add(mutableListOf(char.digitToIntOrNull()!!)) else if(res[idx].size <= index) res[idx].add(char.digitToIntOrNull()!!)
            else char.digitToIntOrNull()!! } }
        return res.toList()
    }

    private fun getLows(heightMap: List<List<Int>>):List<Pair<Int, Int>>{
        val lows = mutableListOf<Pair<Int, Int>>()

        for((x, xItem) in heightMap.withIndex()){
            for((y, _) in xItem.withIndex()){
                if(checkForLow(x, y, findNeighbours(x, y, heightMap), heightMap)) lows.add(Pair(x, y))
            }
        }
        return lows
    }

    private fun checkForLow(x: Int, y: Int, neighbours: List<Pair<Int, Int>>, map: List<List<Int>>): Boolean{

        return !neighbours.any { map[it.first][it.second] <= map[x][y] }
    }

    private fun findNeighbours(x: Int, y: Int, map: List<List<Int>>): List<Pair<Int, Int>>{
        val directions = listOf(
            Pair(0, -1),
            Pair(1, 0),
            Pair(0, 1),
            Pair(-1, 0)
        )

        val neighbours = mutableListOf<Pair<Int, Int>>()


        for(direction in directions){
            if(x + direction.first < map.size && x + direction.first >= 0 && y + direction.second < map[0].size && y + direction.second >= 0){
                neighbours.add(Pair(x + direction.first, y + direction.second))
            }
        }
        return neighbours
    }
}
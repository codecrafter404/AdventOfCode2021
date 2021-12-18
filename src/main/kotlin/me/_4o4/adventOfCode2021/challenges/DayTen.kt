package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayTen : Challenge {
    override fun partOne() {
        val lines = File("data/d10_p1.txt").readLines()

        var totalPoints = 0
        lines.forEach {
            totalPoints += getSyntaxErrorScoreOfLine(it, true)
        }
        println("Total syntax error score: $totalPoints")
    }

    override fun partTwo() {
        val lines = File("data/d10_p1.txt").readLines().toMutableList()
        lines.removeAll { getSyntaxErrorScoreOfLine(it) > 0 }

        val completionScores = mutableListOf<Long>()
        lines.forEach { completionScores.add(completeLine(it, true)) }

        completionScores.sort()
        println("Middle completion score: ${completionScores[(completionScores.size - 1) / 2]}")

    }

    private fun completeLine(line: String, log: Boolean = false): Long{
        val openBrackets = mutableListOf<Char>()

        val pointsTable = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )

        line.forEach {
            if(isOpeningBracket(it)) openBrackets.add(it) else openBrackets.removeAt(openBrackets.size - 1)
        }

        if(log){
            openBrackets.reverse()
            val closedBrackets = mutableListOf<Char>()
            openBrackets.forEach { closedBrackets.add(getCorrespondingBracket(it)) }
            println("'$line' - Complete by adding ${closedBrackets.joinToString("")}")
        }

        var completionScore: Long = 0
        openBrackets.forEach { completionScore = (completionScore * 5) + pointsTable[it]!! }

        return completionScore
    }

    private fun getSyntaxErrorScoreOfLine(line: String, log: Boolean = false): Int{
        val pointsTable = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        val lastOpenBracket = mutableListOf('X')

        var points = 0

        for(char in line){
            if(isOpeningBracket(char)) lastOpenBracket.add(char)
            if(isClosingBracket(char)){
                if(char != getCorrespondingBracket(lastOpenBracket[lastOpenBracket.size - 1])){
                    if(log) println("Line: '$line' - Expected ${getCorrespondingBracket(lastOpenBracket[lastOpenBracket.size - 1])}, but found $char instead")
                    points += pointsTable[char]!!
                }
                if(lastOpenBracket.size > 0) lastOpenBracket.removeAt(lastOpenBracket.size - 1)
            }
        }

        return points
    }

    private fun isClosingBracket(char: Char): Boolean{
        return char == ']' || char == ')' || char == '>' || char == '}'
    }

    private fun isOpeningBracket(char: Char): Boolean{
        return !isClosingBracket(char)
    }

    private fun getCorrespondingBracket(char: Char): Char{
        when(char){
            '(' -> return ')'
            '[' -> return ']'
            '{' -> return '}'
            '<' -> return '>'
            ')' -> return '('
            ']' -> return '['
            '}' -> return '{'
            '>' -> return '<'
        }
        return '#'
    }
}
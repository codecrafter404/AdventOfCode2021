package me._4o4.adventOfCode2021.challenges

import java.io.File


class Field(var value: String) {
    var marked = false
    override fun toString(): String {
        return "{$value|$marked}"
    }
}


class DayFour : Challenge {

    override fun partOne() {
        val (numbersLine, blocks) = readNumbersAndBoards()

        var prevNum = -1
        var wonIndex = 0
        var won = false

        // simulate all games until one wins
        for(number in numbersLine.split(",")){
            simulateGame(blocks, number.toInt())
            val x = checkWin(blocks)
            if(x.size > 0){
                won = true
                wonIndex = x[0]
                prevNum = number.toInt()
                break
            }
            prevNum = number.toInt()
        }



        if(won){
            var winSum = 0
            blocks[wonIndex].forEach { x -> x.forEach { y -> if(!y.marked) winSum += y.value.toInt()}}
            println("Winner Board: $wonIndex")
            println("Previous Number(A): $prevNum")
            println("Winner Board Score(B): $winSum")
            println("Result(A * B): ${prevNum * winSum}")

        }else{
            println("No Board wins")
        }




    }

    override fun partTwo() {
        val (numbersLine, blocks) = readNumbersAndBoards()

        var prevWInNumber = 0

        var i = 0
        while(blocks.size > 1){
            simulateGame(blocks, numbersLine.split(",")[i].toInt())

            val won_res = checkWin(blocks)

            if(won_res.size > 0){
                prevWInNumber = numbersLine.split(",")[i].toInt()
                // need to do this, because if not, the result wouldn't be correct
                if(!(won_res.size == 2 && blocks.size == 2)){
                    var z = blocks.size -1
                    while(z > 0){
                        if(won_res.contains(z)){

                            if(blocks.size != 1) blocks.removeAt(z)
                        }
                        z--
                    }
                }else{
                    blocks.removeAt(won_res[0])
                }
            }

            i++
        }

        simulateGame(blocks, prevWInNumber)
        // Calculate Score
        var score = 0
        blocks[0].forEach {x -> x.forEach { y -> if(!y.marked) score += y.value.toInt() }}
        println("Board Score (A): $score")
        println("Last winning number (B): $prevWInNumber")
        println("Result (A*B): ${score * prevWInNumber}")
    }

    private fun readNumbersAndBoards(): Pair<String, MutableList<List<List<Field>>>>{
        val lines = File("data/d4_p1.txt").readLines()

        var numbersLine = ""

        var currentLine = 0

        for((lineNumber, line) in lines.withIndex()){
            if(lineNumber == 0) {numbersLine += line; continue}

            if(numbersLine.endsWith(",")){
                numbersLine += line
            }else{
                currentLine = lineNumber
                break
            }
        }




        // List of blocks
        val blocksRaw = mutableListOf<MutableList<Field>>()

        val linesPerBlock = 5
        var currentBlockLine = 0

        var currentBlock = 0

        currentLine += 1

        for((idx, x) in lines.withIndex()){
            if(idx < currentLine) continue
            //if(x == "") continue

            // Read Line of block
            if(currentBlockLine < linesPerBlock){
                if(currentBlockLine == 0){
                    blocksRaw.add(mutableListOf())
                }
                for(chunk in x.chunked(3)){
                    blocksRaw[currentBlock].add(Field(chunk.replace(" ", "")))
                }


            }else{
                currentBlock++
                currentBlockLine = 0
                continue
            }
            currentBlockLine++
        }

        // 3D list [block][Y][X]
        val blocks = mutableListOf<List<List<Field>>>()

        for(block in blocksRaw){
            blocks.add(block.chunked(5))
        }
        return Pair(numbersLine, blocks)
    }

    private fun simulateGame(blocks: MutableList<List<List<Field>>>, number: Int){
        blocks.forEach { x -> x.forEach { y -> y.forEach {z -> if(z.value == number.toString()) z.marked = true } } }
    }

    // returns a pair containing the last winning block
    private fun checkWin(blocks: MutableList<List<List<Field>>>): MutableList<Int>{
        val wins = mutableListOf<Int>()

        // Check X axis
        for((idx, block) in blocks.withIndex()) {

            for (col in block) {
                if (col[0].marked && col[1].marked && col[2].marked && col[3].marked && col[4].marked) {
                    if(!wins.contains(idx)) wins.add(idx)
                }
            }
        }


        // Check x
        for((idx, block) in blocks.withIndex()) {
            for(i in 0..4){
                if(block[0][i].marked && block[1][i].marked && block[2][i].marked && block[3][i].marked && block[4][i].marked){
                    if(!wins.contains(idx))  wins.add(idx)
                }
            }
        }

        return wins
    }
}
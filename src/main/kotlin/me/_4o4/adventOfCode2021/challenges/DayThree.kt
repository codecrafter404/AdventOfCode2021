package me._4o4.adventOfCode2021.challenges

import java.io.File

class DayThree : Challenge{
    override fun partOne() {
        val rows = File("data/d3_p1.txt").readLines()
        val cols = convertRowToColumn(rows)

        var gamma = ""
        var epsilon = ""

        for(col in cols){
            if(col.count { x -> x.toString() == "1" } > col.count { x -> x.toString() == "0" }){
                // more 1 than 0
                gamma += "1"
                epsilon += "0"
            }else{
                // more 0 than 1
                gamma += "0"
                epsilon += "1"
            }
        }
        println("Gamma: $gamma (${gamma.toInt(2)})\nEpsilon: $epsilon (${epsilon.toInt(2)})")
        println("Power consumption (Gamma * Epsilon): ${gamma.toInt(2) * epsilon.toInt(2)}")

    }

    override fun partTwo() {
        val rows = File("data/d3_p1.txt").readLines()

        val oxygenGeneratorList = rows.toMutableList()
        val co2ScrubberList = rows.toMutableList()

        // Calculate Oxygen
        var pos = 0

        while(oxygenGeneratorList.size > 1){
            // convert to row
            var row = ""
            for(x in oxygenGeneratorList){
                row += x[pos]
            }

            val zeros = row.count { x -> x.toString() == "0" }
            val ones = row.count { x -> x.toString() == "1" }

            val sel = if(zeros == ones) "1" else if(ones > zeros) "1" else "0"
            oxygenGeneratorList.removeAll { x -> x[pos].toString() != sel  }
            pos++
        }

        // Reset for co2 scrubber calculation
        pos = 0
        while(co2ScrubberList.size > 1){
            // convert to row
            var row = ""
            for(x in co2ScrubberList){
                row += x[pos]
            }

            val zeros = row.count { x -> x.toString() == "0" }
            val ones = row.count { x -> x.toString() == "1" }

            val sel = if(zeros == ones) "0" else if(ones > zeros) "0" else "1"
            co2ScrubberList.removeAll { x -> x[pos].toString() != sel  }
            pos++
        }


        println("oxygen generator rating(A): ${oxygenGeneratorList[0]} (${oxygenGeneratorList[0].toInt(2)})")
        println("CO2 scrubber rating(B): ${co2ScrubberList[0]} (${co2ScrubberList[0].toInt(2)})")
        println("Result(A * B): ${oxygenGeneratorList[0].toInt(2) * co2ScrubberList[0].toInt(2)}")
    }


    private fun convertRowToColumn(rows: List<String>): List<String>{
        val cols = mutableListOf<String>()

        for(row in rows){
            for((index, char) in row.withIndex()){
                if(cols.size <= index){
                    cols.add(index, char.toString())
                }else{
                    cols[index] += char.toString()
                }
            }
        }
        return cols
    }

}
package org.cesar.day04

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")

        val lines = inputFile.readLines()
        val paperRollsMatrix: Array<CharArray> = lines.map {
            it.toCharArray()
        }.toTypedArray()

        var previousAnswer: Long
        do {
            previousAnswer = answer
            paperRollsMatrix.countAccessibleRollsOfPaper()
        } while (previousAnswer != answer)

        //paperRollsMatrix.printResult()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

fun Array<CharArray>.printResult() {
    for ((num, row) in this.withIndex()) {
        val line = row.joinToString(" ")
        println("$num | $line")
    }
}


private fun Array<CharArray>.isRollOfPaperAccessible(i: Int, j: Int): Boolean {
    if (this[i][j] == '.' || this[i][j] == 'X') {
        return false
    }

    var rollsAround = 0

    for (x in -1 .. 1 ) {
        for (y in -1 .. 1 ) {
            if (i+x == -1 || j+y == -1 || i+x == this.size || j+y == this[0].size || (x == 0 && y == 0)) {
                continue
            }

            if (this[i+x][j+y] == '@') {
                rollsAround++
            }

            if (rollsAround == 4) {
                return false
            }
        }
    }

    this[i][j] = 'X'
    return true
}

private fun Array<CharArray>.countAccessibleRollsOfPaper() {
    val height = this.size
    val width = this[0].size

    for (i in 0 until height) {
        for (j in 0 until width) {
            if (this.isRollOfPaperAccessible(i, j)) {
                answer++
            }
        }
    }
}

private fun readFile(file: String): File {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    return File(inputURL.toURI())
}
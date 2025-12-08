package org.cesar.day07

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

        val manifold = lines.map { it.toCharArray() }
        manifold.printResult()
        println("=====================================")
        answer = manifold.fireTachyonBeam()
        manifold.printResult()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun List<CharArray>.fireTachyonBeam(): Long {
    var beamSplit = 0L

    this.dropLast(1).withIndex().forEach { (lineNum, line) ->
        line.withIndex().forEach { (charPos, char) ->
            if (char == '|' || char == 'S') {
                if (this[lineNum.plus(1)][charPos] == '.') {
                    this[lineNum.plus(1)][charPos] = '|'

                } else if (this[lineNum.plus(1)][charPos] == '^') {
                    this[lineNum.plus(1)][charPos.minus(1)] = '|'
                    this[lineNum.plus(1)][charPos.plus(1)] = '|'
                    beamSplit++
                }
            }
        }
    }

    return beamSplit
}

private fun readFile(file: String): File {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    return File(inputURL.toURI())
}

fun <T> List<T>.printResult() {
    for ((num, row) in withIndex()) {
        val line = when (row) {
            is Array<*>      -> row.joinToString(" ")
            is Collection<*> -> row.joinToString(" ")
            is CharArray     -> row.joinToString(" ")
            else             -> row.toString()
        }

        val numPadded = num.toString().padEnd(3, ' ')
        println("$numPadded | $line")
    }
}

package org.cesar.day07

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

data class Cell(
    var char: Char,
    var timelines: Long = 0L
)

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

        val manifold = lines.map { it.toCharArray() }
        //manifold.printResult()

        val manifoldWithTimelines = mapCharToCell(manifold)

        //answer = manifold.fireTachyonBeam()
        answer = manifoldWithTimelines.fireQuantumTachyonBeam()

        //println("    ==================================================================    ")
        //manifoldWithTimelines.printResultWithTimelines()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

fun mapCharToCell(manifold: List<CharArray>): List<List<Cell>> {
    return manifold.map { charArray ->
        charArray.map { char ->
            when (char) {
                'S' -> Cell(char, 1L)
                else -> Cell(char)
            }
        }
    }
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

private fun List<List<Cell>>.fireQuantumTachyonBeam(): Long {
    this.dropLast(1).withIndex().forEach { (lineNum, line) ->
        line.withIndex().forEach { (charPos, cell) ->
            if (cell.char == '|' || cell.char == 'S') {
                if (this[lineNum.plus(1)][charPos].char == '.' || this[lineNum.plus(1)][charPos].char == '|') {
                    this[lineNum.plus(1)][charPos].char = '|'
                    this[lineNum.plus(1)][charPos].timelines += cell.timelines

                } else if (this[lineNum.plus(1)][charPos].char == '^') {
                    this[lineNum.plus(1)][charPos.minus(1)].char = '|'
                    this[lineNum.plus(1)][charPos.minus(1)].timelines += cell.timelines
                    this[lineNum.plus(1)][charPos.plus(1)].char = '|'
                    this[lineNum.plus(1)][charPos.plus(1)].timelines += cell.timelines
                }
            }
        }
    }

    return this.countTimelines()
}

private fun List<List<Cell>>.countTimelines(): Long {
    return this.last().sumOf { it.timelines }
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

fun List<List<Cell>>.printResultWithTimelines() {
    for ((num, line) in withIndex()) {
        val chars = line.joinToString(" ") { it.char.toString() }
        val timelines = line.joinToString(" ") { it.timelines.toString().padStart(13, '0') }

        val numPadded = num.toString().padEnd(3, ' ')
        println("$numPadded | $chars  ║  $timelines")
    }
}

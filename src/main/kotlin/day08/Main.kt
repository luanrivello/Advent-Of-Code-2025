package org.cesar.day08

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

data class JunctionBox (
    val x: Long,
    val y: Long,
    val z: Long,
)

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("example.txt")
        val lines = inputFile.readLines()
        val boxesPositions = lines.map {
            val positions = it.split(',')
            JunctionBox(positions[0].toLong(), positions[1].toLong(), positions[2].toLong())
        }

        boxesPositions.printResult()

        //answer =
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
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

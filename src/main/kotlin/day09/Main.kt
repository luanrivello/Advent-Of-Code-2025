package org.cesar.day09

import java.io.File
import kotlin.system.measureNanoTime

var answer = 0L

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()
        val tilePositions = lines.map {
            val (x, y) = it.split(',')
            x to y
        }

        answer = tilePositions.getLargestRetangleArea()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun List<Pair<String, String>>.getLargestRetangleArea(): Long {
    TODO("Not yet implemented")
    return 0L
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

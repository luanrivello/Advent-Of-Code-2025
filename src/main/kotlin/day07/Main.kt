package org.cesar.day07

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("example.txt")
        val lines = inputFile.readLines()

        //val (worksheet, operators) = extractProblems(lines)

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

fun List<List<String>>.printResult() {
    for ((num, row) in this.withIndex()) {
        val line = row
        //val line = row.joinToString(" ")
        println("$num | $line")
    }
}

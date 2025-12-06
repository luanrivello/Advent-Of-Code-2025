package org.cesar.day06

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.split

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun readFile(file: String): File {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    return File(inputURL.toURI())
}
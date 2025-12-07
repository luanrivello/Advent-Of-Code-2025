package org.cesar.day06

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.split

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

        val worksheet: List<List<String>> = lines.map {
            it.split(" ")
              .filter { it.isNotBlank() }
        }

        worksheet.printResult()
        answer = worksheet.doTheMath()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun List<List<String>>.doTheMath(): Long {
    require(isNotEmpty()) { "Matrix must not be empty" }

    val dataRows = this.subList(0, size - 1)
    val opRow = last()

    return this[0].indices.sumOf { column ->
        if (opRow[column] == "*") {
            dataRows.fold(1L) { mult, row ->
                mult * row[column].toLong()
            }
        } else if (opRow[column] == "+") {
            dataRows.sumOf {
                it[column].toLong()
            }
        } else {
            error("Unsuported operation")
        }
    }
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

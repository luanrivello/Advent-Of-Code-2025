package org.cesar.day03

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.toInt

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile  = readFile("/day03/input.txt")

        inputFile.forEachLine { line ->
            findMaximumVoltage(line)
        }
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun readFile(path: String): File {
    val inputURL = {}.javaClass.getResource(path)
    return File(inputURL.toURI())
}

fun findMaximumVoltage(line: String) {
    val digits = line.map { it.digitToInt() }
    val voltage = Array(12) { 0 }
    var lastIndice = -1

    for (i in voltage.indices) {
        val stop = if (4+i < digits.size) {
            digits.size -voltage.size +i
        } else {
            digits.size -1
        }

        for (j in lastIndice+1..stop) {
            if (digits[j] > voltage[i]) {
                voltage[i] = digits[j]
                lastIndice = j
            }
        }
    }

    answer += voltage.joinToString("").toLong()
}

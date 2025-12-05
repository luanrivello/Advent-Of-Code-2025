package org.cesar.day02

import java.io.File
import kotlin.jvm.java
import kotlin.system.measureNanoTime

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val ranges = inputFile.split(',')

        for (range in ranges) {
            val (lowerBound, upperBound) = range.split('-', limit = 2)
            findInvalidIDsInRange(lowerBound.toLong(), upperBound.toLong())
        }
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun readFile(file: String): String {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    val inputFile = File(inputURL.toURI())

    return inputFile.readText()
}

private fun findInvalidIDsInRange (lowerBound: Long, upperBound: Long) {
    for (id in lowerBound .. upperBound) {
        if (id.isInvalidId()) {
            answer += id
        }
    }
}

private fun Long.isInvalidId(): Boolean {
    val longStr = this.toString()
    val digitCount = longStr.length

    if (digitCount == 1) return false

    for (i in 1 .. digitCount/2) {
        val firstSequence = longStr.take(i)
        var j = i

        while (i+j <= digitCount) {
            val nextSequence = longStr.substring(j, j+i)

            if (!firstSequence.equals(nextSequence)) {
                break
            }

            j += i
        }

        if (j+i == digitCount) {
            return true
        }
    }

    return false
}
package org.cesar

import java.io.File
import kotlin.system.measureNanoTime

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile  = readFile("/day02/input.txt")
        val ranges = inputFile.readText().split(',')

        for (range in ranges) {
            val (lowerBound, upperBound) = range.split('-', limit = 2)
            findInvalidIDsInRange(lowerBound.toLong(), upperBound.toLong())
        }
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun readFile(path: String): File {
    val inputURL = {}.javaClass.getResource(path)
    return File(inputURL.toURI())
}

private fun findInvalidIDsInRange (lowerBound: Long, upperBound: Long) {
    for (id in lowerBound .. upperBound) {
        if (id.isInvalidId()) {
            answer += id
        }
    }
}

private fun Long.isInvalidId(): Boolean {
    val digitCount = this.length()

    if (digitCount == 1) {
        return false

    } else {
        for (i in 1 .. digitCount/2) {
            val longStr = this.toString()
            val firstSequence = longStr.take(i)
            var j = i

            while (i+j <= digitCount) {
                val nextSequence = longStr.substring(j, j+i)

                if (!firstSequence.equals(nextSequence)) {
                    break
                } else if (j+i >= digitCount) {
                    return true
                }

                j += i
            }
        }
    }

    return false
}

fun Long.length(): Int =
    this.toString().length
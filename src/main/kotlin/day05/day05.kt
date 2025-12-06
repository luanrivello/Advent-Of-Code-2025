package org.cesar.day05

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.text.split

var answer:Long = 0

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()

        val (ranges, ids) = extractRangesAndIds(lines)

        answer = ids.countValidIdsIn(ranges)
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun List<Long>.countValidIdsIn(ranges: List<Pair<Long, Long>>): Long {
    var count = 0L

    this.forEach idsLoop@{ id ->
        ranges.forEach { range ->
            if (id >= range.first && id <=range.second){
                count++
                return@idsLoop
            }
        }
    }

    return count
}

private fun extractRangesAndIds(lines: List<String>): Pair<List<Pair<Long, Long>>, List<Long>> {
    val blankLineIndex = lines.indices.first { lines[it].isEmpty() }
    val ranges =
        lines.subList(0, blankLineIndex)
            .map {
                val (lowerBound, upperBound) =
                    it.split('-', limit = 2)

                lowerBound.toLong() to upperBound.toLong()
            }

    val ids =
        lines.subList(blankLineIndex+1, lines.size)
            .map { it.toLong() }

    return ranges to ids
}

private fun readFile(file: String): File {
    val day = object {}::class.java.packageName.substringAfterLast('.')
    val inputURL = {}.javaClass.getResource("/$day/$file")
    return File(inputURL.toURI())
}
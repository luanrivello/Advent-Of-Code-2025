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

        //answer = ids.countValidIdsIn(ranges)
        answer = ranges.countValidIds()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun List<Pair<Long, Long>>.countValidIds(): Long {
    var count = 0L

    val sorted = this.sortedBy{ it.first }

    sorted.withIndex().forEach { (index, range) ->
        var newLowerBound =
            sorted.subList(0, index)
                .filter { range.first in it.first..it.second}
                .maxByOrNull { it.second }
                ?.let { it.second +1 }
                ?: range.first

        var newUpperBound =
            sorted.subList(0, index)
                .filter { range.second in it.first..it.second}
                .maxByOrNull { it.first }
                ?.let { it.first -1 }
                ?: range.second

        //println("$newLowerBound -> ${newUpperBound} +${newUpperBound -newLowerBound +1}")
        if (newLowerBound <= newUpperBound) {
            count += newUpperBound -newLowerBound +1
        }
    }

    return count
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
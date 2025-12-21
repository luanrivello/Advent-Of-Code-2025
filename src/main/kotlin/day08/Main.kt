package org.cesar.day08

import java.io.File
import kotlin.math.sqrt
import kotlin.system.measureNanoTime

var answer:Long = 0
val CONNECTIONS = 1000

data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double,
    var closest: Pair<JunctionBox, Double>? = null,
    val conections: MutableList<JunctionBox> = mutableListOf()
) {
    override fun toString(): String {
        //val bx = closest?.first?.x.toString() ?: "0"
        //val by = closest?.first?.y.toString() ?: "0"
        //val bz = closest?.first?.z.toString() ?: "0"
        //val distance = closest?.second?.let { "%.3f".format(it) } ?: "none"

        //return "X:$x Y:$y Z:$z -> X:$bx Y:$by Z:$bz D:$distance"
        return "[X:$x Y:$y Z:$z]"
    }
}

fun main() {
    val time = measureNanoTime {
        val inputFile = readFile("input.txt")
        val lines = inputFile.readLines()
        val boxesPositions = lines.map { it.toJunctionBox() }

        answer = boxesPositions.makeCircuits()
        //println("  ===============================================  ")
        //boxesPositions.sortedBy { it.closest?.second ?: Double.MAX_VALUE }.printResult()
        //boxesPositions.printResult()
    }

    println("Execution time: ${time / 1_000_000_000.0}s")
    println("Answer: $answer")
}

private fun JunctionBox.findClosestOn(boxes: List<JunctionBox>) {
    var closest = Double.MAX_VALUE
    var distance: Double
    boxes.forEach {
        distance = this.distanceTo(it)
        if (distance < closest && distance != 0.0) {
            this.closest = Pair(it, distance)
            closest = distance
        }
    }
    //println(this) }
}

private fun List<JunctionBox>.makeCircuits(): Long {
    val circuits = mutableListOf<MutableList<JunctionBox>>()
    val boxes = this.toMutableList()

    boxes.forEach { it.findClosestOn(this) }

    repeat(CONNECTIONS) {
        val firstBox = boxes.minBy { box ->
            if (box.conections.contains(box.closest!!.first))
                Double.MAX_VALUE
            else
                box.closest!!.second
        }

        val secondBox = firstBox.closest?.first!!

        val circuitA = circuits.find { firstBox in it }
        val circuitB = circuits.find { secondBox in it }

        when {
            circuitA != null && circuitB == null -> {
                circuitA.add(secondBox)
                firstBox.findClosestOn(boxes.filter { circuitA.contains(it) })
                secondBox.findClosestOn(boxes.filter { circuitA.contains(it) })
            }

            circuitA == null && circuitB != null -> {
                circuitB.add(firstBox)
                firstBox.findClosestOn(boxes.filter { circuitB.contains(it) })
                secondBox.findClosestOn(boxes.filter { circuitB.contains(it) })
            }

            circuitA == null && circuitB == null -> {
                val newCircuit = mutableListOf(firstBox, secondBox)
                circuits.add(newCircuit)
                firstBox.findClosestOn(boxes.filter { it != secondBox })
                secondBox.findClosestOn(boxes.filter { it != firstBox })
            }

            circuitA !== circuitB -> {
                circuitA!!.addAll(circuitB!!)
                circuits.remove(circuitB)
                firstBox.findClosestOn(boxes.filter { circuitA.contains(it) })
                secondBox.findClosestOn(boxes.filter { circuitA.contains(it) })
            }

            else -> Unit
        }

        firstBox.conections.add(secondBox)
        secondBox.conections.add(firstBox)

    }

    //circuits.sortedByDescending { it.size }.printResult()
    return circuits
                .map { it.size }
                .sortedDescending()
                .take(3)
                .fold(1L) { acc, size -> acc * size }
}

private fun JunctionBox.distanceTo(box: JunctionBox): Double {
    val dx = this.x - box.x
    val dy = this.y - box.y
    val dz = this.z - box.z

    val distance = sqrt((dx*dx + dy*dy + dz*dz))
    //println("Distance: $distance")

    return distance
}

fun String.toJunctionBox(): JunctionBox {
    val (x, y, z) = split(',')
    return JunctionBox(x.toDouble(), y.toDouble(), z.toDouble())
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

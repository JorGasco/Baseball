package utils

import models.Players
import models.Stats
object Utilities {

    //NOTE: JvmStatic annotation means that the methods are static (i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun formatSetString(groupsToFormat: Set<Stats>): String =
        groupsToFormat
            .joinToString(separator = "\n") { stats ->  "\t$stats"
            }
}
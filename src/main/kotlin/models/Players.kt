package models

import utils.Utilities

//import utils.Utilities

data class Players(
    var playerId: Int = 0,
    var playerName: String,
    var playerSurname: String,
    var age: Int,
    var height: Double,
    var weight: Double,
    var position: String,
    var isNoteArchived: Boolean,
    var stats : MutableSet<Stats> = mutableSetOf()) {

    private var lastStatsId = 0
    private fun getStatsId() = lastStatsId++

    fun addStats(s: Stats): Boolean {
        s.statsId = getStatsId()
        return stats.add(s)
    }
}


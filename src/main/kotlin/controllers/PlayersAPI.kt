package controllers

import models.Players
import persistence.Serializer

class PlayersAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var players = ArrayList<Players>()

    fun add(p: Players): Boolean {
        return this.players.add(p)
    }

    fun numberOfPlayers(): Int {
        return players.size
    }


    fun deleteNote(indexToDelete: Int): Players? {
        return if (isValidListIndex(indexToDelete, players)) {
            players.removeAt(indexToDelete)
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}
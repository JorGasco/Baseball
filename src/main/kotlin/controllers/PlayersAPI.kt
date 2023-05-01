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

    @Throws(Exception::class)
    fun load() {
        players = serializer.read() as ArrayList<Players>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(players)
    }

    fun listPlayers(): String {
        return if (players.isEmpty()) {
            "No notes stored"
        } else {
            var listPlayers = ""
            for (i in players.indices) {
                listPlayers += "${i}: ${players[i]} \n"
            }
            listPlayers
        }
    }
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, players);
    }



    fun updatePlayer(indexToUpdate: Int, players: Players?): Boolean {
        //find the note object by the index number
        val foundPlayer = findPlayer(indexToUpdate)


        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundPlayer != null) && (players != null)) {
            foundPlayer.playerName = players.playerName
            foundPlayer.playerSurname = players.playerSurname
            foundPlayer.age = players.age
            foundPlayer.height = players.height
            foundPlayer.weight = players.weight
            foundPlayer.position = players.position
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun findPlayer(index: Int): Players? {
        return if (isValidListIndex(index, players)) {
            players[index]
        } else null
    }

    fun deletePlayer(indexToDelete: Int): Players? {
        return if (isValidListIndex(indexToDelete, players)) {
            players.removeAt(indexToDelete)
        } else null
    }
}
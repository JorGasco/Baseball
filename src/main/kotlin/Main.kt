


import controllers.PlayersAPI
import models.Players
import models.Stats

import mu.KotlinLogging
import persistence.JSONSerializer



import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val playersApi = PlayersAPI(JSONSerializer(File("notes.json")))

fun main(args: Array<String>) {
    runMenu()
}


fun mainMenu() : Int {
    return readNextInt(""" 
         > ----------------------------------
         > |   Manager Baseball APP         |
         > ----------------------------------
         > | Player MENU                    |
         > |   1) Add a Player              |
         > |   2) List all Players          |
         > |   3) Update a Player           |
         > |   4) Delete a Player           |
         > |   5) Search a Player by Name   |
         > | 6) Search a Player by Position |
         > |                                |
         > > -------------------------------|
         > |        Stats Part              |
         > ----------------------------------
         > |   7) Add Stats to a Player     |
         > |  8) List of players with Stats |
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > ----------------------------------
         > |   9) Back                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}


fun runMenu() {
    do {

        when (val option = mainMenu()) {

            1 -> addPlayer()
            2 -> listAllPlayers()
            3 -> updatePlayer()
            4 -> deletePlayer()
            5 -> searchName()
            6 -> searchPositions()
            7 -> addStatsToPlayer()

            8 -> listAllPlayerswithStats()

            20 -> save()
            21 -> load()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addPlayer() {
    //logger.info { "addNote() function invoked" }


    val playerName = ScannerInput.readNextLine("Enter Player's Name: ")
    val playerSurname = ScannerInput.readNextLine("Enter Player's Surname: ")
    val age = readNextInt("Enter Player's age: ")
    val height = readNextDouble("Enter Player's height: ")
    val weight = readNextDouble("Enter Player's weight: ")
    val position = ScannerInput.readNextLine(
        """
              > --------------------------------
              > | Type a position           |
              > |   1 - Infield                 |
              > |   2 - OutField                |
              > |   3 - Pitcher                 |
              > --------------------------------
     > ==>> """.trimMargin(">")
    )
    val isAdded = playersApi.add(Players(playerName=playerName, playerSurname = playerSurname, age = age, height = height, weight = weight, position=position, isNoteArchived = false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}


    fun save() {
        try {
            playersApi.store()
        } catch (e: Exception) {
            System.err.println("Error writing to file: $e")
        }
    }

    fun load() {
        try {
            playersApi.load()
        } catch (e: Exception) {
            System.err.println("Error reading from file: $e")
        }
    }

    fun listAllPlayers() {
        println(playersApi.listPlayers())

    }
fun listAllPlayerswithStats() {
    println(playersApi.listPlayers())
    }

fun updatePlayer() {
    //logger.info { "updateNotes() function invoked" }
    println(playersApi.listPlayers())
    if (playersApi.numberOfPlayers() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (playersApi.isValidIndex(indexToUpdate)) {
            val playerName = readNextLine("Enter Player's Name: ")
            val playerSurname = readNextLine("Enter Player's Surname: ")
            val age = readNextInt("Enter Player's age: ")
            val height = readNextDouble("Enter Player's height: ")
            val weight = readNextDouble("Enter Player's weight: ")
            val position = readNextLine(
                """
              > --------------------------------
              > | Choose the position           |
              > |   1 - Infield                 |
              > |   2 - OutField                |
              > |   3 - Pitcher                 |
              > --------------------------------
     > ==>> """.trimMargin(">")
            )

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (playersApi.updatePlayer(
                    indexToUpdate,
                    Players(playerName= playerName, playerSurname = playerSurname, age = age, height = height, weight = weight, position=position, isNoteArchived = false)
                    )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no Players for this index number")
        }
    }
}

fun addStatsToPlayer() {
    listAllPlayers()
    if (playersApi.numberOfPlayers() > 0) {
        // only ask the user to choose the player if players exist
        val playerId = readNextInt("Enter the ID of the player to add stats to: ")
        if (playersApi.isValidId(playerId)) {
            val player = playersApi.findPlayer(playerId)
            if (player != null) {
                val hits = readNextInt("\t Number of Hits:")
                val baseOnBall = readNextInt("\t Number of Base on Balls:")
                val strikeouts = readNextInt("\t Number of Strikeouts:")
                if (player.addStats(Stats(hits = hits, baseOnBall = baseOnBall, strikeouts = strikeouts))) {
                    println("Add Successful")
                } else {
                    println("Add not Successful")
                }
            }
        }
    }

}

    fun deletePlayer(){
        //logger.info { "deleteNote() function invoked" }
        println(playersApi.listPlayers())
        if (playersApi.numberOfPlayers() > 0) {
            //only ask the user to choose the note to delete if notes exist
            val indexToDelete = readNextInt("Enter the index of the player to delete: ")
            //pass the index of the note to NoteAPI for deleting and check for success.
            val playerToDelete = playersApi.deletePlayer(indexToDelete)
            if (playerToDelete != null) {
                println("Delete Successful! Deleted Player: ${playerToDelete.playerName} ${playerToDelete.playerSurname}")
            } else {
                println("Delete NOT Successful")
            }
        }
    }

fun searchName() {
    val searchName = readNextLine("Enter the Name to search by: ")
    val searchResults = playersApi.searchByPlayersName(searchName)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

fun searchPositions() {
    val searchPositions = readNextLine("Enter the Name to search by: ")
    val searchResults = playersApi.searchByPositions(searchPositions)
    if (searchResults.isEmpty()) {
        println("No Players found")
    } else {
        println(searchResults)
    }
}













import controllers.PlayersAPI
import models.Players
//import mu.KotlinLogging
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
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Players                   |
         > |                                |
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun playerMenu() : Int {
    return readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | SUB MENU                       |
         > |   1) Add a Player              |
         > |   2) List all Players          |
         > |   3) Update a Player           |
         > |   4) Delete a Player           |
         > |   5) Search a Player by Name  |
         > |                                |
         > ----------------------------------
         > |   20) Save notes               |
         > |   21) Load notes               |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> players()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun players(){
    do {
        val option = playerMenu()
        when (option) {
            1 -> addPlayer()
            2-> listAllPlayers()
            3  -> updatePlayer()
            4  -> deletePlayer()

            //6 -> searchPlayerByPosition()

            20 -> save()
            21 -> load()
            // 0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addPlayer() {
    //logger.info { "addNote() function invoked" }
    //val noteTitle = readNextLine("Enter a title for the note: ")
    //val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    //val noteCategory = readNextLine("Enter a category for the note: ")

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
    val isAdded = playersApi.add(Players(playerName, playerSurname, age, height, weight, position, false))

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
            val priority = readNextLine(
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
                    Players(playerName, playerSurname, age, height, weight, priority, false)
                )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
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



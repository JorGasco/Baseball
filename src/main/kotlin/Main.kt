


//import controllers.PlayersAPI
//import models.Players
//import mu.KotlinLogging
//import persistence.JSONSerializer
import controllers.PlayersAPI
import models.Players
import utils.ScannerInput
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
//import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private var players = ArrayList<Players>()

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
         > |    6) Search a Player by Name  |
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
            1  -> addPlayer()
            //    2  -> listPlayers()
            // 3  -> updateNote()
            // 4  -> deleteNote()

            //6 -> searchPlayerByPosition()

            // 0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addPlayer(){
    //logger.info { "addNote() function invoked" }
    //val noteTitle = readNextLine("Enter a title for the note: ")
    //val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    //val noteCategory = readNextLine("Enter a category for the note: ")

    val playerName = ScannerInput.readNextLine("Enter Player's Name: ")
    val playerSurname = ScannerInput.readNextLine("Enter Player's Surname: ")
    val age = readNextInt("Enter Player's age: ")
    val height = readNextDouble("Enter Player's height: ")
    val weight = readNextDouble("Enter Player's weight: ")
    val position = ScannerInput.readNextLine("""
              > --------------------------------
              > | Type a position           |
              > |   1 - Infield                 |
              > |   2 - OutField                |
              > |   3 - Pitcher                 |
              > --------------------------------
     > ==>> """.trimMargin(">"))
    val isAdded = players.add(Players( playerName,playerSurname, age, height, weight, position, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }

    /*fun deleteNote(){
        //logger.info { "deleteNote() function invoked" }
        //listPlayers()
        if (PlayersAPI.numberOfPlayers() > 0) {
            //only ask the user to choose the note to delete if notes exist
            val indexToDelete = readNextInt("Enter the index of the player to delete: ")
            //pass the index of the note to NoteAPI for deleting and check for success.
            val playerToDelete = playersApi.deleteNote(indexToDelete)
            if (playerToDelete != null) {
                println("Delete Successful! Deleted Player: ${playerToDelete.playerName} ${playerToDelete.playerSurname}")
            } else {
                println("Delete NOT Successful")
            }
        }*/

}
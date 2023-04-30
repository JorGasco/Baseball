


//import controllers.PlayersAPI
//import models.Players
//import mu.KotlinLogging
//import persistence.JSONSerializer
import utils.ScannerInput.readNextInt
//import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit


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
            // 1  -> addPlayer()
            //    2  -> listPlayers()
            // 3  -> updateNote()
            // 4  -> deleteNote()

            //6 -> searchPlayerByPosition()

            // 0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}
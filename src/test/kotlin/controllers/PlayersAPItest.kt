package controllers

import controllers.PlayersAPI
import models.Players
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import persistence.JSONSerializer
import java.io.File
import kotlin.test.assertEquals
class PlayersAPITest {
    private var player1: Players? = null
    private var player2: Players? = null
    private var player3: Players? = null
    private var player4: Players? = null
    private var player5: Players? = null
    private var populatedPlayers: PlayersAPI? = PlayersAPI(JSONSerializer(File("notes.json")))
    private var emptyPlayers: PlayersAPI? = PlayersAPI(JSONSerializer(File("notes.json")))

    @BeforeEach
    fun setup() {
        player1 = Players("Jorge", "Gasco", 12, 1.50, 50.2, "infield", false)
        player2 = Players("Dani", "Dominguez", 12, 1.60, 60.2, "outfield", false)
        player3 = Players("Patty", "Castillo", 12, 1.70, 70.2, "pitcher", true)
        player4 = Players("Diego", "Pabon", 12, 1.80, 80.2, "infield", false)
        player5 = Players("Tia", "Mercedes", 12, 1.90, 90.2, "outfield", true)
        populatedPlayers!!.add(player1!!)
        populatedPlayers!!.add(player2!!)
        populatedPlayers!!.add(player3!!)
        populatedPlayers!!.add(player4!!)
        populatedPlayers!!.add(player5!!)
    }

    @AfterEach
    fun tearDown() {
        player1 = null
        player2 = null
        player3 = null
        player4 = null
        player5 = null
        populatedPlayers = null
        emptyPlayers = null
    }

    @Nested
    inner class AddPlayers {
        @Test
        fun `adding a Player to a populated list adds to ArrayList`() {
            val newPlayer = Players("Carmen ", "Rosalia", 12, 1.50, 50.2, "infield", false)
            assertEquals(5, populatedPlayers!!.numberOfPlayers())
            assertTrue(populatedPlayers!!.add(newPlayer))
            assertEquals(6, populatedPlayers!!.numberOfPlayers())
            assertEquals(newPlayer, populatedPlayers!!.findPlayer(populatedPlayers!!.numberOfPlayers() - 1))
        }

        @Test
        fun `adding a Player to an empty list adds to ArrayList`() {
            val newPlayer = Players("Enrique ", "Galan", 12, 1.70, 12.2, "infield", false)
            assertEquals(0, emptyPlayers!!.numberOfPlayers())
            assertTrue(emptyPlayers!!.add(newPlayer))
            assertEquals(1, emptyPlayers!!.numberOfPlayers())
            assertEquals(newPlayer, emptyPlayers!!.findPlayer(emptyPlayers!!.numberOfPlayers() - 1))
        }
    }

    @Nested
    inner class UpdatePlayers {
        @Test
        fun `updating a note that does not exist returns false`() {
            assertFalse(
                populatedPlayers!!.updatePlayer(
                    6,
                    Players("Updating player", "Gasco", 12, 1.80, 50.00, "infield", false)
                )
            )
            assertFalse(
                populatedPlayers!!.updatePlayer(
                    -1,
                    Players("Updating player", "Gasco", 12, 1.80, 50.00, "infield", false)
                )
            )
            assertFalse(
                emptyPlayers!!.updatePlayer(
                    0,
                    Players("Updating player", "Gasco", 12, 1.80, 50.00, "infield", false)
                )
            )
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(player5, populatedPlayers!!.findPlayer(4))
            Assertions.assertEquals("Tia", populatedPlayers!!.findPlayer(4)!!.playerName)
            Assertions.assertEquals("outfield", populatedPlayers!!.findPlayer(4)!!.position)
            Assertions.assertEquals("Mercedes", populatedPlayers!!.findPlayer(4)!!.playerSurname)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(
                populatedPlayers!!.updatePlayer(
                    4,
                    Players("Updating player", "Gasco", 12, 1.80, 50.00, "infield", false)
                )
            )
            Assertions.assertEquals("Updating player", populatedPlayers!!.findPlayer(4)!!.playerName)
            Assertions.assertEquals("infield", populatedPlayers!!.findPlayer(4)!!.position)
            Assertions.assertEquals("Gasco", populatedPlayers!!.findPlayer(4)!!.playerSurname)
        }
    }

    @Nested
    inner class DeletePlayers {

        @Test
        fun `deleting a Player that does not exist, returns null`() {
            Assertions.assertNull(emptyPlayers!!.deleteNote(0))
            Assertions.assertNull(populatedPlayers!!.deleteNote(-1))
            Assertions.assertNull(populatedPlayers!!.deleteNote(5))
        }

        @Test
        fun `deleting a  Player exists delete and returns deleted object`() {
            Assertions.assertEquals(5, populatedPlayers!!.numberOfPlayers())
            assertEquals(player5, populatedPlayers!!.deleteNote(4))
            Assertions.assertEquals(4, populatedPlayers!!.numberOfPlayers())
            assertEquals(player1, populatedPlayers!!.deleteNote(0))
            Assertions.assertEquals(3, populatedPlayers!!.numberOfPlayers())
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty players.json file.
            val storingPlayers = PlayersAPI(JSONSerializer(File("notes.json")))
            storingPlayers.store()

            //Loading the empty players.json file into a new object
            val loadedPlayers = PlayersAPI(JSONSerializer(File("notes.json")))
            loadedPlayers.load()

            //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            Assertions.assertEquals(0, storingPlayers.numberOfPlayers())
            Assertions.assertEquals(0, loadedPlayers.numberOfPlayers())
            assertEquals(storingPlayers.numberOfPlayers(), loadedPlayers.numberOfPlayers())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 notes to the notes.json file.
            val storingPlayers = PlayersAPI(JSONSerializer(File("notes.json")))
            storingPlayers.add(player4!!)
            storingPlayers.add(player5!!)
            storingPlayers.add(player2!!)
            storingPlayers.store()

            //Loading notes.json into a different collection
            val loadedPlayers = PlayersAPI(JSONSerializer(File("notes.json")))
            loadedPlayers.load()

            //Comparing the source of the notes (storingPlayers) with the json loaded notes (loadedPlayers)
            Assertions.assertEquals(3, storingPlayers.numberOfPlayers())
            Assertions.assertEquals(3, loadedPlayers.numberOfPlayers())
            assertEquals(storingPlayers.numberOfPlayers(), loadedPlayers.numberOfPlayers())
            assertEquals(storingPlayers.findPlayer(0), loadedPlayers.findPlayer(0))
            assertEquals(storingPlayers.findPlayer(1), loadedPlayers.findPlayer(1))
            assertEquals(storingPlayers.findPlayer(2), loadedPlayers.findPlayer(2))
        }
    }

    @Nested
    inner class ListPlayers {

        @Test
        fun `listAllPlayers returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyPlayers!!.numberOfPlayers())
            assertFalse(emptyPlayers!!.listPlayers().lowercase().contains("no players"))
        }

        @Test
        fun `listAllPlayers returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedPlayers!!.numberOfPlayers())
            val notesString = populatedPlayers!!.listPlayers().lowercase()
            assertFalse(notesString.contains("Jorge"))
            assertFalse(notesString.contains("Dani"))
            assertFalse(notesString.contains("Patty"))
            assertFalse(notesString.contains("Diego"))
            assertFalse(notesString.contains("Tia"))
        }
    }
}




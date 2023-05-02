package controller


import controllers.PlayersAPI
import models.Players
import org.junit.jupiter.api.*
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
    private var populatedNotes: PlayersAPI? = PlayersAPI(JSONSerializer(File("notes.json")))
    private var emptyNotes: PlayersAPI? = PlayersAPI(JSONSerializer(File("notes.json")))

    @BeforeEach
    fun setup() {
        player1 = Players("Jorge", "Gasco", 12, 1.50, 50.2, "infield", false)
        player2 = Players("Dani", "Dominguez", 12, 1.60, 60.2, "outfield", false)
        player3 = Players("Patty", "Castillo", 12, 1.70, 70.2, "pitcher", true)
        player4 = Players("Diego", "Pabon", 12, 1.80, 80.2, "infield", false)
        player5 = Players("Tia", "Mercedes", 12, 1.90, 90.2, "outfield", true)

        //adding 5 Note to the notes api
        populatedNotes!!.add(player1!!)
        populatedNotes!!.add(player2!!)
        populatedNotes!!.add(player3!!)
        populatedNotes!!.add(player4!!)
        populatedNotes!!.add(player5!!)
    }

    @AfterEach
    fun tearDown() {
        player1 = null
        player2 = null
        player3 = null
        player4 = null
        player5 = null
        populatedNotes = null
        emptyNotes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Player to a populated list adds to ArrayList`() {
            val newPlayer = Players("Carmen ", "Rosalia", 12, 1.50, 50.2, "infield", false)
            assertEquals(5, populatedNotes!!.numberOfPlayers())
            assertTrue(populatedNotes!!.add(newPlayer))
            assertEquals(6, populatedNotes!!.numberOfPlayers())
            assertEquals(newPlayer, populatedNotes!!.findPlayer(populatedNotes!!.numberOfPlayers() - 1))
        }

        @Test
        fun `adding a Player to an empty list adds to ArrayList`() {
            val newNote = Players("Enrique ", "Galan", 12, 1.70, 12.2, "infield", false)
            assertEquals(0, emptyNotes!!.numberOfPlayers())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfPlayers())
            assertEquals(newNote, emptyNotes!!.findPlayer(emptyNotes!!.numberOfPlayers() - 1))
        }
    }
}


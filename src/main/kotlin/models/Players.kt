package models

data class Players(

    var playerName: String,
    var playerSurname: String,
    var age: Int,
    var height: Double,
    var weight: Double,
    var position: String,
    var isNoteArchived: Boolean)
{}

package models

data class Stats(var statsId: Int = 0,var hits: Int = 0, var baseOnBall: Int = 0, var strikeouts: Int = 0) {
    override fun toString() =
        "Hits: $hits, Base on Ball: $baseOnBall, Strikeouts: $strikeouts"
}
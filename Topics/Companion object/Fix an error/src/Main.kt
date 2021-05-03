data class Player(val id: Int, val name: String) {
    companion object {
        var role = "playable character"
    }

    fun getInfo(): String = "$id, $name, $role"
}

fun getPlayerInfo(player: Player) = player.getInfo()

/*
fun main() {
    val player1 = Player(1, "Karlo")
    Player.role = "role 1"

    val player2 = Player(2, "Petar")
    Player.role = "role 2"

    val player3 = Player(3, "Anton")
    Player.role = "role 3"

    println(getPlayerInfo(player1))
    println(getPlayerInfo(player2))
    println(getPlayerInfo(player3))
}
---------------
1, Karlo, role 3
2, Petar, role 3
3, Anton, role 3
*/

import Node.NPC.DropTables
import RoomSystem.RoomManager
import java.util.*

fun main() {
    DefinitionParser().parseItems()
    DefinitionParser().parseObjects()
    DefinitionParser().parseNPCs()
    DropTables.map()
    RoomManager.init("data/rooms.json")
    gameloop()
}

fun gameloop(){
    val scanner = Scanner(System.`in`)
    GameConstants.login.open()
}
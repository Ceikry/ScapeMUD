import Entity.Item.Item
import RoomSystem.Room
import RoomSystem.RoomManager
import Entity.Player
import gui.MainWindow
import java.util.*

fun main() {
    DefinitionParser().parseItems()
    RoomManager.init("data/rooms.json")
   gameloop()
}

fun gameloop(){
    val scanner = Scanner(System.`in`)
    GameConstants.login.open()
}
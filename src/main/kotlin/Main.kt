import Entity.Item
import RoomSystem.Room
import RoomSystem.RoomManager
import Entity.Player
import java.util.*

fun main() {
    DefinitionParser().parseItems()

    println("Welcome to ScapeSUD! My lazy attempt at a Runescape-like")
    println("MUD, but singleplayer.")
   gameloop()
}

fun gameloop(){
    val player = Player()
    val scanner = Scanner(System.`in`)
    val testRoom = Room()
    testRoom.entryText = "Welcome to the testing room!"
    testRoom.title = "Test Room"
    testRoom.items.add(Item(1,1))
    RoomManager.rooms.put(1,testRoom)
    player.enterRoom(1)
    while(true){
        print(">")
        val userInput = scanner.nextLine()
        InputInterpreter.handle(userInput, player)
    }
}
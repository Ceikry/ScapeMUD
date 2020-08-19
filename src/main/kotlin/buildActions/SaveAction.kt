package buildActions

import Node.Player
import RoomSystem.RoomManager
import actions.Action

class SaveAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        RoomManager.saveRooms("data")
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: save")
        player.addLine("Saves all rooms to the rooms file.")
    }
}
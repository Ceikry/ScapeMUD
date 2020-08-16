package buildActions

import Entity.Player
import RoomSystem.RoomManager
import actions.Action

class SaveAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        RoomManager.saveRooms("data")
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: save")
        GameConstants.addLine("Saves all rooms to the rooms file.")
    }
}
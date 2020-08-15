package buildActions

import Entity.Player
import RoomSystem.RoomManager
import actions.Action

class SaveAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        RoomManager.saveRooms("data")
    }
}
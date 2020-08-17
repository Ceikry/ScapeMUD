package actions

import Node.Player
import RoomSystem.RoomManager

class LookAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size == 2) {
            if (GameConstants.validDirs.any() { it == tokens[1].toLowerCase() }){
                val exits = player.currentRoom!!.exits
                var roomID = when(tokens[1].toLowerCase()){
                    "n" -> exits.north
                    "e" -> exits.east
                    "s" -> exits.south
                    "w" -> exits.west
                    "ne" -> exits.north_east
                    "nw" -> exits.north_west
                    "se" -> exits.south_east
                    "sw" -> exits.south_west
                    "up" -> exits.up
                    "down" -> exits.down
                    else -> -1
                }
                val room = RoomManager.getRoom(roomID)
                room ?: GameConstants.addLine("You see nothing of interest there.")
                room?.onEntry()
            } else {
                player.currentRoom?.look()
            }
        } else {
            player.currentRoom?.look()
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: look or look direction")
        GameConstants.addLine("look by itself prints the details about the current room again.")
        GameConstants.addLine("look direction prints the contents of the room in that direction.")
    }
}
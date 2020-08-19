package actions

import Node.Player
import RoomSystem.RoomManager

class MovementAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        val rid = when(tokens[0]){
            "n" -> player.currentRoom?.exits?.north!!
            "e" -> player.currentRoom?.exits?.east!!
            "s" -> player.currentRoom?.exits?.south!!
            "w" -> player.currentRoom?.exits?.west!!
            "ne" -> player.currentRoom?.exits?.north_east!!
            "nw" -> player.currentRoom?.exits?.north_west!!
            "se" -> player.currentRoom?.exits?.south_east!!
            "sw" -> player.currentRoom?.exits?.south_west!!
            "up" -> player.currentRoom?.exits?.up!!
            "down" -> player.currentRoom?.exits?.down!!
            else -> 0
        }
        val r = RoomManager.getRoom(rid)
        if(r == null){
            System.out.println("Room id = [$rid] token 0 = [${tokens.get(0)}]")
            player.addLine("You can't go that way.").also { return }
        } else {
            player.enterRoom(r.id)
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("E")
    }
}
package buildActions

import Entity.Player
import RoomSystem.Room
import RoomSystem.RoomManager
import actions.Action

class AddAction : Action(){
    val validDirs = arrayOf("n","e","s","w","ne","nw","se","sw","u","d",".")

    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            GameConstants.addLine("add where?").also { return }
        val direction = tokens[1].toLowerCase()
        if(!validDirs.any{ it == direction }){
            GameConstants.addLine("Not a valid direction.").also { return }
        }

        val r = Room()

        if(player.currentRoom == null && direction != "."){
            GameConstants.addLine("You need to add the starting room first!").also { return }
        }

        RoomManager.rooms.put(r.id,r)
        when(direction){
            "n" -> {player.currentRoom?.exits?.north = r.id; r.exits.south = player.currentRoom?.id!!}
            "e" -> {player.currentRoom?.exits?.east = r.id; r.exits.west = player.currentRoom?.id!!}
            "s" -> {player.currentRoom?.exits?.south = r.id; r.exits.north = player.currentRoom?.id!!}
            "w" -> {player.currentRoom?.exits?.west = r.id; r.exits.east = player.currentRoom?.id!!}
            "ne" -> {player.currentRoom?.exits?.north_east = r.id; r.exits.south_west = player.currentRoom?.id!!}
            "nw" -> {player.currentRoom?.exits?.north_west = r.id; r.exits.south_east = player.currentRoom?.id!!}
            "se" -> {player.currentRoom?.exits?.south_east = r.id; r.exits.north_west = player.currentRoom?.id!!}
            "sw" -> {player.currentRoom?.exits?.south_west = r.id; r.exits.north_east = player.currentRoom?.id!!}
            "u" -> {player.currentRoom?.exits?.up = r.id; r.exits.down = player.currentRoom?.id!!}
            "d" -> {player.currentRoom?.exits?.down = r.id; r.exits.up = player.currentRoom?.id!!}
            "." -> {player.enterRoom(r.id)}
        }
    }
}
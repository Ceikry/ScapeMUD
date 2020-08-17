package buildActions

import Node.Item.Item
import Node.NPC.NPC
import Node.Player
import actions.Action

class AddNPCAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            GameConstants.addLine("Add what NPC?")
        if(player.currentRoom != null){
            val id: Int? = tokens[1].toIntOrNull() ?: (-1).also{GameConstants.addLine("Arguments need to be valid integers!"); return}
            if(NPC(id!!).definition == null){
                GameConstants.addLine("Invalid NPC id!")
                return
            }

            player.currentRoom!!.npcs.add(NPC(id))
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: addnpc id")
        GameConstants.addLine("Adds npc with given id to the current room.")
    }
}
package buildActions

import Node.Object.Object
import Node.Player
import actions.Action

class AddObjectAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            GameConstants.addLine("Add what?").also { return }
        if(player.currentRoom != null){
            val id: Int? = tokens[1].toIntOrNull() ?: (-1).also{GameConstants.addLine("Arguments need to be valid integers!"); return}
            if(Object(id!!).definition == null){
                GameConstants.addLine("Invalid item id!")
                return
            }

            player.currentRoom!!.objects.add(Object(id))
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: addobj id")
        GameConstants.addLine("Adds and object with the given id to the current room.")
    }
}
package buildActions

import Node.Object.Object
import Node.Player
import actions.Action

class AddObjectAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            player.addLine("Add what?").also { return }
        if(player.currentRoom != null){
            val id: Int? = tokens[1].toIntOrNull() ?: (-1).also{player.addLine("Arguments need to be valid integers!"); return}
            if(Object(id!!).definition == null){
                player.addLine("Invalid item id!")
                return
            }

            player.currentRoom!!.objects.add(Object(id))
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: addobj id")
        player.addLine("Adds and object with the given id to the current room.")
    }
}
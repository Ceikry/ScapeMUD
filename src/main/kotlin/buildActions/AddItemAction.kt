package buildActions

import Node.Item.Item
import Node.Player
import actions.Action

class AddItemAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 3)
            player.addLine("Add what and how many?").also { return }
        if(player.currentRoom != null){
            val id: Int? = tokens[1].toIntOrNull() ?: (-1).also{player.addLine("Arguments need to be valid integers!"); return}
            val amount: Int? = tokens[2].toIntOrNull() ?: (-1).also{player.addLine("Arguments need to be valid integers!"); return}
            if(Item(id!!,1).definition == null){
                player.addLine("Invalid item id!")
                return
            }

            player.currentRoom!!.items.addItem(Item(id,amount!!))
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: additem id amount")
        player.addLine("Adds an item with given id and amount to the current room.")
    }
}
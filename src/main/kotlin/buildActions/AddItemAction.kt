package buildActions

import Entity.Item.Item
import Entity.Player
import actions.Action

class AddItemAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 3)
            GameConstants.addLine("Add what and how many?").also { return }
        if(player.currentRoom != null){
            val id: Int? = tokens[1].toIntOrNull() ?: (-1).also{GameConstants.addLine("Arguments need to be valid integers!"); return}
            val amount: Int? = tokens[2].toIntOrNull() ?: (-1).also{GameConstants.addLine("Arguments need to be valid integers!"); return}
            if(Item(id!!,1).definition == null){
                GameConstants.addLine("Invalid item id!")
                return
            }

            player.currentRoom!!.items.add(Item(id,amount!!))
        }
    }
}
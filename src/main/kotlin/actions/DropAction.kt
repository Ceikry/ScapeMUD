package actions

import Node.Player

class DropAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            player.addLine("Drop what?")
            return
        }
        if(player.hasItem(tokens[1])){
            val item = player.cachedItem
            item ?: return
            player.inventory.withdrawItem(player.currentRoom!!.items,item)
            player.addLine("You drop ${item.definition?.name} on the floor.")
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: drop item_name")
        player.addLine("Drop allows you to drop items in your inventory")
        player.addLine("onto the ground.")
    }
}
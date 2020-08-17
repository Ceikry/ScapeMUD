package actions

import Node.Player

class DropAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            GameConstants.textQueue += System.lineSeparator() + "Drop what?"
            return
        }
        if(player.hasItem(tokens[1])){
            val item = player.cachedItem
            item ?: return
            player.inventory.withdrawItem(player.currentRoom!!.items,item)
            GameConstants.textQueue += System.lineSeparator() + "You drop ${item.definition?.name} on the floor."
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: drop item_name")
        GameConstants.addLine("Drop allows you to drop items in your inventory")
        GameConstants.addLine("onto the ground.")
    }
}
package actions

import Entity.Player

class DropAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            println("Drop what?").also { return }

        if(player.hasItem(tokens[1])){
            val item = player.cachedItem
            item ?: return
            player.inventory.remove(item)
            player.currentRoom?.items?.add(item)
            println("You drop ${item.definition?.name} on the floor.")
        }
    }
}
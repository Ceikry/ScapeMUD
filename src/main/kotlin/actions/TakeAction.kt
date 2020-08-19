package actions

import Node.Container
import Node.Item.Item
import Node.Object.Object
import Node.Player

class TakeAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            player.addLine("Take what?")
            return
        }
        if(tokens.size == 3){
            val containerNode: Any? = if(player.hasItem(tokens[2])){
                player.cachedItem
            } else if(player.currentRoom!!.hasObject(tokens[2])) {
                player.currentRoom!!.cachedObject
            } else null
            containerNode ?: player.addLine("You don't see a ${tokens[2]} here.").also { return }
            var nodeName = ""
            val container: Container? = if(containerNode is Item && containerNode.container != null){
                containerNode.container.also { nodeName = containerNode.definition?.name!! }
            } else if (containerNode is Object && containerNode.container != null){
                containerNode.container.also { nodeName = containerNode.definition?.name!! }
            } else null

            container ?: player.addLine("That can't store items.").also { return }
            if(container?.hasItem(tokens[1]) == true){
                if(!container.withdrawItem(player.inventory,container.cachedItem!!)){
                    player.addLine("You don't have space for that.")
                } else {
                    player.addLine("You take ${container.cachedItem?.definition?.name} from $nodeName")
                }
            }
            return
        }
        if(player.currentRoom?.hasItem(tokens[1]) == true){
            val item = player.currentRoom!!.getItem(tokens[1])
            player.addLine("You take ${item.definition?.name} and place it in your bag.")
            player.currentRoom!!.items.withdrawItem(player.inventory,item)
            return
        }
        player.addLine("You see no such item.")
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: take item_name or take item_name container_name")
        player.addLine("Take allows you to take an item from the ground or from")
        player.addLine("a container and place it in your inventory.")
    }
}
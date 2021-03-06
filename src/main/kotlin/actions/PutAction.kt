package actions

import Node.Container
import Node.Item.Item
import Node.Object.Object
import Node.Player

class PutAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 3)
            player.addLine("Put what in what?").also { return }
        if(player.hasItem(tokens[1])){
            val depositItem = player.cachedItem!!
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
            if(depositItem.container != null){
                player.addLine("You can't put a container in a container, silly.").also { return }
            }
            if(container?.depositItem(player.inventory,depositItem) != true){
                player.addLine("There isn't enough space in $nodeName for that.")
            } else {
                player.addLine("You put ${if(depositItem.amount > 1) "${depositItem.amount} " else "" }${depositItem.getName()} into $nodeName")
            }
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: put item_name container_name")
        player.addLine("Put allows you to put items from your inventory")
        player.addLine("into a container.")
    }
}
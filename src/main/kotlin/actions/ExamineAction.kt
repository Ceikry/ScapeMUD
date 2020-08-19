package actions

import Node.Container
import Node.Item.Item
import Node.Object.Object
import Node.Player

class ExamineAction : Action() {
    var player: Player = Player()
    override fun handle(player: Player, tokens: Array<String>) {
        this.player = player
        if(tokens.size < 2) {
            player.addLine("Examine what?")
            return
        }
        if(player.hasItem(tokens[1])) {
            val item = player.cachedItem
            examine(item)
            return
        }

        if(player.currentRoom?.hasItem(tokens[1]) == true){
            val item = player.currentRoom!!.cachedItem
            examine(item)
            return
        }

        if(player.currentRoom?.hasObject(tokens[1]) == true){
            val obj = player.currentRoom!!.cachedObject
            examine(obj)
            return
        }
    }

    fun examine(obj: Object?){
        player.addLine(if(obj?.harvested!!) obj.definition?.emptyDesc!! else obj.definition?.desc!!)
        if(obj.container != null){
            player.addLine("${obj.definition.name} has used ${obj.container!!.getUsedSlots()} out of ${obj.container!!.getSize()} spaces and contains:")
            printInventory(obj.container!!)
        }
    }

    fun examine(item: Item?){
        player.addLine("You take a closer look at ${item?.definition?.name}")
        player.addLine("It's " + item?.definition?.desc)
        player.addLine("It's worth " + item?.definition?.value)

        if(item?.definition?.wieldable == true){
            printWieldableStats(item)
        }
        if(item?.container != null){
            player.addLine(item.definition?.name!! + " has used ${item.container!!.getUsedSlots()} out of ${item.container!!.getSize()} spaces and contains:")
            printInventory(item.container!!)
        }
    }

    fun printInventory(con: Container){
        for(item in con.getItems()){
            player.addLine("${item.getName()} ${if(item.amount > 1) "(${item.amount})" else ""}")
        }
    }

    fun printWieldableStats(item: Item){
        for(i in item.definition?.stats!!.indices){
            val bonus = item.definition.stats[i]
            if(bonus == 0) continue
            val sb = StringBuilder()
            sb.append(item.definition.name + " has ")
            sb.append(when(i){
                0 -> "a melee attack bonus of "
                1 -> "a range attack bonus of "
                2 -> "a mage attack bonus of "
                3 -> "a melee defence bonus of "
                4 -> "a range defence bonus of "
                5 -> "a mage defence bonus of "
                6 -> "a melee damage bonus of "
                7 -> "a range damage bonus of "
                8 -> "a mage damage bonus of "
                else -> "Index out of bounds."
            })
            sb.append(bonus)
            player.addLine(sb.toString())
        }
    }

    override fun printHelp(player: Player) {
        player.addLine("Usage: examine name")
        player.addLine("Examine allows you to see more detailed information")
        player.addLine("about just about anything. Objects, items, NPCs, etc.")
        player.addLine("Examine also shows the inventory of containers.")
    }
}
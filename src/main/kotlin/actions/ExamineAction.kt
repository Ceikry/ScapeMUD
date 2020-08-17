package actions

import Node.Container
import Node.Item.Item
import Node.Object.Object
import Node.Player

class ExamineAction : Action() {
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2) {
            GameConstants.textQueue += System.lineSeparator() + "Examine what?"
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
        GameConstants.addLine(if(obj?.harvested!!) obj.definition?.emptyDesc!! else obj.definition?.desc!!)
        if(obj.container != null){
            GameConstants.addLine("${obj.definition.name} has used ${obj.container!!.getUsedSlots()} out of ${obj.container!!.getSize()} spaces and contains:")
            printInventory(obj.container!!)
        }
    }

    fun examine(item: Item?){
        GameConstants.textQueue += System.lineSeparator() + "You take a closer look at ${item?.definition?.name}"
        GameConstants.textQueue += System.lineSeparator() + "It's " + item?.definition?.desc
        GameConstants.textQueue += System.lineSeparator() + "It's worth " + item?.definition?.value

        if(item?.definition?.wieldable == true){
            printWieldableStats(item)
        }
        if(item?.container != null){
            GameConstants.addLine(item.definition?.name!! + " has used ${item.container!!.getUsedSlots()} out of ${item.container!!.getSize()} spaces and contains:")
            printInventory(item.container!!)
        }
    }

    fun printInventory(con: Container){
        for(item in con.getItems()){
            GameConstants.addLine("${item.getName()} ${if(item.amount > 1) "(${item.amount})" else ""}")
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
            GameConstants.textQueue += System.lineSeparator() + sb.toString()
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: examine name")
        GameConstants.addLine("Examine allows you to see more detailed information")
        GameConstants.addLine("about just about anything. Objects, items, NPCs, etc.")
        GameConstants.addLine("Examine also shows the inventory of containers.")
    }
}
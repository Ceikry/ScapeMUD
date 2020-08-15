package actions

import Entity.Item.Item
import Entity.Player

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


    }

    fun examine(item: Item?){
        GameConstants.textQueue += System.lineSeparator() + "You take a closer look at ${item?.definition?.name}"
        GameConstants.textQueue += System.lineSeparator() + "It's " + item?.definition?.desc
        GameConstants.textQueue += System.lineSeparator() + "It's worth " + item?.definition?.value

        if(item?.definition?.wieldable == true){
            printWieldableStats(item)
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
}
package RoomSystem

import Entity.Item.Item

/**
 * Represents a room.
 * @author Ceikry
 */
class Room {
    var entryText: String = ""
    var title: String = ""
    val exits = ExitList()
    val items= ArrayList<Item>()
    var cachedItem: Item? = null
    var id: Int = RoomManager.getNextOpen()

    fun onEntry(){
        drawCardinals()
        GameConstants.textQueue += System.lineSeparator() + ">> " + title + "<<"
        GameConstants.textQueue += System.lineSeparator() + entryText
        if(items.isNotEmpty()) {
            GameConstants.textQueue += System.lineSeparator() + "--------"
            printItems()
        }
    }

    fun printItems(){
        if(items.isNotEmpty()){
            for(item in items){
                GameConstants.textQueue += System.lineSeparator() + "On the ground you see ${item.getName()}" + if(item.amount > 1) "(${item.amount})" else ""
            }
        }
    }

    fun drawCardinals(){
        var sb = StringBuilder()
        if(exits.hasNW()) sb.append("NW") else sb.append("/-")
        sb.append("--")
        if(exits.hasNorth()) sb.append("N") else sb.append("-")
        sb.append("--")
        if(exits.hasNE()) sb.append("NE") else sb.append("-\\")

        val topLine = sb.toString()
        sb = StringBuilder()

        if(exits.hasWest()) sb.append("W") else sb.append("-")
        sb.append("-")
        if(exits.hasUp()) sb.append("U") else sb.append("-")
        sb.append("-@-")
        if(exits.hasDown()) sb.append("D") else sb.append("-")
        sb.append("-")
        if(exits.hasEast()) sb.append("E") else sb.append("-")

        val midLine = sb.toString()
        sb = StringBuilder()

        if(exits.hasSW()) sb.append("SW") else sb.append("\\-")
        sb.append("--")
        if(exits.hasSouth()) sb.append("S") else sb.append("-")
        sb.append("--")
        if(exits.hasSE()) sb.append("SE") else sb.append("-/")

        val bottomLine = sb.toString()

        GameConstants.textQueue += System.lineSeparator() + topLine
        GameConstants.textQueue += System.lineSeparator() + midLine
        GameConstants.textQueue += System.lineSeparator() + bottomLine
    }

    fun look(){
        onEntry()
    }

    fun hasItem(name: String): Boolean{
        for(item in items){
            if(item.definition?.name?.contains(name) == true){
                cachedItem = item
                return true
            }
        }
        return false
    }

    fun getItem(name: String): Item {
        if(cachedItem != null)
            if(cachedItem!!.definition?.name?.contains(name) == true){
                return cachedItem!!
            }
        for(item in items){
            if(item.definition?.name?.contains(name) == true){
                return item
            }
        }
        return Item(-1, -1)
    }
}
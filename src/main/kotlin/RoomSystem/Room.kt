package RoomSystem

import Entity.Item

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

    fun onEntry(){
        drawCardinals()
        println(title)
        println(entryText)
        printItems()
    }

    fun printItems(){
        if(items.isNotEmpty()){
            for(item in items){
                println("On the ground you see ${item.definition?.name}")
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

        println(topLine)
        println(midLine)
        println(bottomLine)
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

    fun getItem(name: String): Item{
        if(cachedItem != null)
            if(cachedItem!!.definition?.name?.contains(name) == true){
                return cachedItem!!
            }
        for(item in items){
            if(item.definition?.name?.contains(name) == true){
                return item
            }
        }
        return Item(-1,-1)
    }
}
package Entity

import RoomSystem.Room
import RoomSystem.RoomManager

class Player {
    var currentRoom: Room? = null
    val inventory = ArrayList<Item>()
    var cachedItem: Item? = null

    fun enterRoom(roomId: Int){
        currentRoom = RoomManager.getRoom(roomId)
        currentRoom?.onEntry()
    }

    fun printInventory(){
        if(inventory.size > 0)
            println("Your inventory contains the following items:")
        else
            println("Your inventory is empty.")

        for(item in inventory){
            println("${item.definition?.name} " + if(item.amount > 1) "(${item.amount})" else "")
        }
    }

    fun hasItem(item: String): Boolean{
        for(it in inventory){
            if(it.definition?.name?.contains(item) == true){
                cachedItem = it
                return true
            }
        }
        return false
    }
}
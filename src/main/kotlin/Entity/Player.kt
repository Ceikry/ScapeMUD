package Entity

import Entity.Item.Item
import RoomSystem.Room
import RoomSystem.RoomManager

class Player {
    var currentRoom: Room? = null
    val inventory = ArrayList<Item>()
    val equipment = Array<Item>(8){Item(-1,0)}
    val stats = Array<Int>(9){0}
    var cachedItem: Item? = null
    var locked = false
    var skills: Skills = Skills()

    fun init() {
        if(currentRoom == null && RoomManager.rooms.isNotEmpty()){
            enterRoom(1)
        }
    }

    fun enterRoom(roomId: Int){
        currentRoom = RoomManager.getRoom(roomId)
        currentRoom?.onEntry()
    }

    fun equip(item: String){
        if(!hasItem(item)){
            GameConstants.textQueue += System.lineSeparator() + "You can't wear an item you don't have."
        } else {
            val equipItem = cachedItem ?: Item(-1,0)
            if (equipItem.definition?.wieldable == true) {
                if (equipment[equipItem.definition.wieldSlot!!].id == -1) {
                    equipment[equipItem.definition.wieldSlot!!] = equipItem
                    for(i in equipItem.definition.stats.indices){
                        val bonus = equipItem.definition.stats[i]
                        stats[i] += bonus
                    }
                    inventory.remove(equipItem)
                    GameConstants.textQueue += System.lineSeparator() + equipItem.definition.equipMsg
                }
            } else {
                GameConstants.textQueue += System.lineSeparator() + "You can't wear that item."
            }
        }
    }

    fun printEquipment(){
        for(i in equipment.indices){
            val sb = StringBuilder()
            when(i){
                0 -> sb.append("<used as weapon>   ")
                1 -> sb.append("<used as shield>   ")
                2 -> sb.append("<worn on head>     ")
                3 -> sb.append("<worn around neck> ")
                4 -> sb.append("<worn on torso>    ")
                5 -> sb.append("<worn on legs>     ")
                6 -> sb.append("<worn on feet>     ")
                7 -> sb.append("<used as ammo>     ")
            }
            if(equipment[i].id != -1){
                sb.append(equipment[i].getName())
            } else {
                sb.append("nothing")
            }
            GameConstants.textQueue += System.lineSeparator() + sb.toString()
        }
    }

    fun printStats(){
        for(i in stats.indices){
            val sb = StringBuilder()
            when(i){
                0 -> sb.append("Melee ATK Bonus: ")
                1 -> sb.append("Range ATK Bonus: ")
                2 -> sb.append("Mage  ATK Bonus: ")
                3 -> sb.append("Melee DEF Bonus: ")
                4 -> sb.append("Range DEF Bonus: ")
                5 -> sb.append("Mage  DEF Bonus: ")
                6 -> sb.append("Melee DMG Bonus: ")
                7 -> sb.append("Range DMG Bonus: ")
                8 -> sb.append("Mage  DMG Bonus: ")
            }
            sb.append(stats[i])
            GameConstants.textQueue += System.lineSeparator() + sb.toString()
            if((i + 1) % 3 == 0){
                GameConstants.textQueue += System.lineSeparator() + "-------------------"
            }
        }
    }

    fun printInventory(){
        if(inventory.size > 0)
            GameConstants.textQueue += System.lineSeparator() + "Your inventory contains the following items:"
        else
            GameConstants.textQueue += System.lineSeparator() + "Your inventory is empty."

        for(item in inventory){
            GameConstants.textQueue += System.lineSeparator() + "${item.definition?.name} " + if(item.amount > 1) "(${item.amount})" else ""
        }
        GameConstants.addLine("You have ${inventory.size} items in your inventory, with space for ${28 - inventory.size} more.")
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

    fun addItem(item: Item): Boolean {
        if(inventory.size >= 28){
            GameConstants.addLine("You do not have space for that.")
            return false
        }
        inventory.add(item)
        return true
    }
}
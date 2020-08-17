package Node.NPC

import Node.AttStyle
import Node.Container
import Node.Item.Item

class NPC(val id: Int) {
    val definition: NPCDefinition? = NPCRepository.forId(id)
    var hp = definition?.hp!!
    val inventory = Container(definition?.containerSize!!)
    val equipment = Array<Item>(8){ Item(-1,0) }
    var style: AttStyle = AttStyle.MELEE
    val stats = Array<Int>(9){0}
    var alive = true

    fun equip(equipItem: Item){
        if (equipItem.definition?.wieldable == true) {
            if (equipment[equipItem.definition.wieldSlot!!].id == -1) {
                equipment[equipItem.definition.wieldSlot!!] = equipItem
                for(i in equipItem.definition.stats.indices){
                    val bonus = equipItem.definition.stats[i]
                    stats[i] += bonus
                }
            }
        }
    }
}
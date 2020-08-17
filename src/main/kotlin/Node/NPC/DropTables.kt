package Node.NPC

import Node.Item.Item
enum class DropTables(val npcID: Int, vararg val drops: Drop) {
    RAT(1,
        Drop(0,5,1)
    );

    companion object {
        val dropMap = hashMapOf<Int,DropTables>()

        @JvmStatic
        fun rollFor(npc: Int): Item? {
            val table = dropMap.get(npc)
            table ?: return null
            val totalWt = table.drops.sumBy { it.weight }
            val drops = table.drops.toMutableList()
            drops.shuffle()
            for (drop in drops) {
                if (totalWt - drop.weight <= 0) {
                    return Item(drop.id, drop.amount)
                }
            }
            return null
        }

        fun map(){
            values().map {
                dropMap.put(it.npcID,it)
            }
        }
    }
}

class Drop(val id: Int, val amount: Int, val weight: Int)
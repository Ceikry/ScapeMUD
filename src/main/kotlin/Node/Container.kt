package Node

import Node.Item.Item
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class Container(private val slots: Int) {
    private var inventory = ArrayList<Item>()
    var cachedItem: Item? = null


    fun save(root: JSONObject){
        val it = JSONArray()
        for(i in inventory){
            val item = JSONObject()
            item.put("id",i.id.toString())
            item.put("amount",i.amount.toString())
            if(i.container != null){
                i.container!!.save(item)
            }
            it.add(item)
        }
        root.put("items",it)
    }

    fun parse(data: JSONArray){
        for(i in data){
            val it = i as JSONObject
            val id = it["id"].toString().toInt()
            val amt = it["amount"].toString().toInt()
            val item = Item(id,amt)

            if(item.definition?.hasContainer == true){
                val its = it["items"] as JSONArray
                for(e in its){
                    val et = e as JSONObject
                    val id2 = et["id"].toString().toInt()
                    val amt2 = et["amount"].toString().toInt()
                    item.container!!.addItem(Item(id2,amt2))
                }
            }
            inventory.add(item)
        }
    }

    fun addItem(item: Item): Boolean{
        if(!isFull()){

            if(item.definition?.stackable == true){
                for(it in inventory){
                    if(it.id == item.id){
                        it.amount += item.amount
                        return true
                    }
                }
            }
            inventory.add(item).also { return true }
        }
        return false
    }

    fun removeItem(item: Item): Boolean{
        if(hasItem(item)){

            if(item.definition?.stackable == true) {
                for (it in inventory) {
                    if (it.id == item.id) {
                        val newItemAmt = item.amount
                        it.amount -= item.amount
                        return true
                    }
                }
            }
            inventory.remove(item).also { return true }
        }
        return false
    }

    fun hasItem(item: Item): Boolean{
        return inventory.contains(item)
    }

    fun hasItem(item: String): Boolean{
        for(it in inventory){
            if(it.definition?.name?.toLowerCase()?.contains(item.toLowerCase()) == true){
                cachedItem = it
                return true
            }
        }
        return false
    }

    fun depositItem(from: Container, item: Item): Boolean{
        if(from.hasItem(item) && !isFull()){
            from.removeItem(item).also { addItem(item) }
            return true
        }
        return false
    }

    fun withdrawItem(to: Container, item: Item): Boolean{
        if(hasItem(item) && !to.isFull()){
            removeItem(item).also { to.addItem(item) }
            return true
        }
        return false
    }

    fun getSize(): Int{
        return slots
    }

    fun isFull(): Boolean{
        return slots == inventory.size
    }

    fun isEmpty(): Boolean {
        return inventory.size == 0
    }

    fun getFreeSlots(): Int {
        return slots - inventory.size
    }

    fun getUsedSlots(): Int{
        return inventory.size
    }

    fun getItems(): List<Item>{
        return inventory
    }
}
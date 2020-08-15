import Entity.Item.ItemDefinition
import Entity.Item.ItemRepository
import Entity.Object.ObjectDefinition
import Entity.Object.ObjectRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

class DefinitionParser {
    val parser = JSONParser()
    var reader: FileReader? = null
    val statKeys = arrayOf("MeleeATKBonus","RangeATKBonus","MageATKBonus","MeleeDEFBonus","RangeDEFBonus","MageDEFBonus","MeleeDMGBonus","RangeDMGBonus","MageDMGBonus")

    fun parseObjects(){
        reader = FileReader("data/Objects.json")
        val objects = parser.parse(reader) as JSONArray
        var itemTotal = 0
        for(it in objects) {
            val obj = it as JSONObject
            val id = obj["id"].toString().toInt()
            val name = obj["name"].toString()
            val desc = obj["description"].toString()
            val emptyDesc = obj["emptyDesc"]
            val harvestable = obj["harvestable"]
            val harvestAmt = obj["harvestAmt"]
            val harvestID = obj["harvestID"]
            val emptyName = obj["emptyName"]

            val newObj = ObjectDefinition()
            newObj.name = name
            newObj.emptyName = (emptyName ?: name).toString()
            newObj.desc = desc
            newObj.emptyDesc = (emptyDesc ?: "").toString()
            newObj.harvestable = (harvestable ?: false) as Boolean
            newObj.harvestAmount = (harvestAmt ?: 0).toString().toInt()
            newObj.harvestID = (harvestID ?: -1).toString().toInt()

            ObjectRepository.add(id, newObj)
            itemTotal++
        }
    }

    fun parseItems() {
        reader = FileReader("data/Items.json")
        val items = parser.parse(reader) as JSONArray
        var itemTotal = 0
        for(it in items){
            val item = it as JSONObject
            val id = item["id"].toString().toInt()
            val name = item["name"].toString()
            val desc = item["description"].toString()
            val newItem = ItemDefinition()
            newItem.name = name
            newItem.desc = desc
            newItem.value = item["value"].toString().toInt()

            if(item.containsKey("wieldable")){
                newItem.wieldable = item["wieldable"] as Boolean
            }

            if(item.containsKey("wieldSlot")){
                newItem.wieldSlot = item["wieldSlot"].toString().toInt()
            }

            if(item.containsKey("equipMsg")){
                newItem.equipMsg = item["equipMsg"].toString()
            } else {
                newItem.equipMsg = "You wear $name"
            }

            for(i in statKeys.indices){
                if(item.containsKey(statKeys[i])){
                    newItem.stats[i] = item[statKeys[i]].toString().toInt()
                }
            }

            ItemRepository.add(id,newItem)
            itemTotal++
        }
    }
}
import Entity.Item
import Entity.ItemDefinition
import Entity.ItemRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

class DefinitionParser {
    val parser = JSONParser()
    var reader: FileReader? = null

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
            newItem.value = 100
            ItemRepository.add(id,newItem)
            itemTotal++
        }
        println("Startup: Parsed $itemTotal item definitions.")
    }
}
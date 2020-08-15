package RoomSystem

import Entity.Item.Item
import Entity.Object.Object
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import javax.script.ScriptEngineManager

class RoomManager {
    companion object{
        val rooms = hashMapOf<Int,Room>()

        @JvmStatic
        fun getRoom(id: Int): Room?{
            return rooms.get(id)
        }

        fun getNextOpen(): Int{
            return rooms.keys.size + 1
        }

        fun init(path: String) {
            val parser = JSONParser()
            val reader = FileReader(path)
            if(File(path).exists()){
                val roos = parser.parse(reader) as JSONArray
                for(roo in roos){
                    val room = roo as JSONObject
                    val r = Room()
                    val t = room["roomTitle"] as String
                    val d = room["desc"] as String
                    val id = room["roomID"].toString().toInt()
                    r.exits.north = room["exitN"].toString().toInt()
                    r.exits.south = room["exitS"].toString().toInt()
                    r.exits.east = room["exitE"].toString().toInt()
                    r.exits.west = room["exitW"].toString().toInt()
                    r.exits.north_west = room["exitNW"].toString().toInt()
                    r.exits.north_east = room["exitNE"].toString().toInt()
                    r.exits.south_west = room["exitSW"].toString().toInt()
                    r.exits.south_east = room["exitSE"].toString().toInt()
                    r.exits.up = room ["exitUP"].toString().toInt()
                    r.exits.down = room ["exitDOWN"].toString().toInt()
                    r.id = id
                    r.title = t
                    r.entryText = d
                    val items = room["items"] as JSONArray
                    val objects = room["objects"] as JSONArray
                    for(i in items){
                        i ?: continue
                        val item = i as JSONObject
                        val it = Item(item["id"].toString().toInt(),item["amount"].toString().toInt())
                        r.items.add(it)
                    }
                    for(i in objects){
                        i ?: continue
                        val obj = i as JSONObject
                        val it = Object(obj["id"].toString().toInt())
                        it.harvested = obj["harvested"] as Boolean
                        r.objects.add(it)
                    }
                    rooms.put(id,r)
                }
            }
        }

        fun saveRooms(path: String){
            val root = JSONArray()
            for(entry in rooms){
                val ro = JSONObject()
                val room = entry.value
                val exits = room.exits
                ro.put("roomID",entry.key.toString())
                ro.put("roomTitle",room.title)
                ro.put("desc",room.entryText)
                ro.put("exitN",exits.north.toString())
                ro.put("exitE",exits.east.toString())
                ro.put("exitS",exits.south.toString())
                ro.put("exitW",exits.west.toString())
                ro.put("exitNW",exits.north_west.toString())
                ro.put("exitNE",exits.north_east.toString())
                ro.put("exitSE",exits.south_east.toString())
                ro.put("exitSW",exits.south_west.toString())
                ro.put("exitUP",exits.up.toString())
                ro.put("exitDOWN",exits.down.toString())
                val items = JSONArray()
                val objects = JSONArray()
                for(i in room.items){
                    val it = JSONObject()
                    it.put("id",i.id.toString())
                    it.put("amount",i.amount.toString())
                    items.add(it)
                }
                for(i in room.objects){
                    val it = JSONObject()
                    it.put("id",i.id)
                    it.put("harvested",i.harvested)
                    objects.add(it)
                }
                ro.put("objects",objects)
                ro.put("items",items)
                root.add(ro)
            }

            val manager = ScriptEngineManager()
            val scriptEngine = manager.getEngineByName("JavaScript")
            scriptEngine.put("jsonString", root.toJSONString())
            scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, 2)")
            val prettyPrintedJson = scriptEngine["result"] as String

            try {
                FileWriter("$path/rooms.json").use { file ->
                    file.write(prettyPrintedJson)
                    file.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }



    }
}
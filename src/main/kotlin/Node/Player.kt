package Node

import LoginHandler
import Node.Item.Item
import RoomSystem.Room
import RoomSystem.RoomManager
import org.apache.mina.core.session.IoSession
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.io.FileWriter
import java.io.IOException
import java.util.*
import javax.script.ScriptEngineManager
import kotlin.collections.ArrayList

class Player {
    var currentRoom: Room? = null
    val inventory = Container(28)
    val equipment = Array(8){Item(-1,0)}
    val stats = Array<Int>(9){0}
    var cachedItem: Item? = null
    var locked = false
    var skills: Skills = Skills(this)
    var hp = 10
    var maxHp = 10
    var style: AttStyle = AttStyle.MELEE
    var messageQueue = ArrayList<String>()
    var session: IoSession? = null
    var name = ""
    var loggedIn = false
    var loginHandler = LoginHandler(this)
    var croomId = (currentRoom ?: RoomManager.getRoom(0))?.id ?: 0
    val commandQueue: Queue<String> = LinkedList<String>()

    fun init() {
        name = loginHandler.username
        enterRoom(croomId)
        addLine("Welcome, $name.")
        loggedIn = true
    }

    fun parse(data: JSONObject){
        croomId = data["currentRoom"].toString().toInt()
        inventory.parse(data["items"] as JSONArray)
        val equ = data["equipment"] as JSONArray
        for(e in equ){
            val it = e as JSONObject
            val id = it["id"].toString().toIntOrNull()
            val amount = it["amount"].toString().toIntOrNull()

            val item = Item(id!!,amount!!)
            if(item.id != -1) {
                equipment[item.definition?.wieldSlot!!] = item
                for(i in item.definition.stats.indices){
                    val bonus = item.definition.stats[i]
                    stats[i] += bonus
                }
            }
        }
        skills.parse(data["skills"] as JSONArray)
    }

    fun save(){
        val root = JSONObject()
        root.put("currentRoom",(currentRoom ?: RoomManager.getRoom(1))?.id.toString())
        root.put("password",loginHandler.password)
        inventory.save(root)
        val equipment = JSONArray()
        for(i: Item in this.equipment){
            val item = JSONObject()
            item.put("id",i.id.toString())
            item.put("amount",i.amount.toString())
            equipment.add(item)
        }
        root.put("equipment",equipment)
        skills.save(root)

        val manager = ScriptEngineManager()
        val scriptEngine = manager.getEngineByName("JavaScript")
        scriptEngine.put("jsonString", root.toJSONString())
        scriptEngine.eval("result = JSON.stringify(JSON.parse(jsonString), null, 2)")
        val prettyPrintedJson = scriptEngine["result"] as String

        try {
            FileWriter(GameConstants.PLAYER_SAVE_PATH + "${loginHandler.username}.json").use { file ->
                file.write(prettyPrintedJson)
                file.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        System.out.println("Player saved.")
    }
    
    fun addLine(message: String){
        messageQueue.add(message)
    }
    
    fun refresh(){
        val command = commandQueue.poll()
        if(command != null)  InputInterpreter.handle(command,this)
        if(messageQueue.isNotEmpty()){
            for(i in messageQueue){
                session?.write(i)
            }
            if(loggedIn)
                session?.write("${ColorCore.build("HP:[$hp/$maxHp]",ColorCore.RED)} ${ColorCore.build("[${skills.mostRecentlyTrained?.name}] XP to level: ${skills.mostRecentlyTrained?.xpToLevel}",ColorCore.GREEN)}")
        }
        messageQueue.clear()
    }

    fun enterRoom(roomId: Int){
        currentRoom = RoomManager.getRoom(roomId)
        currentRoom?.onEntry(this)
        currentRoom?.announce(this)
    }

    fun equip(item: String){
        if(!hasItem(item)){
            addLine("You can't wear an item you don't have.")
        } else {
            val equipItem = cachedItem ?: Item(-1,0)
            if (equipItem.definition?.wieldable == true) {

                for(req in equipItem.definition.requirements){
                    if(skills.skills[req.key].level < req.value){
                        addLine("You need a ${skills.skills[req.key].name} level of ${req.value} to use this.")
                        return
                    }
                }


                if (equipment[equipItem.definition.wieldSlot!!].id == -1) {
                    equipment[equipItem.definition.wieldSlot!!] = equipItem
                    for(i in equipItem.definition.stats.indices){
                        val bonus = equipItem.definition.stats[i]
                        stats[i] += bonus
                    }
                    inventory.removeItem(equipItem)
                    addLine(equipItem.definition.equipMsg!!)
                }
            } else {
                addLine("You can't wear that item.")
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
            addLine(sb.toString())
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
            addLine(sb.toString())
            if((i + 1) % 3 == 0){
                addLine("-------------------")
            }
        }
    }

    fun printInventory(){
        if(!inventory.isEmpty())
            addLine("Your inventory contains the following items:")
        else
            addLine("Your inventory is empty.")

        for(item in inventory.getItems()){
            addLine("${item.definition?.name} " + if(item.amount > 1) "(${item.amount})" else "")
        }
        addLine("You have ${inventory.getUsedSlots()} items in your inventory, with space for ${inventory.getFreeSlots()} more.")
    }

    fun hasItem(item: String): Boolean{
        for(it in inventory.getItems()){
            if(it.definition?.name?.contains(item) == true){
                cachedItem = it
                return true
            }
        }
        return false
    }

    fun addItem(item: Item): Boolean {
        return inventory.addItem(item)
    }
}
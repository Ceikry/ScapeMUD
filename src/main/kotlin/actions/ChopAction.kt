package actions

import ActionPulse.ActionPulse
import ActionPulse.ObjectRespawn
import Entity.Item.Item
import Entity.Player
import Entity.Skills
import gui.MainWindow
import kotlin.random.Random
import kotlin.random.nextInt

class ChopAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2){
            GameConstants.addLine("Chop what?").also { return }
        }

        if(player.currentRoom!!.hasObject(tokens[1].toLowerCase())){
            val obj = player.currentRoom!!.cachedObject
            if(obj?.definition?.name?.toLowerCase()?.contains("tree") == true){
                if(!player.hasItem("axe")){
                    GameConstants.addLine("You need an axe to chop a tree.").also { return }
                }
                if(obj.harvested){
                    GameConstants.addLine("This tree has already been harvested.").also { return }
                }
                if(player.inventory.size >= 28){
                    GameConstants.addLine("You are out of inventory space.")
                    return
                }
                if(obj.amtLeft!! <= 0){
                    obj.harvested = true
                    GameConstants.addLine("The tree has been depleted of wood.")
                    GameConstants.queue(ObjectRespawn(obj))
                    return
                }
                val roll = Random.nextInt(IntRange(1,10)) == 5
                if(roll){
                    player.skills.addXP(Skills.WOODCUTTING,10.0)
                    GameConstants.addLine("You harvest a log from the tree.")
                    GameConstants.addLine(" ${player.skills.skills[Skills.WOODCUTTING].xpToLevel} experience until next ${player.skills.skills[Skills.WOODCUTTING].name} level.")
                    player.inventory.add(Item(obj.definition.harvestID!!,1))
                    obj.amtLeft -= 1
                } else {
                    GameConstants.addLine("You swing your axe at the tree.")
                }
            }
        } else {
            GameConstants.addLine("You see no such thing.")
        }
    }
}
package actions

import ActionPulse.ObjectRespawn
import Node.Item.Item
import Node.Player
import Node.Skills
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
                if(player.skills.skills[Skills.WOODCUTTING].level < obj.definition.levelReq){
                    GameConstants.addLine("You need a woodcutting level of ${obj.definition.levelReq} to cut this tree.").also { return }
                }
                if(obj.harvested){
                    GameConstants.addLine("This tree has already been harvested.").also { return }
                }
                if(player.inventory.isFull()){
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
                    player.addItem(Item(obj.definition.harvestID!!,1))
                    obj.amtLeft -= 1
                } else {
                    GameConstants.addLine("You swing your axe at the tree.")
                }
            }
        } else {
            GameConstants.addLine("You see no such thing.")
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: chop name_of_object")
        GameConstants.addLine("Chop allows you to chop trees and some other very specific things,")
        GameConstants.addLine("provided that you have an axe in your inventory. For most trees, you")
        GameConstants.addLine("also need to have the appropriate woodcutting level for that tree.")
    }
}
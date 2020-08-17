package ActionPulse

import ActionPulse.ActionPulse
import Node.AttStyle
import Node.EqSlot
import Node.Item.Item
import Node.NPC.DropTables
import Node.NPC.NPC
import Node.Player
import Node.Stats
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.random.Random

class CombatActionPulse(val player: Player,val npc: NPC) : ActionPulse(){
    override fun pulse(): Boolean {
        System.out.println("Pulse running")
        val weapon = player.equipment[EqSlot.WEP.ordinal]
        val npcWeapon = npc.equipment[EqSlot.WEP.ordinal]
        var npcAttkSlot = 0
        var playerAttkSlot = 0
        var npcDMGSlot = 0
        var playerDMGSlot = 0
        var npcDEFSlot = 0
        var playerDEFSlot = 0

        if(player.hp <= 0){
            GameConstants.addLine("Oh dead, you are dead!")
            return true
        }

        if(weapon.id == -1){
            player.style = AttStyle.MELEE
        } else {
            player.style = weapon.definition?.attStyle!!
        }
        playerAttkSlot = Stats.attkForStyle(player.style).ordinal
        playerDMGSlot = Stats.dmgForStyle(player.style).ordinal

        if(npcWeapon.id == -1){
            npc.style = AttStyle.MELEE
        } else {
            npc.style = weapon.definition?.attStyle!!
        }
        npcAttkSlot = Stats.attkForStyle(npc.style).ordinal
        npcDMGSlot = Stats.dmgForStyle(npc.style).ordinal

        playerDEFSlot = Stats.defAgainstStyle(npc.style).ordinal
        npcDEFSlot = Stats.defAgainstStyle(player.style).ordinal

        val npcAttackRoll = Random.nextInt(
            ceil(
                sqrt(npc.definition?.level!!.toDouble() + npc.stats[npcAttkSlot])
            +1).toInt())

        val npcDamageRoll = Random.nextInt(
            ceil(
                sqrt(npc.definition.level.toDouble() + npc.stats[npcDMGSlot])
            +1).toInt()
        )

        val npcDefenceRoll = Random.nextInt(
            ceil(
                sqrt(npc.definition.level.toDouble() + npc.stats[npcDEFSlot])
            +1).toInt()
        )

        val playerAttackRoll = Random.nextInt(
            ceil(
                sqrt(player.skills.forStyle(player.style).level.toDouble() + player.stats[playerAttkSlot])
            +1).toInt()
        )

        val playerDamageRoll = Random.nextInt(
            ceil(
                sqrt(player.skills.forStyle(player.style).level.toDouble() + player.stats[playerDMGSlot])
            +1).toInt()
        )

        val playerDefenceRoll = Random.nextInt(
            ceil(
                sqrt(player.skills.forStyle(player.style).level.toDouble() + player.stats[playerDEFSlot])
            +1).toInt()
        )

        if(playerAttackRoll > npcDefenceRoll){
            if(playerDamageRoll > 0)
                GameConstants.addLine("You hit the ${npc.definition.name} for a $playerDamageRoll")
            else
                GameConstants.addLine("Your attack misses the ${npc.definition.name}")

            npc.hp -= playerDamageRoll
        } else {
            GameConstants.addLine("${npc.definition.name} blocked your attack.")
        }

        if(npcAttackRoll > playerDefenceRoll){
            if(npcDamageRoll > 0)
                GameConstants.addLine("${npc.definition.name} hits you for a $npcDamageRoll")
            else
                GameConstants.addLine("${npc.definition.name}'s attack misses you.")
            player.hp -= npcDamageRoll
        } else {
             GameConstants.addLine("You block ${npc.definition.name}'s attack.")
        }

        GameConstants.addLine("Your HP: ${player.hp}/${player.maxHp} || ${npc.definition.name}: ${buildHpBar(npc.hp,npc.definition.hp)}")
        if(npc.hp <= 0){
            npc.alive = false
            GameConstants.addLine(npc.definition?.name!! + " falls over, dead.")
            npc.inventory.addItem(DropTables.rollFor(npc.id) ?: Item(0,1))

            val xpAddAmt = 4 * npc.definition.hp.toDouble()
            val skill = player.skills.forStyle(player.style)

            player.skills.addXP(skill, xpAddAmt)
            GameConstants.addLine("For your victory, you gain $xpAddAmt ${skill.name} XP.")

            player.skills.forStyle(player.style)
            return true
        }

        return false
    }

    fun buildHpBar(health: Int, maxHealth: Int): String{
        val hpPerc = (health.toDouble() / maxHealth.toDouble())
        val bar = StringBuilder()
        bar.append("[")
        bar.append(if(hpPerc > 0.05) "#" else "-")
        bar.append(if(hpPerc >= 0.20) "#" else "-")
        bar.append(if(hpPerc >= 0.40) "#" else "-")
        bar.append(if(hpPerc >= 0.60) "#" else "-")
        bar.append(if(hpPerc >= 0.80) "#" else "-")
        bar.append("]")
        return bar.toString()
    }
}
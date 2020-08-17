package actions

import ActionPulse.CombatActionPulse
import Node.Player

class AttackAction : Action(){
    override fun handle(player: Player, tokens: Array<String>) {
        if(tokens.size < 2)
            GameConstants.addLine("Attack what?").also { return }
        if(player.currentRoom!!.hasNPC(tokens[1])){
            GameConstants.queue(
                CombatActionPulse(
                    player,
                    player.currentRoom!!.cachedNPC!!
                )
            )
            System.out.println("Queued combat action.")
        } else {
            GameConstants.addLine("You see no such thing here.")
        }
    }

    override fun printHelp() {
        GameConstants.addLine("Usage: attack npc_name")
        GameConstants.addLine("Attack allows you to attack certain NPCs.")
        GameConstants.addLine("You will probably die if you attack something much")
        GameConstants.addLine("stronger than you.")
    }
}
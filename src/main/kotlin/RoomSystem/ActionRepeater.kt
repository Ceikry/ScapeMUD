package RoomSystem

import ActionPulse.ActionPulse
import Node.Player
import actions.Action

class ActionRepeater(val action: Action){
    fun repeat(amount: Int, player: Player, tokens: Array<String>){
        player.locked = true
        GameConstants.queue(ActionRepeatPulse(amount,action,player,tokens))
    }

    private class ActionRepeatPulse(var amount: Int, val action: Action, val player: Player, val tokens: Array<String>) : ActionPulse(){
        override fun pulse(): Boolean {
            action.handle(player,tokens)
            val done = amount-- <= 1
            if(done)
                player.locked = false
            return done
        }
    }
}
package ActionPulse

import Entity.Object.Object

class ObjectRespawn(val obj: Object) : ActionPulse(obj.definition?.respawnTime!!){
    override fun pulse(): Boolean {
        obj.harvested = false
        obj.amtLeft = obj.definition?.harvestAmount!!
        return true
    }
}
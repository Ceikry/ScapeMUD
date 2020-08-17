package Node.Object

import Node.Container

class Object(val id: Int) {
    val definition = ObjectRepository.forId(id)
    var amtLeft = definition?.harvestAmount ?: 0
    var harvested = false
    var container: Container? = null

    init {
        if(definition!!.hasContainer){
            container = Container(definition.containerSize)
        }
    }
}
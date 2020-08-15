package Entity.Object

class Object(val id: Int) {
    val definition = ObjectRepository.forId(id)
    var amtLeft = definition?.harvestAmount ?: 0
    var harvested = false
}
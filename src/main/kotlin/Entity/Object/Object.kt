package Entity.Object

class Object(val id: Int) {
    val definition = ObjectRepository.forId(id)
    var harvested = false
}
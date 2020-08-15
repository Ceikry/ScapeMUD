package Entity.Item

class ItemDefinition{
    var name: String? = ""
    var desc: String? = ""
    var value: Int? = 0
    var wieldable: Boolean? = false
    var wieldSlot: Int? = 0
    var equipMsg: String? = ""
    val stats = Array<Int>(9){0}
}
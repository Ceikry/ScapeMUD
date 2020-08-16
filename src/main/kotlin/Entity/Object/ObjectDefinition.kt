package Entity.Object

class ObjectDefinition {
    var name: String = ""
    var emptyName: String = name
    var desc: String? = ""
    var emptyDesc: String? = ""
    var harvestable: Boolean? = false
    var harvestAmount: Int? = 0
    var harvestID: Int? = 0
    var respawnTime: Int = 25
    var levelReq: Int = 1
}
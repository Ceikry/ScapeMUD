package ActionPulse

abstract class ActionPulse(var delay: Int = 0){
    abstract fun pulse(): Boolean
}
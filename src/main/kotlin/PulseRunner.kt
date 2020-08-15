import ActionPulse.ActionPulse
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PulseRunner {
    val executor = Executors.newSingleThreadScheduledExecutor()

    init{
        executor.scheduleAtFixedRate(sequence,100,500, TimeUnit.MILLISECONDS)
    }

    companion object{
        val queue = ArrayList<ActionPulse>()
    }

    fun addToQueue(pulse: ActionPulse){
        queue.add(pulse)
    }

    object sequence : Runnable{
        override fun run() {
            val toRun = queue.toTypedArray()
            for(action in toRun){
                if(action.delay == 0) {
                    if (action.pulse()) {
                        queue.remove(action)
                    }
                } else action.delay -= 1
            }
            GameConstants.gui.refresh()
        }
    }
}
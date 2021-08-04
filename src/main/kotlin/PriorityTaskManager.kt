import java.time.Instant
import java.util.*

class PriorityTaskManager(private val capacity: Int) : TaskManager {

    override val processes = PriorityQueue<Process>()

    /**
     * Adds a process to the tasks manager in form of a [processBuilder]
     * The  oldest process will be killed and removed if capacity is reached
     */
    override fun add(processBuilder: Process.Builder) {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("process not added (name, pid and priority of process must be provided)")
            return
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("process not added (process with pid=${process.pid} already exists)")
            return
        }
        this.processes.add(process)
        if (processes.size > this.capacity) {
            val processToRemove = processes.remove()
            processToRemove?.kill()
        }
    }
}


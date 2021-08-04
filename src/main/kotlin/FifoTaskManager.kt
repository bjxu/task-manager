import java.time.Instant
import java.util.LinkedList

class FifoTaskManager(private val capacity: Int) : TaskManager {

    override val processes = LinkedList<Process>()

    /**
     * Adds a process to the tasks manager in form of a [processBuilder]
     * The oldest process will be killed and removed if capacity is reached
     */
    override fun add(processBuilder: Process.Builder) {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("cannot add (name, pid and priority of process must be provided)")
            return
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("cannot add (process with pid=${process.pid} already exists)")
            return
        }
        if (processes.size >= this.capacity) {
            val processToRemove = processes.remove()
            processToRemove?.kill()
        }
        this.processes.add(process)

    }
}

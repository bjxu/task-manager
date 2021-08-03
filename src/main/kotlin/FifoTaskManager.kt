import java.time.Instant
import java.util.LinkedList

class FifoTaskManager(private val capacity: Int) : TaskManager {
    override val processes = LinkedList<Process>()

    override fun add(processBuilder: Process.Builder) {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("name, pid and priorty of process must be provided")
            return
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("pid exists")
            return
        }
        if (processes.size >= this.capacity) {
            val processToRemove = processes.remove()
            processToRemove?.kill();
        }
        this.processes.add(process)

    }
}

import java.time.Instant

class DefaultTaskManager(private val capacity: Int) : TaskManager {

    override val processes: HashSet<Process> = hashSetOf()

    /**
     * Adds a process to the tasks manager in form of a [processBuilder]
     * Process can't be added if the capacity of the task manager is reached
     */
    override fun add(processBuilder: Process.Builder) : Process? {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("process not added (name, pid and priority of process must be provided)")
            return null
        }
        if (processes.size >= this.capacity) {
            println("process not added (capacity=${this.capacity} reached)")
            return null
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("process not added (process with pid=${process.pid} already exists)")
            return null
        } else {
            this.processes.add(process)
            return process
        }
    }
}

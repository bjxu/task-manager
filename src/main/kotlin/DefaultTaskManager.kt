import java.time.Instant

class DefaultTaskManager(private val capacity: Int) : TaskManager {

    override val processes: HashSet<Process> = hashSetOf()

    /**
     * Adds a process to the tasks manager in form of a [processBuilder]
     * Process can't be added if the capacity of the task manager is reached
     */
    override fun add(processBuilder: Process.Builder) {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("cannot add (name, pid and priority of process must be provided)")
            return
        }
        if (processes.size >= this.capacity) {
            println("cannot add (capacity=${this.capacity} reached)")
            return
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("cannot add (process with pid=${process.pid} already exists)")
        } else {
            this.processes.add(process)
        }
    }
}

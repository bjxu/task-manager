import java.time.Instant

class NormalTaskManager(private val capacity: Int) : TaskManager {
    override val processes:HashSet<Process> = hashSetOf()

    override fun add(processBuilder: Process.Builder) {
        if (processBuilder.name.isNullOrEmpty() || processBuilder.pid == null || processBuilder.priority == null) {
            println("name, pid and priorty of process must be provided")
            return
        }
        if (processes.size >= this.capacity) {
            println("capacity reached")
            return;
        }
        val process = processBuilder.timestamp(Instant.now()).build()
        if (processes.contains(process)) {
            println("pid exists")
        } else this.processes.add(process)
    }
}

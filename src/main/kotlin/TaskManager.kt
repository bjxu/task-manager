import java.util.*

interface TaskManager {
    val processes: MutableCollection<Process>

    /**
     * Adds a process to the tasks manager in form of a [processBuilder]
     */
    fun add(processBuilder: Process.Builder)

    /**
     * Lists all processes in the task manager sorted with the [comparator] for Process
     * @return the sorted list of all the processes
     */
    fun list(comparator: Comparator<Process>): List<Process> {
        return this.processes.sortedWith(comparator).toList()
    }

    /**
     * Kills a process with [pid] in the task manager
     */
    fun kill(pid: Int) {
        var processToKill: Process? = null
        for (process in processes) {
            if (process.pid == pid) {
                processToKill = process
            }
        }
        processToKill?.kill()
        processes.remove(processToKill)
    }

    /**
     * Kills all process with [priority] in the task manager
     */
    fun killGroup(priority: PriorityType) {
        val processesToKill = processes.filter { process -> process.priority == priority }
        for (processToKill in processesToKill) {
            processToKill.kill()
            processes.remove(processToKill)
        }
    }

    /**
     * Kills all process in the task manager
     */
    fun killAll() {
        processes.forEach { process -> process.kill() }
        processes.clear()
    }
}

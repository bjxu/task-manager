import java.util.*

interface TaskManager {
    val processes: MutableCollection<Process>
    fun add(processBuilder: Process.Builder)

    fun list(comparator: Comparator<Process>): List<Process> {
        return this.processes.sortedWith(comparator).toList()
    }

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

    fun killGroup(priority: PriorityType) {
        val processesToKill = processes.filter { process -> process.priority == priority }
        for (processToKill in processesToKill) {
            processToKill.kill()
            processes.remove(processToKill)
        }
    }

    fun killAll() {
        processes.forEach { process -> process.kill() }
        processes.clear()
    }
}

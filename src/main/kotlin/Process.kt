import java.time.Instant

class Process private constructor(
    val name: String,
    val pid: Int,
    val priority: PriorityType,
    val creationTS: Instant
) : Comparable<Process> {

    override fun equals(other: Any?): Boolean {
        if (this.pid == other) return true
        if (javaClass != other?.javaClass) return false
        other as Process
        return this.pid == other.pid
    }

    override fun hashCode(): Int {
        return pid
    }

    override fun compareTo(other: Process): Int {
        return when {
            this.priority > other.priority -> 1
            this.priority < other.priority -> -1
            else -> this.creationTS.compareTo(other.creationTS)
        }
    }

    /**
     * Kills the underlying process
     */
    fun kill() {}

    data class Builder(
        var name: String? = null,
        var pid: Int? = null,
        var priority: PriorityType? = null,
        var creationTS: Instant? = null
    ) {
        fun name(name: String) = apply { this.name = name }
        fun pid(pid: Int) = apply { this.pid = pid }
        fun priority(priority: PriorityType) = apply { this.priority = priority }
        fun timestamp(timestamp: Instant) = apply { this.creationTS = timestamp }
        fun build(): Process {
            val name = this.name ?: ""
            val pid = this.pid ?: 0
            val priority = this.priority ?: PriorityType.LOW
            val timestamp = this.creationTS ?: Instant.MIN
            return Process(name, pid, priority, timestamp)
        }
    }
}

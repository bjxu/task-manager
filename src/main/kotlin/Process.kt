import java.time.Instant

data class Process private constructor(
    val name: String,
    val pid: Int,
    val priority: PriorityType,
    val timestamp: Instant
) : Comparable<Process> {
    /**
     * Kills the underlying process
     */
    fun kill() {}

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
            else -> 0
        }
    }

    data class Builder(
        var name: String? = null,
        var pid: Int? = null,
        var priority: PriorityType? = null,
        var timestamp: Instant? = null
    ) {

        fun name(name: String) = apply { this.name = name }
        fun pid(pid: Int) = apply { this.pid = pid }
        fun priority(priority: PriorityType) = apply { this.priority = priority }
        fun timestamp(timestamp: Instant) = apply { this.timestamp = timestamp }
        fun build(): Process {
            val name = this.name ?: ""
            val pid = this.pid ?: 0
            val priority = this.priority ?: PriorityType.LOW
            val timestamp = this.timestamp ?: Instant.MIN
            return Process(name, pid, priority, timestamp)
        }
    }
}

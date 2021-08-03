import java.util.Comparator
import kotlin.test.assertEquals

class CommonBehaviorTests {
    fun testAdd(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(1).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(3).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        assertEquals(
            listOf(procBuilder1.build(), procBuilder2.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testAddIncompleteBuilder(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("").pid(3).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        assertEquals(
            listOf(procBuilder2.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testAddDuplicatePid(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(1).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(3).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        val procBuilder4 = Process.Builder().name("proc3").pid(2).priority(PriorityType.HIGH)
        taskManager.add(procBuilder4)
        assertEquals(
            listOf(procBuilder1.build(), procBuilder2.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testList(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(3).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(1).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        assertEquals(
            listOf(procBuilder1.build(), procBuilder2.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
        assertEquals(
            listOf(procBuilder2.build(), procBuilder1.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::priority))
        )
        assertEquals(
            listOf(procBuilder3.build(), procBuilder2.build(), procBuilder1.build()),
            taskManager.list(Comparator.comparing(Process::pid))
        )
    }

    fun testKill(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(3).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(1).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        taskManager.kill(3)
        assertEquals(
            listOf(procBuilder2.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testKillNotExist(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(3).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(1).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        taskManager.kill(4)
        assertEquals(
            listOf(procBuilder1.build(), procBuilder2.build(), procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testKillGroup(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(3).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.MEDIUM)
        val procBuilder3 = Process.Builder().name("proc3").pid(1).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        taskManager.killGroup(PriorityType.MEDIUM)
        assertEquals(
            listOf(procBuilder3.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    fun testKillAll(taskManager: TaskManager) {
        val procBuilder1 = Process.Builder().name("proc1").pid(3).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.MEDIUM)
        val procBuilder3 = Process.Builder().name("proc3").pid(1).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        taskManager.killAll()
        assertEquals(
            listOf(),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )

    }
}

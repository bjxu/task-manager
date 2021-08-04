import org.junit.jupiter.api.Test
import java.util.Comparator
import kotlin.test.assertEquals

class FifoTaskManagerTest {
    private val taskManager = FifoTaskManager(3)
    private val commonBehaviorTests = CommonBehaviorTests()

    @Test
    fun testAddOverCapacity() {
        val procBuilder1 = Process.Builder().name("proc1").pid(1).priority(PriorityType.MEDIUM)
        val procBuilder2 = Process.Builder().name("proc2").pid(2).priority(PriorityType.LOW)
        val procBuilder3 = Process.Builder().name("proc3").pid(3).priority(PriorityType.HIGH)
        taskManager.add(procBuilder1)
        taskManager.add(procBuilder2)
        taskManager.add(procBuilder3)
        val procBuilder4 = Process.Builder().name("proc4").pid(4).priority(PriorityType.HIGH)
        taskManager.add(procBuilder4)
        assertEquals(
            listOf(procBuilder2.build(), procBuilder3.build(), procBuilder4.build()),
            taskManager.list(Comparator.comparing(Process::timestamp))
        )
    }

    @Test
    fun testAdd() {
        commonBehaviorTests.testAdd(taskManager)
    }

    @Test
    fun testAddIncompleteBuilder() {
        commonBehaviorTests.testAddIncompleteBuilder(taskManager)
    }

    @Test
    fun testAddDuplicatePid() {
        commonBehaviorTests.testAddDuplicatePid(taskManager)
    }

    @Test
    fun testList() {
        commonBehaviorTests.testList(taskManager)
    }

    @Test
    fun testKill() {
        commonBehaviorTests.testKill(taskManager)
    }

    @Test
    fun testKillNotExist() {
        commonBehaviorTests.testKillNotExist(taskManager)
    }

    @Test
    fun testKillGroup() {
        commonBehaviorTests.testKillGroup(taskManager)
    }

    @Test
    fun testKillAll() {
        commonBehaviorTests.testKillAll(taskManager)
    }
}

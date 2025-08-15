@file:Suppress("unused")

package benchmark.intersection

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Blackhole
import kotlinx.benchmark.Param
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import kotlin.random.Random

@State(Scope.Benchmark)
open class IntersectionBenchmark {

    @Param("10", "100", "1000")
    lateinit var receiverSize: String

    @Param("10", "100", "1000")
    lateinit var parameterSize: String

    @Param("0", "25", "50", "75", "100")
    lateinit var intersectionPercentage: String

    private lateinit var receiver: Iterable<Int>
    private lateinit var parameter: Iterable<Int>

    @Setup
    fun generateArrays() {
        val receiverArray = IntArray(receiverSize.toInt()) { it }
        val parameterArray = IntArray(parameterSize.toInt()) { Int.MAX_VALUE - it }
        for (i in 0..(intersectionPercentage.toInt() / 100.0 * receiverSize.toInt()).toInt()) {
            parameterArray[i] = receiverArray[i]
        }
        val random = Random(0xcafebabe)
        receiverArray.shuffle(random)
        parameterArray.shuffle(random)
        receiver = receiverArray.asIterable()
        parameter = parameterArray.asIterable()
    }

    @Benchmark
    fun intersectBenchmark(bh: Blackhole) {
        bh.consume(receiver intersect parameter)
    }
}

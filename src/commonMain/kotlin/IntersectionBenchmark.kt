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

    @Param("100", "10000")
    lateinit var receiverSize: String

    @Param("100", "10000")
    lateinit var parameterSize: String

    @Param("0", "25", "75", "100")
    lateinit var intersectionPercentage: String

    private lateinit var receiver: Iterable<Int>
    private lateinit var parameter: Iterable<Int>

    @Setup
    fun generateArrays() {
        val random = Random(0xcafebabe)

        val receiverArray = IntArray(receiverSize.toInt()) { it }
        receiverArray.shuffle(random)

        val parameterArray = IntArray(parameterSize.toInt()) { Int.MAX_VALUE - it }
        for (i in 0..<(intersectionPercentage.toInt() / 100.0 * receiverSize.toInt()).toInt()) {
            parameterArray[i] = receiverArray[i]
        }
        parameterArray.shuffle(random)

        receiver = receiverArray.asIterable()
        parameter = parameterArray.asIterable()
    }

    @Benchmark
    fun intersect71822(bh: Blackhole) {
        bh.consume(receiver intersect71822 parameter)
    }

    @Benchmark
    fun intersectOpt(bh: Blackhole) {
        bh.consume(receiver intersectOpt parameter)
    }
}

package dev.chu.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * https://medium.com/javarevisited/java-concurrency-thread-pools-3f1902b7beee
 */
internal class Task(private val name: String) : Runnable {
    // Prints task name and sleeps for 1s
    // This Whole process is repeated 5 times
    override fun run() {
        try {
            for (i in 0..5) {
                if (i == 0) {
                    val d = Date()
                    val ft = SimpleDateFormat("hh:mm:ss")
                    println("Initialization Time for task name - $name = ${ft.format(d)}")
                    //prints the initialization time for every task
                } else {
                    val d = Date()
                    val ft = SimpleDateFormat("hh:mm:ss")
                    println("Executing Time for task name - $name = ${ft.format(d)}")
                    // prints the execution time for every task
                }
                Thread.sleep(1000)
            }
            println("$name complete")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

object TestThreadPool {
    // Maximum number of threads in thread pool
    const val MAX_THREADS = 5
    @JvmStatic
    fun main(args: Array<String>) {
        // creates five tasks
        val taskA: Runnable = Task("taskA")
        val taskB: Runnable = Task("taskB")
        val taskC: Runnable = Task("taskC")
        val taskD: Runnable = Task("taskD")
        val taskE: Runnable = Task("taskE")
        val pool: ExecutorService = Executors.newFixedThreadPool(MAX_THREADS)
        pool.execute(taskA)
        pool.execute(taskB)
        pool.execute(taskC)
        pool.execute(taskD)
        pool.execute(taskE)

        // pool shutdown
        pool.shutdown()
    }
}
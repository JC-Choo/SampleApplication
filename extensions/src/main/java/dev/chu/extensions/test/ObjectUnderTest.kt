package dev.chu.extensions.test

object ObjectUnderTest: SampleInterface {
    override fun doSomething(testParameter: String): String {
        return "Operation done with $testParameter"
    }
}

interface SampleInterface {
    fun doSomething(testParameter: String): String
}
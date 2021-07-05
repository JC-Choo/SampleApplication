package dev.chu.extensions.test

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class ObjectUnderTestTest {
    @Test
    fun testObjectWithMockito() {
        val mock = Mockito.mock(SampleInterface::class.java)
        `when` (mock.doSomething("parameter")).thenReturn("Mocked result")

        val answer = mock.doSomething("parameter")
        assertEquals("Mocked result", answer)
    }
}
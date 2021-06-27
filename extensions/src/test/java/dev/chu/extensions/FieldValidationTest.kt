package dev.chu.extensions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FieldValidationTest {
    /*
    test case for empty email address
     */
    @Test
    fun `empty email address returns false`() {
        val resultValue = FieldValidation.isValidLoginFields("", "some password")
        assertThat(resultValue).isFalse()
    }

    /*
    test case for empty password
     */
    @Test
    fun `empty password returns false`() {
        val resultValue = FieldValidation.isValidLoginFields("aaa@aaa.com", "")
        assertThat(resultValue).isFalse()
    }

    /*
    test case for empty email address and password
    */
    @Test
    fun `empty email address and password returns false`() {
        val resultValue = FieldValidation.isValidLoginFields("", "")
        assertThat(resultValue).isFalse()
    }

    /*
    test case for valid email and invalid password (less than 6 characters)
    */
    @Test
    fun `valid email address and password is less than 6 characters returns false`() {
        val resultValue = FieldValidation.isValidLoginFields(
            "arsalankhan994@gmail.com",
            "1234"
        )
        assertThat(resultValue).isFalse()
    }

    /*
    test case for invalid email and valid password
    */
    @Test
    fun `invalid email address and valid password returns false`() {
        val resultValue = FieldValidation.isValidLoginFields(
            "arsalankhan994",
            "123456"
        )
        assertThat(resultValue).isFalse()
    }

    /*
    test case for both valid fields
    */
    @Test
    fun `valid email address and password returns true`() {
        val resultValue = FieldValidation.isValidLoginFields(
            "arsalankhan994@gmail.com",
            "123456"
        )
        assertThat(resultValue).isTrue()
    }
}
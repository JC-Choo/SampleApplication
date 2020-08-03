package dev.chu.memo.ui.rv_sticky_header.test01

import androidx.annotation.NonNull
import java.util.*

class Person(
    @param:NonNull val firstName: CharSequence,
    @param:NonNull val lastName: CharSequence
) : Comparable<Person?> {

    companion object {
        private const val NAME_DISPLAY = "%s, %s"
    }

    val fullName: String
        get() = java.lang.String.format(
            Locale.getDefault(),
            NAME_DISPLAY,
            lastName,
            firstName
        )

    override fun compareTo(person: Person?): Int {
        return lastName.toString().compareTo(person?.lastName.toString())
    }

}
package by.freiding.braindrop.core.common

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/** Single source of truth for "today" — used everywhere data needs to be tied to a calendar day. */
object AppClock {
    fun todayIso(): String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
}

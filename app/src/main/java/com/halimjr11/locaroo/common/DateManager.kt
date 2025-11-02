package com.halimjr11.locaroo.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateManager @Inject constructor() {

    /**
     * Returns the current date.
     *
     * @return The current date.
     */
    fun today(): LocalDate = LocalDate.now()

    /**
     * Returns the start of the week for a given date.
     *
     * The start of the week is defined as the most recent Sunday before the given date.
     * If the given date is already a Sunday, it is returned unchanged.
     *
     * @param date The date for which to find the start of the week. Defaults to the current date.
     * @return The start of the week for the given date.
     */
    fun startOfWeek(date: LocalDate = today()): LocalDate {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    }

    /**
     * Returns a list of all dates in the week of the given date.
     *
     * The list will contain the most recent Sunday before the given date, followed by Monday, Tuesday, Wednesday, Thursday, Friday, and Saturday of the same week.
     * If the given date is already a Sunday, it will be the first element of the list.
     *
     * @param date The date for which to find the days of the week. Defaults to the current date.
     * @return A list of all dates in the week of the given date.
     */
    fun daysOfWeek(date: LocalDate = today()): List<LocalDate> {
        val start = startOfWeek(date)
        return (0..6).map { start.plusDays(it.toLong()) }
    }
}
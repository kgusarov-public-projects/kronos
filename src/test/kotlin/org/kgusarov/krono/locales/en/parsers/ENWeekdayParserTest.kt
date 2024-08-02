package org.kgusarov.krono.locales.en.parsers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ParsingOption
import org.kgusarov.krono.RefDateInputFactory
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import java.util.stream.Stream

internal class ENWeekdayParserTest {
    @Test
    fun casualMonday() {
        testSingleCase(Krono.enCasual, "Monday", RefDateInputFactory("2012-08-09T00:00:00")) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("Monday")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2012)
                assertThat(get(KronoComponents.Month)).isEqualTo(8)
                assertThat(get(KronoComponents.Day)).isEqualTo(6)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(1)

                assertThat(isCertain(KronoComponents.Day)).isFalse()
                assertThat(isCertain(KronoComponents.Month)).isFalse()
                assertThat(isCertain(KronoComponents.Year)).isFalse()
                assertThat(isCertain(KronoComponents.Weekday)).isTrue()

                assertDate("2012-08-06T12:00:00")
            }
        }
    }

    @Test
    fun casualThursday() {
        testSingleCase(Krono.enCasual, "Thursday", RefDateInputFactory("2012-08-09T00:00:00")) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("Thursday")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2012)
                assertThat(get(KronoComponents.Month)).isEqualTo(8)
                assertThat(get(KronoComponents.Day)).isEqualTo(9)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(4)

                assertDate("2012-08-09T12:00:00")
            }
        }
    }

    @Test
    fun casualSunday() {
        testSingleCase(Krono.enCasual, "Sunday", RefDateInputFactory("2012-08-09T00:00:00")) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("Sunday")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2012)
                assertThat(get(KronoComponents.Month)).isEqualTo(8)
                assertThat(get(KronoComponents.Day)).isEqualTo(12)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(7)

                assertDate("2012-08-12T12:00:00")
            }
        }
    }

    @Test
    fun lastFriday() {
        testSingleCase(
            Krono.enCasual,
            "The Deadline is last Friday...",
            RefDateInputFactory("2012-08-09T00:00:00"),
        ) {
            assertThat(it.index).isEqualTo(16)
            assertThat(it.text).isEqualTo("last Friday")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2012)
                assertThat(get(KronoComponents.Month)).isEqualTo(8)
                assertThat(get(KronoComponents.Day)).isEqualTo(3)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(5)

                assertDate("2012-08-03T12:00:00")
            }
        }
    }

    @Test
    fun pastFriday() {
        testSingleCase(
            Krono.enCasual,
            "The Deadline is past Friday...",
            RefDateInputFactory("2012-08-09T00:00:00"),
        ) {
            assertThat(it.index).isEqualTo(16)
            assertThat(it.text).isEqualTo("past Friday")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2012)
                assertThat(get(KronoComponents.Month)).isEqualTo(8)
                assertThat(get(KronoComponents.Day)).isEqualTo(3)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(5)

                assertDate("2012-08-03T12:00:00")
            }
        }
    }

    @Test
    fun nextFriday() {
        testSingleCase(
            Krono.enCasual,
            "Let's have a meeting on Friday next week",
            RefDateInputFactory("2015-04-18T00:00:00"),
        ) {
            assertThat(it.index).isEqualTo(21)
            assertThat(it.text).isEqualTo("on Friday next week")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2015)
                assertThat(get(KronoComponents.Month)).isEqualTo(4)
                assertThat(get(KronoComponents.Day)).isEqualTo(24)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(5)

                assertDate("2015-04-24T12:00:00")
            }
        }
    }

    @Test
    fun nextTuesday() {
        testSingleCase(
            Krono.enCasual,
            "I plan on taking the day off on Tuesday, next week",
            RefDateInputFactory("2015-04-18T00:00:00"),
        ) {
            assertThat(it.index).isEqualTo(29)
            assertThat(it.text).isEqualTo("on Tuesday, next week")

            with(it.start) {
                assertThat(get(KronoComponents.Year)).isEqualTo(2015)
                assertThat(get(KronoComponents.Month)).isEqualTo(4)
                assertThat(get(KronoComponents.Day)).isEqualTo(21)
                assertThat(get(KronoComponents.Weekday)).isEqualTo(2)

                assertDate("2015-04-21T12:00:00")
            }
        }
    }

    @ParameterizedTest
    @MethodSource("guessingArgs")
    fun guessing(text: String, ref: String, expectedDay: Int, expectedMonth: Int, expectedYear: Int) {
        testSingleCase(Krono.enCasual, text, ref) {
            with(it.start) {
                assertThat(day).isEqualTo(expectedDay)
                assertThat(month).isEqualTo(expectedMonth)
                assertThat(year).isEqualTo(expectedYear)
            }
        }
    }

    @Test
    fun weekDayWithCasualTime() {
        testSingleCase(Krono.enCasual, "Lets meet on Tuesday morning", "2015-04-18T12:00:00") {
            assertThat(it.index).isEqualTo(10)
            assertThat(it.text).isEqualTo("on Tuesday morning")

            with(it.start) {
                assertThat(year).isEqualTo(2015)
                assertThat(month).isEqualTo(4)
                assertThat(day).isEqualTo(21)
                assertThat(weekday).isEqualTo(2)
                assertThat(hour).isEqualTo(6)

                assertDate("2015-04-21T06:00:00")
            }
        }
    }

    @Test
    fun weekdayRangeFriToMon() {
        testSingleCase(Krono.enCasual, "Friday to Monday", "2023-04-09T12:00:00") {
            assertThat(it.end).isNotNull

            with (it.start) {
                assertThat(year).isEqualTo(2023)
                assertThat(month).isEqualTo(4)
                assertThat(day).isEqualTo(7)
                assertThat(weekday).isEqualTo(5)
            }

            with (it.end!!) {
                assertThat(year).isEqualTo(2023)
                assertThat(month).isEqualTo(4)
                assertThat(day).isEqualTo(10)
                assertThat(weekday).isEqualTo(1)
            }
        }
    }

    @Test
    fun weekdayRangeMonToFri() {
        testSingleCase(Krono.enCasual, "Monday to Friday", "2023-04-09T12:00:00") {
            assertThat(it.end).isNotNull

            with (it.start) {
                assertThat(year).isEqualTo(2023)
                assertThat(month).isEqualTo(4)
                assertThat(day).isEqualTo(10)
                assertThat(weekday).isEqualTo(1)
            }

            with (it.end!!) {
                assertThat(year).isEqualTo(2023)
                assertThat(month).isEqualTo(4)
                assertThat(day).isEqualTo(14)
                assertThat(weekday).isEqualTo(5)
            }
        }
    }

    @Test
    fun weekdayOverlap1() {
        testSingleCase(Krono.enCasual, "Sunday 12/7/2014", "2012-08-09T00:00:00") {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("Sunday 12/7/2014")

            with(it.start) {
                assertThat(year).isEqualTo(2014)
                assertThat(month).isEqualTo(12)
                assertThat(day).isEqualTo(7)
                assertThat(weekday).isEqualTo(7)

                assertThat(certainDay).isTrue()
                assertThat(certainMonth).isTrue()
                assertThat(certainYear).isTrue()
                assertThat(certainWeekday).isTrue()

                assertDate("2014-12-07T12:00:00")
            }
        }
    }

    @Test
    fun `forward dates - monday`() {
        testSingleCase(
            Krono.enCasual,
            "Monday (forward dates only)",
            "2012-08-09T00:00:00",
            ParsingOption(true)
        ) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("Monday")

            with(it.start) {
                assertThat(year).isEqualTo(2012)
                assertThat(month).isEqualTo(8)
                assertThat(day).isEqualTo(13)
                assertThat(weekday).isEqualTo(1)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2012-08-13T12:00:00")
            }
        }
    }

    @Test
    fun `forward dates - this friday to this monday`() {
        testSingleCase(
            Krono.enCasual,
            "this Friday to this Monday",
            "2016-08-01T00:00:00",
            ParsingOption(true)
        ) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("this Friday to this Monday")

            with(it.start) {
                assertThat(year).isEqualTo(2016)
                assertThat(month).isEqualTo(8)
                assertThat(day).isEqualTo(5)
                assertThat(weekday).isEqualTo(5)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2016-08-05T12:00:00")
            }

            with(it.end!!) {
                assertThat(year).isEqualTo(2016)
                assertThat(month).isEqualTo(8)
                assertThat(day).isEqualTo(8)
                assertThat(weekday).isEqualTo(1)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2016-08-08T12:00:00")
            }
        }
    }

    @Test
    fun `forward dates - sunday morning`() {
        testSingleCase(
            Krono.enCasual,
            "sunday morning",
            "2021-08-15T20:00:00",
            ParsingOption(true)
        ) {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("sunday morning")

            with(it.start) {
                assertThat(year).isEqualTo(2021)
                assertThat(month).isEqualTo(8)
                assertThat(day).isEqualTo(22)
                assertThat(weekday).isEqualTo(7)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2021-08-22T06:00:00")
            }
        }
    }

    @Test
    fun `forward dates - vacation monday - friday`() {
        testSingleCase(
            Krono.enCasual,
            "vacation monday - friday",
            "2019-06-13T00:00:00",
            ParsingOption(true)
        ) {
            assertThat(it.text).isEqualTo("monday - friday")

            with(it.start) {
                assertThat(year).isEqualTo(2019)
                assertThat(month).isEqualTo(6)
                assertThat(day).isEqualTo(17)
                assertThat(weekday).isEqualTo(1)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2019-06-17T12:00:00")
            }

            with(it.end!!) {
                assertThat(year).isEqualTo(2019)
                assertThat(month).isEqualTo(6)
                assertThat(day).isEqualTo(21)
                assertThat(weekday).isEqualTo(5)

                assertThat(certainDay).isFalse()
                assertThat(certainMonth).isFalse()
                assertThat(certainYear).isFalse()
                assertThat(certainWeekday).isTrue()

                assertDate("2019-06-21T12:00:00")
            }
        }
    }

    /*

test("Test - Weekday Overlap", function () {
    testSingleCase(chrono.casual, "Sunday, December 7, 2014", new Date(2012, 7, 9), (result) => {
        expect(result.index).toBe(0);
        expect(result.text).toBe("Sunday, December 7, 2014");

        expect(result.start).not.toBeNull();
        expect(result.start.get("year")).toBe(2014);
        expect(result.start.get("month")).toBe(12);
        expect(result.start.get("day")).toBe(7);
        expect(result.start.get("weekday")).toBe(0);

        expect(result.start.isCertain("day")).toBe(true);
        expect(result.start.isCertain("month")).toBe(true);
        expect(result.start.isCertain("year")).toBe(true);
        expect(result.start.isCertain("weekday")).toBe(true);

        expect(result.start).toBeDate(new Date(2014, 12 - 1, 7, 12));
    });
});
     */

    companion object {
        @JvmStatic
        fun guessingArgs(): Stream<Arguments> =
            Stream.of(
                Arguments.of("This Saturday", "2022-08-02T00:00:00", 6, 8, 2022),
                Arguments.of("This Sunday", "2022-08-02T00:00:00", 7, 8, 2022),
                Arguments.of("This Wednesday", "2022-08-02T00:00:00", 3, 8, 2022),
                Arguments.of("This Saturday", "2022-08-07T00:00:00", 13, 8, 2022),
                Arguments.of("This Sunday", "2022-08-07T00:00:00", 7, 8, 2022),
                Arguments.of("This Wednesday", "2022-08-07T00:00:00", 10, 8, 2022),

                Arguments.of("Last Saturday", "2022-08-02T00:00:00", 30, 7, 2022),
                Arguments.of("Last Sunday", "2022-08-02T00:00:00", 31, 7, 2022),
                Arguments.of("Last Wednesday", "2022-08-02T00:00:00", 27, 7, 2022),

                Arguments.of("Next Saturday", "2022-08-02T00:00:00", 13, 8, 2022),
                Arguments.of("Next Sunday", "2022-08-02T00:00:00", 14, 8, 2022),
                Arguments.of("Next Wednesday", "2022-08-02T00:00:00", 10, 8, 2022),

                Arguments.of("Next Saturday", "2022-08-06T00:00:00", 13, 8, 2022),
                Arguments.of("Next Sunday", "2022-08-06T00:00:00", 14, 8, 2022),
                Arguments.of("Next Wednesday", "2022-08-06T00:00:00", 10, 8, 2022),

                Arguments.of("Next Saturday", "2022-08-07T00:00:00", 13, 8, 2022),
                Arguments.of("Next Sunday", "2022-08-07T00:00:00", 14, 8, 2022),
                Arguments.of("Next Wednesday", "2022-08-07T00:00:00", 10, 8, 2022),
            )
    }
}

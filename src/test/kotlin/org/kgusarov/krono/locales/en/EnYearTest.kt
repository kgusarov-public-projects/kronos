package org.kgusarov.krono.locales.en

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kgusarov.krono.Krono
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase

private const val REF_DATE_2012_07_10 = "2012-07-10T00:00:00"

internal class EnYearTest {
    @Test
    fun `year numbers with BCE CE Era label`() {
        testSingleCase(Krono.enCasual, "10 August 234 BCE", REF_DATE_2012_07_10) {
            assertThat(it.text).isEqualTo("10 August 234 BCE")
            assertThat(it.index).isEqualTo(0)

            with(it.start) {
                assertThat(year()).isEqualTo(-234)
                assertThat(month()).isEqualTo(8)
                assertThat(day()).isEqualTo(10)

                assertDate("-0234-08-10T12:00:00")
            }
        }

        testSingleCase(Krono.enCasual, "10 August 88 CE", REF_DATE_2012_07_10) {
            assertThat(it.text).isEqualTo("10 August 88 CE")
            assertThat(it.index).isEqualTo(0)

            with(it.start) {
                assertThat(year()).isEqualTo(88)
                assertThat(month()).isEqualTo(8)
                assertThat(day()).isEqualTo(10)

                assertDate("0088-08-10T12:00:00")
            }
        }
    }

    @Test
    fun `year numbers with BC AD Era label`() {
        testSingleCase(Krono.enCasual, "10 August 234 BC", REF_DATE_2012_07_10) {
            assertThat(it.text).isEqualTo("10 August 234 BC")
            assertThat(it.index).isEqualTo(0)

            with(it.start) {
                assertThat(year()).isEqualTo(-234)
                assertThat(month()).isEqualTo(8)
                assertThat(day()).isEqualTo(10)

                assertDate("-0234-08-10T12:00:00")
            }
        }

        testSingleCase(Krono.enCasual, "10 August 88 AD", REF_DATE_2012_07_10) {
            assertThat(it.text).isEqualTo("10 August 88 AD")
            assertThat(it.index).isEqualTo(0)

            with(it.start) {
                assertThat(year()).isEqualTo(88)
                assertThat(month()).isEqualTo(8)
                assertThat(day()).isEqualTo(10)

                assertDate("0088-08-10T12:00:00")
            }
        }
    }

    @Test
    fun `year numbers with Buddhist Era label`() {
        testSingleCase(Krono.enCasual, "10 August 2555 BE", REF_DATE_2012_07_10) {
            assertThat(it.text).isEqualTo("10 August 2555 BE")
            assertThat(it.index).isEqualTo(0)

            with(it.start) {
                assertThat(year()).isEqualTo(2012)
                assertThat(month()).isEqualTo(8)
                assertThat(day()).isEqualTo(10)

                assertDate("2012-08-10T12:00:00")
            }
        }
    }
}
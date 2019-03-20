package no.nav.dagpenger.events.inntekt.v1

import no.nav.dagpenger.events.inntekt.v1.Inntekt.Companion.LAST_12_MONTHS
import no.nav.dagpenger.events.inntekt.v1.Inntekt.Companion.LAST_36_MONTHS
import no.nav.dagpenger.events.inntekt.v1.Inntekt.Companion.findStartingMonth
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.time.Year
import java.time.YearMonth

class InntektTest {

    val testInntekt = Inntekt("id", inntektsListe = (1..36).toList().map {
        KlassifisertInntektMåned(
            YearMonth.now().minusMonths(it.toLong()),
            listOf(
                KlassifisertInntekt(
                    BigDecimal(1000),
                    InntektKlasse.ARBEIDSINNTEKT
                ),
                KlassifisertInntekt(
                    BigDecimal(2000),
                    InntektKlasse.DAGPENGER_FANGST_FISKE
                )
            )
        )
    })

    @Test
    fun `starting month should be april 2018 going back 12 months from march 2019 `() {
        assertEquals(YearMonth.of(2018, 4), findStartingMonth(YearMonth.of(2019, 3), LAST_12_MONTHS))
    }

    @Test
    fun `starting month should be april 2016 going back 36 months from march 2019 `() {
        assertEquals(YearMonth.of(2016, 4), findStartingMonth(YearMonth.of(2019, 3), LAST_36_MONTHS))
    }

    @Test
    fun `starting month should be jan 2019 going back 12 months from december 2019 `() {
        assertEquals(YearMonth.of(2019, 1), findStartingMonth(YearMonth.of(2019, 12), LAST_12_MONTHS))
    }

    @Test
    fun `starting month should be feb 2016 going back 36 months from jan 2019 `() {
        assertEquals(YearMonth.of(2016, 2), findStartingMonth(YearMonth.of(2019, 1), LAST_36_MONTHS))
    }

    @Test
    fun `empty list of inntektsklasse returns 0`() {
        assertEquals(BigDecimal(0), testInntekt.sumInntektLast12Months(emptyList(), YearMonth.now().minusMonths(1)))
    }

    @Test
    fun `empty inntekt returns 0`() {
        assertEquals(
            BigDecimal(0),
            Inntekt("nothing", emptyList()).sumInntektLast12Months(
                InntektKlasse.values().toList(),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should sum arbeidsinntekt`() {
        assertEquals(
            BigDecimal(12000), testInntekt.sumInntektLast12Months(
                listOf(InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(36000), testInntekt.sumInntektLast36Months(
                listOf(InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should sum arbeidsinntekt and dp fangst fiske `() {
        assertEquals(
            BigDecimal(24000), testInntekt.sumInntektLast12Months(
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(72000), testInntekt.sumInntektLast36Months(
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should sum multiple inntektsklasser`() {
        assertEquals(
            BigDecimal(36000), testInntekt.sumInntektLast12Months(
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE, InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(108000), testInntekt.sumInntektLast36Months(
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE, InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should return 0 when no inntekt matches `() {
        assertEquals(
            BigDecimal(0), testInntekt.sumInntektLast12Months(
                listOf(InntektKlasse.SYKEPENGER),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(0), testInntekt.sumInntektLast36Months(
                listOf(InntektKlasse.SYKEPENGER),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun `filtering period of last three months affects sum of inntekt`() {
        val filteredInntekt = testInntekt.filterPeriod(YearMonth.now().minusMonths(3), YearMonth.now().minusMonths(1))
        assertEquals(
            BigDecimal(9000),
            filteredInntekt.sumInntektLast12Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1))
        )
        assertEquals(
            BigDecimal(33000),
            filteredInntekt.sumInntektLast36Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1))
        )
    }

    @Test
    fun `filtering period not overlapping exisiting months does not affect sum `() {
        val filteredInntekt = testInntekt.filterPeriod(YearMonth.now().minusMonths(48), YearMonth.now().minusMonths(37))
        assertEquals(
            testInntekt.sumInntektLast12Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1)),
            filteredInntekt.sumInntektLast12Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1))
        )
        assertEquals(
            testInntekt.sumInntektLast36Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1)),
            filteredInntekt.sumInntektLast36Months(listOf(InntektKlasse.ARBEIDSINNTEKT), YearMonth.now().minusMonths(1))
        )
    }

    @Test
    fun `filter period throws exception if from-argument is more recent than to`() {
        assertThrows<IllegalArgumentException> {
            testInntekt.filterPeriod(
                YearMonth.of(2019, 5),
                YearMonth.of(2019, 4)
            )
        }
    }

    @Test
    fun `inntektsPerioder splits up inntekt correctly`() {

        val senesteMåned = YearMonth.of(2019, 3)

        val inntekts_up_to_march_2019 = Inntekt("id", inntektsListe = (0..36).toList().map {
            KlassifisertInntektMåned(
                YearMonth.now().minusMonths(it.toLong()),
                listOf(
                    KlassifisertInntekt(
                        BigDecimal(1000),
                        InntektKlasse.ARBEIDSINNTEKT
                    )
                )
            )
        })

        val (first, second, third) = inntekts_up_to_march_2019.inntektsPerioder(senesteMåned)

        assertEquals(12, first.size)
        assertEquals(12, second.size)
        assertEquals(12, third.size)

        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
        assertTrue(second.all { it.årMåned in YearMonth.of(2017, 4)..YearMonth.of(2018, 3) })
        assertTrue(third.all { it.årMåned in YearMonth.of(2016, 4)..YearMonth.of(2017, 3) })
    }

    @Test
    fun `inntektsPerioder correctly splits up inntekt for only last year`() {
        val senesteMåned = YearMonth.of(2019, 3)
        val onlyInntektLastYear = Inntekt("id", inntektsListe = (0..11).toList().map {
            KlassifisertInntektMåned(senesteMåned.minusMonths(it.toLong()), emptyList())
        })
        val (first, second, third) = onlyInntektLastYear.inntektsPerioder(senesteMåned)

        assertEquals(12, first.size)
        assertEquals(0, second.size)
        assertEquals(0, third.size)
        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
    }

    @Test
    fun `inntektsPerioder correctly splits up inntekt missing earliest year`() {
        val senesteMåned = YearMonth.of(2019, 3)
        val noInntektThirdPeriod = Inntekt("id", inntektsListe = (0..23).toList().map {
            KlassifisertInntektMåned(senesteMåned.minusMonths(it.toLong()), emptyList())
        })

        val (first, second, third) = noInntektThirdPeriod.inntektsPerioder(senesteMåned)
        assertEquals(12, noInntektThirdPeriod.inntektsPerioder(senesteMåned).first.size)
        assertEquals(12, noInntektThirdPeriod.inntektsPerioder(senesteMåned).second.size)
        assertEquals(0, noInntektThirdPeriod.inntektsPerioder(senesteMåned).third.size)
        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
        assertTrue(second.all { it.årMåned in YearMonth.of(2017, 4)..YearMonth.of(2018, 3) })
    }

    @Test
    fun `inntektsPerioder correctly splits up noncontinous inntekt`() {
        val senesteMåned = YearMonth.of(2019, 3)
        val nonContinous = Inntekt("id", inntektsListe = ((0..5).toList() + (10..24).toList()).map {
            KlassifisertInntektMåned(senesteMåned.minusMonths(it.toLong()), emptyList())
        })

        val (first, second, third) = nonContinous.inntektsPerioder(senesteMåned)
        assertEquals(8, nonContinous.inntektsPerioder(senesteMåned).first.size)
        assertEquals(12, nonContinous.inntektsPerioder(senesteMåned).second.size)
        assertEquals(1, nonContinous.inntektsPerioder(senesteMåned).third.size)
        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
        assertTrue(second.all { it.årMåned in YearMonth.of(2017, 4)..YearMonth.of(2018, 3) })
        assertTrue(third.all { it.årMåned in YearMonth.of(2016, 4)..YearMonth.of(2017, 3) })
    }
}

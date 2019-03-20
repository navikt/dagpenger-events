package no.nav.dagpenger.events.inntekt.v1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.time.YearMonth

class InntektTest {

    val senesteMåned = YearMonth.of(2019, 3)

    val testInntektsListe = (0..38).toList().map {
        KlassifisertInntektMåned(
            senesteMåned.minusMonths(it.toLong()),
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
    }

    val testInntekt = Inntekt("id", testInntektsListe)

    @Test
    fun `sum with empty list of inntektsklasserToSum returns 0`() {
        assertEquals(BigDecimal(0), testInntektsListe.sumInntekt(emptyList()))
    }

    @Test
    fun `empty inntekt returns 0`() {
        assertEquals(BigDecimal(0), emptyList<KlassifisertInntektMåned>().sumInntekt(InntektKlasse.values().toList()))
    }

    @Test
    fun ` should sum arbeidsinntekt correctly`() {
        assertEquals(
            BigDecimal(12000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
        )

        assertEquals(
            BigDecimal(36000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
        )
    }

    @Test
    fun ` should sum dp fangst fiske correctly `() {
        assertEquals(
            BigDecimal(24000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(listOf(InntektKlasse.DAGPENGER_FANGST_FISKE))
        )

        assertEquals(
            BigDecimal(72000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(listOf(InntektKlasse.DAGPENGER_FANGST_FISKE))
        )
    }

    @Test
    fun ` should sum multiple inntektsklasser`() {
        assertEquals(
            BigDecimal(36000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(
                listOf(
                    InntektKlasse.DAGPENGER_FANGST_FISKE,
                    InntektKlasse.ARBEIDSINNTEKT
                )
            )
        )

        assertEquals(
            BigDecimal(108000),
            testInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(
                listOf(
                    InntektKlasse.DAGPENGER_FANGST_FISKE,
                    InntektKlasse.ARBEIDSINNTEKT
                )
            )
        )
    }

    @Test
    fun ` should return 0 when no inntekt matches `() {
        assertEquals(
            BigDecimal(0),
            testInntektsListe.sumInntekt(listOf(InntektKlasse.SYKEPENGER))
        )
    }

    @Test
    fun `filtering period of last three months affects sum of inntekt`() {
        val filteredInntekt = testInntekt.filterPeriod(YearMonth.now().minusMonths(3), YearMonth.now().minusMonths(1))
        assertEquals(
            BigDecimal(9000),
            filteredInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
        )
        assertEquals(
            BigDecimal(33000),
            filteredInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
        )
    }

    @Test
    fun `filtering period not overlapping exisiting months does not affect sum `() {
        val filteredInntekt = testInntekt.filterPeriod(YearMonth.now().minusMonths(48), YearMonth.now().minusMonths(37))
        assertEquals(
            testInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT)),
            filteredInntekt.splitIntoInntektsPerioder(senesteMåned).first.sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
        )
        assertEquals(
            testInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT)),
            filteredInntekt.splitIntoInntektsPerioder(senesteMåned).toList().flatten().sumInntekt(listOf(InntektKlasse.ARBEIDSINNTEKT))
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

        val (first, second, third) = inntekts_up_to_march_2019.splitIntoInntektsPerioder(senesteMåned)

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
        val (first, second, third) = onlyInntektLastYear.splitIntoInntektsPerioder(senesteMåned)

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

        val (first, second, third) = noInntektThirdPeriod.splitIntoInntektsPerioder(senesteMåned)
        assertEquals(12, noInntektThirdPeriod.splitIntoInntektsPerioder(senesteMåned).first.size)
        assertEquals(12, noInntektThirdPeriod.splitIntoInntektsPerioder(senesteMåned).second.size)
        assertEquals(0, noInntektThirdPeriod.splitIntoInntektsPerioder(senesteMåned).third.size)
        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
        assertTrue(second.all { it.årMåned in YearMonth.of(2017, 4)..YearMonth.of(2018, 3) })
    }

    @Test
    fun `inntektsPerioder correctly splits up noncontinous inntekt`() {
        val nonContinous = Inntekt("id", inntektsListe = ((0..5).toList() + (10..24).toList()).map {
            KlassifisertInntektMåned(senesteMåned.minusMonths(it.toLong()), emptyList())
        })

        val (first, second, third) = nonContinous.splitIntoInntektsPerioder(senesteMåned)
        assertEquals(8, nonContinous.splitIntoInntektsPerioder(senesteMåned).first.size)
        assertEquals(12, nonContinous.splitIntoInntektsPerioder(senesteMåned).second.size)
        assertEquals(1, nonContinous.splitIntoInntektsPerioder(senesteMåned).third.size)
        assertTrue(first.all { it.årMåned in YearMonth.of(2018, 4)..YearMonth.of(2019, 3) })
        assertTrue(second.all { it.årMåned in YearMonth.of(2017, 4)..YearMonth.of(2018, 3) })
        assertTrue(third.all { it.årMåned in YearMonth.of(2016, 4)..YearMonth.of(2017, 3) })
    }
}

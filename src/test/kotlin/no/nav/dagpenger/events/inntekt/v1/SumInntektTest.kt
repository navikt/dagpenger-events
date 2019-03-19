package no.nav.dagpenger.events.inntekt.v1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.YearMonth

class SumInntektTest {

    val arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month = (1..36).toList().map {
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
    }

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
    fun `empty list of inntekt returns 0`() {
        assertEquals(BigDecimal(0), sumInntektLast12Months(emptyList(), emptyList(), YearMonth.now().minusMonths(1)))
    }

    @Test
    fun ` should sum arbeidsinntekt`() {
        assertEquals(
            BigDecimal(12000), sumInntektLast12Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(36000), sumInntektLast36Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should sum arbeidsinntekt and dp fangst fiske `() {
        assertEquals(
            BigDecimal(24000), sumInntektLast12Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(72000), sumInntektLast36Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should sum multiple inntektsklasser`() {
        assertEquals(
            BigDecimal(36000), sumInntektLast12Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE, InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(108000), sumInntektLast36Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.DAGPENGER_FANGST_FISKE, InntektKlasse.ARBEIDSINNTEKT),
                YearMonth.now().minusMonths(1)
            )
        )
    }

    @Test
    fun ` should return 0 when no inntekt matches `() {
        assertEquals(
            BigDecimal(0), sumInntektLast12Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.SYKEPENGER),
                YearMonth.now().minusMonths(1)
            )
        )

        assertEquals(
            BigDecimal(0), sumInntektLast36Months(
                arbeidsInntekt_1000_month_and_dpfangstfiske_2000_month,
                listOf(InntektKlasse.SYKEPENGER),
                YearMonth.now().minusMonths(1)
            )
        )
    }
}

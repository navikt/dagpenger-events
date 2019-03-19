package no.nav.dagpenger.events.inntekt.v1

import java.math.BigDecimal
import java.time.YearMonth

internal const val LAST_12_MONTHS = 11
internal const val LAST_36_MONTHS = 35

fun sumInntektLast12Months(
    inntekt: List<KlassifisertInntektMåned>,
    inntektsKlasserToSum: List<InntektKlasse>,
    lastMonth: YearMonth
) = sumInntekt(inntekt, inntektsKlasserToSum, findStartingMonth(lastMonth, LAST_12_MONTHS), lastMonth)

fun sumInntektLast36Months(
    inntektsListe: List<KlassifisertInntektMåned>,
    inntektsKlasserToSum: List<InntektKlasse>,
    lastMonth: YearMonth
) = sumInntekt(inntektsListe, inntektsKlasserToSum, findStartingMonth(lastMonth, LAST_36_MONTHS), lastMonth)

private fun sumInntekt(
    inntekt: List<KlassifisertInntektMåned>,
    inntektsKlasserToSum: List<InntektKlasse>,
    from: YearMonth,
    to: YearMonth
): BigDecimal {
    val periodToSum = inntekt.filter { it.årMåned in from..to }

    return periodToSum
        .flatMap {
            it.klassifiserteInntekter
                .filter { it.inntektKlasse in inntektsKlasserToSum }
                .map { it.beløp }
        }.fold(BigDecimal.ZERO, BigDecimal::add)
}

internal fun findStartingMonth(senesteMåned: YearMonth, lengde: Int): YearMonth {
    return senesteMåned.minusMonths(lengde.toLong())
}

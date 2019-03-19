package no.nav.dagpenger.events.inntekt.v1

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.time.YearMonth

class Inntekt(val inntektsId: String, private val inntektsListe: List<KlassifisertInntektMåned>) {
    internal companion object {
        val LAST_12_MONTHS = 11
        val LAST_36_MONTHS = 35

        fun findStartingMonth(senesteMåned: YearMonth, lengde: Int): YearMonth {
            return senesteMåned.minusMonths(lengde.toLong())
        }
    }

    fun filterPeriod(from: YearMonth, to: YearMonth): Inntekt {
        if (from.isAfter(to)) throw IllegalArgumentException("Argument from=$from is after argument to=$to")
        return Inntekt(inntektsId, inntektsListe.filter { it.årMåned !in from..to })
    }

    fun sumInntektLast12Months(
        inntektsKlasserToSum: List<InntektKlasse>,
        lastMonth: YearMonth
    ) = sumInntekt(inntektsKlasserToSum, findStartingMonth(lastMonth, LAST_12_MONTHS), lastMonth)

    fun sumInntektLast36Months(
        inntektsKlasserToSum: List<InntektKlasse>,
        lastMonth: YearMonth
    ) = sumInntekt(inntektsKlasserToSum, findStartingMonth(lastMonth, LAST_36_MONTHS), lastMonth)

    private fun sumInntekt(
        inntektsKlasserToSum: List<InntektKlasse>,
        from: YearMonth,
        to: YearMonth
    ): BigDecimal {
        val periodToSum = inntektsListe.filter { it.årMåned in from..to }

        return periodToSum
            .flatMap {
                it.klassifiserteInntekter
                    .filter { it.inntektKlasse in inntektsKlasserToSum }
                    .map { it.beløp }
            }.fold(BigDecimal.ZERO, BigDecimal::add)
    }
}

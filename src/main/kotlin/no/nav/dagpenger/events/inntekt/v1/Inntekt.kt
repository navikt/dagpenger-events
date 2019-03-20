package no.nav.dagpenger.events.inntekt.v1

import java.lang.IllegalArgumentException
import java.time.YearMonth

class Inntekt(val inntektsId: String, private val inntektsListe: List<KlassifisertInntektMåned>) {
    fun splitIntoInntektsPerioder(senesteMåned: YearMonth): Triple<List<KlassifisertInntektMåned>, List<KlassifisertInntektMåned>, List<KlassifisertInntektMåned>> {
        return Triple(
            inntektsListe.filter { it.årMåned in senesteMåned.minusYears(1).plusMonths(1)..senesteMåned },
            inntektsListe.filter { it.årMåned in senesteMåned.minusYears(2).plusMonths(1)..senesteMåned.minusYears(1) },
            inntektsListe.filter { it.årMåned in senesteMåned.minusYears(3).plusMonths(1)..senesteMåned.minusYears(2) }
        )
    }

    fun filterPeriod(from: YearMonth, to: YearMonth): Inntekt {
        if (from.isAfter(to)) throw IllegalArgumentException("Argument from=$from is after argument to=$to")
        return Inntekt(inntektsId, inntektsListe.filter { it.årMåned !in from..to })
    }
}

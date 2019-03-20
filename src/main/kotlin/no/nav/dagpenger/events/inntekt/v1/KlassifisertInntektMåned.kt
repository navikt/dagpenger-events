package no.nav.dagpenger.events.inntekt.v1

import java.math.BigDecimal
import java.time.YearMonth

data class KlassifisertInntektMåned(
    val årMåned: YearMonth,
    val klassifiserteInntekter: List<KlassifisertInntekt>
)

fun Collection<KlassifisertInntektMåned>.sumInntekt(inntektsKlasserToSum: List<InntektKlasse>) =
    this.flatMap {
        it.klassifiserteInntekter
            .filter { it.inntektKlasse in inntektsKlasserToSum }
            .map { it.beløp }
    }.fold(BigDecimal.ZERO, BigDecimal::add)

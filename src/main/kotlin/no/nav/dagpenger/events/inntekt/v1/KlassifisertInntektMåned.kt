package no.nav.dagpenger.events.inntekt.v1

import java.time.YearMonth

data class KlassifisertInntektMåned(
    val årMåned: YearMonth,
    val klassifiserteInntekter: List<KlassifisertInntekt>
)

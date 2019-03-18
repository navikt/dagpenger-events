package no.nav.dagpenger.events.inntekt.v1

import java.math.BigDecimal

data class KlassifisertInntekt(
    val beløp: BigDecimal,
    val inntektKlasse: InntektKlasse
)

enum class InntektKlasse {
    ARBEIDSINNTEKT,
    DAGPENGER,
    DAGPENGER_FANGST_FISKE,
    SYKEPENGER_FANGST_FISKE,
    NÆRINGSINNTEKT,
    SYKEPENGER,
    TILTAKSLØNN
}
package no.nav.dagpenger.events.inntekt.v1

data class Inntekt(
    val inntektsId: String,
    val inntektsListe: List<KlassifisertInntektMåned>
)
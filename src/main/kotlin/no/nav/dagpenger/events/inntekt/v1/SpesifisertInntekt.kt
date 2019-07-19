package no.nav.dagpenger.events.inntekt.v1

import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

data class SpesifisertInntekt(
    val inntektId: InntektId,
    val månedsInntekter: List<MånedsInntekt>,
    val ident: Aktør,
    val manueltRedigert: Boolean,
    val timestamp: LocalDateTime
)

data class MånedsInntekt(
    val årMåned: YearMonth,
    val avvikListe: List<Avvik>,
    val posteringer: List<Postering>
)

data class Postering(
    val beløp: BigDecimal,
    val fordel: String,
    val inntektskilde: String,
    val inntektsstatus: String,
    val inntektsperiodetype: String,
    val leveringstidspunkt: YearMonth? = null,
    val opptjeningsland: String? = null,
    val opptjeningsperiode: Periode? = null,
    val skattemessigBosattLand: String? = null,
    val utbetaltIMåned: YearMonth,
    val opplysningspliktig: Aktør? = null,
    val inntektsinnsender: Aktør? = null,
    val virksomhet: Aktør? = null,
    val inntektsmottaker: Aktør? = null,
    val inngårIGrunnlagForTrekk: Boolean? = null,
    val utløserArbeidsgiveravgift: Boolean? = null,
    val informasjonsstatus: String? = null,
    val posteringsType: PosteringsType
)

data class InntektId(val id: String) {
    init {
        try {
            ULID.parseULID(id)
        } catch (e: IllegalArgumentException) {
            throw IllegalInntektIdException("ID $id is not a valid ULID", e)
        }
    }
}

data class Periode(
    val startDato: LocalDate,
    val sluttDato: LocalDate
)

data class Aktør(
    val aktørType: AktørType,
    val identifikator: String
)

enum class AktørType {
    AKTOER_ID,
    NATURLIG_IDENT,
    ORGANISASJON
}

data class Avvik(
    val ident: Aktør,
    val opplysningspliktig: Aktør,
    val virksomhet: Aktør?,
    val avvikPeriode: YearMonth,
    val tekst: String
)

class IllegalInntektIdException(override val message: String, override val cause: Throwable?) : java.lang.RuntimeException(message, cause)
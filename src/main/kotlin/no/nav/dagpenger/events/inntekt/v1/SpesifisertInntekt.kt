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
    val beskrivelse: InntektBeskrivelse,
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
    val inntektType: InntektType,
    val tilleggsinformasjon: TilleggsInformasjon? = null,
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

data class TilleggsInformasjon(
    val kategori: String,
    val tilleggsinformasjonDetaljer: TilleggsInformasjonDetaljer?
)

data class TilleggsInformasjonDetaljer(
    val detaljerType: String?,
    val spesielleInntjeningsforhold: SpesielleInntjeningsforhold?
)

enum class InntektType {
    LOENNSINNTEKT,
    NAERINGSINNTEKT,
    PENSJON_ELLER_TRYGD,
    YTELSE_FRA_OFFENTLIGE
}

enum class SpesielleInntjeningsforhold {
    HYRE_TIL_MANNSKAP_PAA_FISKE_SMAAHVALFANGST_OG_SELFANGSTFARTOEY,
    LOENN_VED_ARBEIDSMARKEDSTILTAK,
    UNKNOWN
}

enum class InntektBeskrivelse {
    AKSJER_GRUNNFONDSBEVIS_TIL_UNDERKURS,
    ANNET,
    ARBEIDSOPPHOLD_KOST,
    ARBEIDSOPPHOLD_LOSJI,
    BEREGNET_SKATT,
    BESOEKSREISER_HJEMMET_ANNET,
    BESOEKSREISER_HJEMMET_KILOMETERGODTGJOERELSE_BIL,
    BETALT_UTENLANDSK_SKATT,
    BIL,
    BOLIG,
    BONUS,
    BONUS_FRA_FORSVARET,
    ELEKTRONISK_KOMMUNIKASJON,
    FAST_BILGODTGJOERELSE,
    FAST_TILLEGG,
    FASTLOENN,
    FERIEPENGER,
    FOND_FOR_IDRETTSUTOEVERE,
    FORELDREPENGER,
    HELLIGDAGSTILLEGG,
    HONORAR_AKKORD_PROSENT_PROVISJON,
    HYRETILLEGG,
    INNBETALING_TIL_UTENLANDSK_PENSJONSORDNING,
    KILOMETERGODTGJOERELSE_BIL,
    KOMMUNAL_OMSORGSLOENN_OG_FOSTERHJEMSGODTGJOERELSE,
    KOST_DAGER,
    KOST_DOEGN,
    KOSTBESPARELSE_I_HJEMMET,
    LOSJI,
    IKKE_SKATTEPLIKTIG_LOENN_FRA_UTENLANDSK_DIPLOM_KONSUL_STASJON,
    LOENN_FOR_BARNEPASS_I_BARNETS_HJEM,
    LOENN_TIL_PRIVATPERSONER_FOR_ARBEID_I_HJEMMET,
    LOENN_UTBETALT_AV_VELDEDIG_ELLER_ALLMENNYTTIG_INSTITUSJON_ELLER_ORGANISASJON,
    LOENN_TIL_VERGE_FRA_FYLKESMANNEN,
    OPSJONER,
    OVERTIDSGODTGJOERELSE,
    REISE_ANNET,
    REISE_KOST,
    REISE_LOSJI,
    RENTEFORDEL_LAAN,
    SKATTEPLIKTIG_DEL_FORSIKRINGER,
    SLUTTVEDERLAG,
    SMUSSTILLEGG,
    STIPEND,
    STYREHONORAR_OG_GODTGJOERELSE_VERV,
    SVANGERSKAPSPENGER,
    TIMELOENN,
    TREKK_I_LOENN_FOR_FERIE,
    UREGELMESSIGE_TILLEGG_KNYTTET_TIL_ARBEIDET_TID,
    UREGELMESSIGE_TILLEGG_KNYTTET_TIL_IKKE_ARBEIDET_TID,
    YRKEBIL_TJENESTLIGBEHOV_KILOMETER,
    YRKEBIL_TJENESTLIGBEHOV_LISTEPRIS,
    DAGPENGER_VED_ARBEIDSLOESHET,
    DAGPENGER_TIL_FISKER,
    DAGPENGER_TIL_FISKER_SOM_BARE_HAR_HYRE,
    SYKEPENGER_TIL_FISKER,
    SYKEPENGER_TIL_FISKER_SOM_BARE_HAR_HYRE,
    LOTT_KUN_TRYGDEAVGIFT,
    VEDERLAG,
    SYKEPENGER,
    TIPS,
    SKATTEPLIKTIG_PERSONALRABATT
}

class IllegalInntektIdException(override val message: String, override val cause: Throwable?) : java.lang.RuntimeException(message, cause)
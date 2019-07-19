package no.nav.dagpenger.events.inntekt.v1

enum class PosteringsType(val beskrivelse: String) {
    L_AKSJER_GRUNNFONDSBEVIS_TIL_UNDERKURS("Aksjer/grunnfondsbevis til underkurs"),
    L_ANNET("Annen arbeidsinntekt"),
    L_ANNET_H("Hyre - Annet"),
    L_ANNET_T("Tiltak - Annet"),
    L_ARBEIDSOPPHOLD_KOST("Arbeidsopphold kost"),
    L_ARBEIDSOPPHOLD_LOSJI("Arbeidsopphold losji"),
    L_BEREGNET_SKATT("Beregnet skatt"),
    L_BESOEKSREISER_HJEMMET_ANNET("Besøksreiser hjemmet annet"),
    L_BESOEKSREISER_HJEMMET_KILOMETERGODTGJOERELSE_BIL("Besøksreiser hjemmet kilometergodtgjørelse bil"),
    L_BETALT_UTENLANDSK_SKATT("Betalt utenlandsk skatt"),
    L_BIL("Bil"),
    L_BOLIG("Bolig"),
    L_BONUS("Bonus"),
    L_BONUS_FRA_FORSVARET("Bonus fra forsvaret"),
    L_BONUS_H("Hyre - Bonus"),
    L_BONUS_T("Tiltak - Bonus"),
    L_ELEKTRONISK_KOMMUNIKASJON("Elektronisk kommunikasjon"),
    L_FAST_BILGODTGJOERELSE("Fast bilgodtgjørelse"),
    L_FAST_TILLEGG("Faste tillegg"),
    L_FAST_TILLEGG_H("Hyre - Faste tillegg"),
    L_FAST_TILLEGG_T("Tiltak - Faste tillegg"),
    L_FASTLOENN("Fastlønn"),
    L_FASTLOENN_H("Hyre - Fastlønn"),
    L_FASTLOENN_T("Tiltak - Fastlønn"),
    L_FERIEPENGER("Feriepenger"),
    L_FERIEPENGER_H("Hyre - Feriepenger"),
    L_FERIEPENGER_T("Tiltak - Feriepenger"),
    L_FOND_FOR_IDRETTSUTOEVERE("Fond for idrettsutøvere"),
    L_HELLIGDAGSTILLEGG("Helligdagstillegg"),
    L_HELLIGDAGSTILLEGG_H("Hyre - Helligdagstillegg"),
    L_HELLIGDAGSTILLEGG_T("Tiltak - Helligdagstillegg"),
    L_HONORAR_AKKORD_PROSENT_PROVISJON("Honorar, akkord, prosent eller provisjonslønn"),
    L_HYRETILLEGG("Hyretillegg"),
    L_IKKE_SKATTEPLIKTIG_LOENN_FRA_UTENLANDSK_DIPLOM_KONSUL_STASJON("Lønn mv som ikke er skattepliktig i Norge fra utenlandsk diplomatisk eller konsulær stasjon"),
    L_INNBETALING_TIL_UTENLANDSK_PENSJONSORDNING("Innbetaling til utenlandsk pensjonsordning"),
    L_KILOMETERGODTGJOERELSE_BIL("Kilometergodtgjørelse bil"),
    L_KOMMUNAL_OMSORGSLOENN_OG_FOSTERHJEMSGODTGJOERELSE("Kommunal omsorgslønn og fosterhjemsgodtgjørelse"),
    L_KOST_DAGER("Kost (dager)"),
    L_KOST_DOEGN("Kost (døgn)"),
    L_KOSTBESPARELSE_I_HJEMMET("Kostbesparelse i hjemmet\n"),
    L_LOENN_FOR_BARNEPASS_I_BARNETS_HJEM("Lønn og godtgjørelse til dagmamma eller praktikant som passer barn i barnets hjem"),
    L_LOENN_TIL_PRIVATPERSONER_FOR_ARBEID_I_HJEMMET("Lønn og godtgjørelse til privatpersoner for arbeidsoppdrag i oppdragsgivers hjem"),
    L_LOENN_TIL_VERGE_FRA_FYLKESMANNEN("Lønn til verge fra Fylkesmannen"),
    L_LOENN_UTBETALT_AV_VELDEDIG_ELLER_ALLMENNYTTIG_INSTITUSJON_ELLER_ORGANISASJON("Lønn og godtgjørelse utbetalt av veldedig eller allmennyttig institusjon eller organisasjon"),
    L_LOSJI("Losji"),
    L_OPSJONER("Opsjoner"),
    L_OVERTIDSGODTGJOERELSE("Overtidsgodtgjørelse"),
    L_OVERTIDSGODTGJOERELSE_H("Hyre - Overtidsgodtgjørelse"),
    L_OVERTIDSGODTGJOERELSE_T("Tiltak - Overtidsgodtgjørelse"),
    L_REISE_ANNET("Reise annet"),
    L_REISE_KOST("Reise kost"),
    L_REISE_LOSJI("Reise losji"),
    L_RENTEFORDEL_LAAN("Rentefordel lån"),
    L_SKATTEPLIKTIG_DEL_FORSIKRINGER("Skattepliktig del av visse typer forsikringer"),
    L_SKATTEPLIKTIG_PERSONALRABATT("Skattepliktig personalrabatt"),
    L_SLUTTVEDERLAG(" Sluttvederlag"),
    L_SLUTTVEDERLAG_H("Hyre - Sluttvederlag"),
    L_SLUTTVEDERLAG_T("Tiltak - Sluttvederlag"),
    L_SMUSSTILLEGG("Smusstillegg"),
    L_STIPEND("Stipend"),
    L_STYREHONORAR_OG_GODTGJOERELSE_VERV("Styrehonorar og godtgjørelse i forbindelse med verv"),
    L_TIMELOENN("Timelønn"),
    L_TIMELOENN_H("Hyre - Timelønn"),
    L_TIMELOENN_T("Tiltak - Timelønn"),
    L_TIPS("Tips"),
    L_TREKK_I_LOENN_FOR_FERIE("Trekk i lønn for ferie"),
    L_TREKK_I_LOENN_FOR_FERIE_H("Trekk i lønn for ferie - Hyre"),
    L_TREKK_I_LOENN_FOR_FERIE_T("Trekk i lønn for ferie - Tiltak"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_ARBEIDET_TID("Uregelmessige tillegg knyttet til arbeidet tid"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_ARBEIDET_TID_H("Hyre - Uregelmessige tillegg knyttet til arbeidet tid"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_ARBEIDET_TID_T("Tiltak - Uregelmessige tillegg knyttet til arbeidet tid"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_IKKE_ARBEIDET_TID("Uregelmessige tillegg knyttet til ikke-arbeidet tid"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_IKKE_ARBEIDET_TID_H("Hyre - Uregelmessige tillegg knyttet til ikke-arbeidet tid"),
    L_UREGELMESSIGE_TILLEGG_KNYTTET_TIL_IKKE_ARBEIDET_TID_T("Tiltak - Uregelmessige tillegg knyttet til ikke-arbeidet tid"),
    L_YRKEBIL_TJENESTLIGBEHOV_KILOMETER("Yrkebil tjenestligbehov kilometer"),
    L_YRKEBIL_TJENESTLIGBEHOV_LISTEPRIS("Yrkebil tjenestligbehov listepris"),
    N_ANNET("Annen næringsinntekt"),
    N_DAGPENGER_TIL_FISKER("Dagpenger til fisker"),
    N_LOTT_KUN_TRYGDEAVGIFT("Lott det skal beregnes trygdeavgift av"),
    N_SYKEPENGER("Sykepenger fra næring"),
    N_SYKEPENGER_TIL_DAGMAMMA("Sykepenger til dagmamma\n"),
    N_SYKEPENGER_TIL_FISKER("Sykepenger til fisker"),
    N_SYKEPENGER_TIL_JORD_OG_SKOGBRUKERE("Sykepenger til jord- og skogbrukere"),
    N_VEDERLAG("Vederlag lott"),
    Y_DAGPENGER_TIL_FISKER_SOM_BARE_HAR_HYRE("Dagpenger til fisker som bare har hyre"),
    Y_DAGPENGER_VED_ARBEIDSLOESHET("Dagpenger ved arbeidsløshet"),
    Y_FORELDREPENGER("Foreldrepenger fra folketrygden"),
    Y_SVANGERSKAPSPENGER("Svangerskapspenger"),
    Y_SYKEPENGER("Sykepenger fra folketrygden"),
    Y_SYKEPENGER_TIL_FISKER_SOM_BARE_HAR_HYRE("Sykepenger til fisker som bare har hyre")
}
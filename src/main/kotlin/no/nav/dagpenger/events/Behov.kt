package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Annet
import no.nav.dagpenger.events.avro.Behov
import no.nav.dagpenger.events.avro.Ettersending
import no.nav.dagpenger.events.avro.Søknad
import no.nav.dagpenger.events.avro.Vedtakstype

fun Behov.isSoknad() = this.getHenvendelsesType() is Søknad
fun Behov.isEttersending() = this.getHenvendelsesType() is Ettersending
fun Behov.isAnnet() = this.getHenvendelsesType() is Annet

fun Behov.hasBehandlendeEnhet() = this.getBehandleneEnhet() != null
fun Behov.hasFagsakId() = this.getFagsakId() != null
fun Behov.hasGsakId() = this.getGsaksakId() != null
fun Behov.hasHenvendelsesType() = this.getHenvendelsesType() != null

fun Behov.isGjenopptakSoknad() = isSøknadsType(Vedtakstype.GJENOPPTAK)
fun Behov.isNySoknad() = isSøknadsType(Vedtakstype.NY_RETTIGHET)

private fun Behov.isSøknadsType(type: Vedtakstype) =
    isSoknad() && (this.getHenvendelsesType() as Søknad).getVedtakstype() == type

package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Behov

fun Behov.isSoknad() = this.getHenvendelsesType().getSøknad() != null
fun Behov.isEttersending() = this.getHenvendelsesType().getEttersending() != null
fun Behov.isAnnet() = this.getHenvendelsesType().getAnnet() != null

fun Behov.hasBehandlendeEnhet() = this.getBehandleneEnhet() != null
fun Behov.hasFagsakId() = this.getFagsakId() != null
fun Behov.hasGsakId() = this.getGsaksakId() != null

fun Behov.isGjenopptakSoknad() = this.getHenvendelsesType()?.getSøknad()?.getVedtakstype()?.equals("GJENOPPTAK") ?: false
fun Behov.isNySoknad() = this.getHenvendelsesType()?.getSøknad()?.getVedtakstype()?.equals("NY") ?: false

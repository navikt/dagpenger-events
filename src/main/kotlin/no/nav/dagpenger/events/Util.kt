package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Behov

fun Behov.isSoknad() = this.getHenvendelsesType().getSøknad() != null
fun Behov.isEttersending() = this.getHenvendelsesType().getEttersending() != null
fun Behov.isAnnet() = this.getHenvendelsesType().getAnnet() != null

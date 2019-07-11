package no.nav.dagpenger.events

import no.nav.dagpenger.events.avro.Annet
import no.nav.dagpenger.events.avro.Behov
import no.nav.dagpenger.events.avro.Ettersending
import no.nav.dagpenger.events.avro.Søknad
import no.nav.dagpenger.events.avro.Vedtakstype
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertTrue

class BehovKtTest {

    @Test
    fun isSoknad() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Søknad.newBuilder().build()
        }.build()

        assertTrue(behov.isSoknad())
    }

    @Test
    fun isEttersending() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Ettersending.newBuilder().build()
        }.build()

        assertTrue(behov.isEttersending())
    }

    @Test
    fun isAnnet() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Annet.newBuilder().build()
        }.build()

        assertTrue(behov.isAnnet())
    }

    @Test
    fun hasBehandlendeEnhet() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            behandleneEnhet = UUID.randomUUID().toString()
        }.build()

        assertTrue(behov.hasBehandlendeEnhet())
    }

    @Test
    fun hasFagsakId() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            fagsakId = UUID.randomUUID().toString()
        }.build()

        assertTrue(behov.hasFagsakId())
    }

    @Test
    fun hasGsakId() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            gsaksakId = UUID.randomUUID().toString()
        }.build()

        assertTrue(behov.hasGsakId())
    }

    @Test
    fun hasHenvendelsesType() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Søknad.newBuilder().build()
        }.build()

        assertTrue(behov.hasHenvendelsesType())
    }

    @Test
    fun isGjenopptakSoknad() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Søknad.newBuilder().apply {
                vedtakstype = Vedtakstype.GJENOPPTAK
            }.build()
        }.build()

        assertTrue(behov.isGjenopptakSoknad())
    }

    @Test
    fun isNySoknad() {
        val behov = Behov.newBuilder().apply {
            behovId = UUID.randomUUID().toString()
            henvendelsesType = Søknad.newBuilder().apply {
                vedtakstype = Vedtakstype.NY_RETTIGHET
            }.build()
        }.build()

        assertTrue(behov.isNySoknad())
    }
}
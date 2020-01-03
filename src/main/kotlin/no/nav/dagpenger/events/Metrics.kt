package no.nav.dagpenger.events

import io.prometheus.client.Summary

val packetByteSize = Summary.build()
    .name("packet_size_bytes")
    .help("Packet size in bytes.").register()
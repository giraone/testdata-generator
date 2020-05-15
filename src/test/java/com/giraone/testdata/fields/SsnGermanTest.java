package com.giraone.testdata.fields;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SsnGermanTest {

    @Test
    void checksum() {
        assertThat(SsnGerman.checksum(1)).isEqualTo(1);
        assertThat(SsnGerman.checksum(11)).isEqualTo(2);
        assertThat(SsnGerman.checksum(12345)).isEqualTo(15);
    }

    @Test
    void calculateChecksumOfGermanSsn() {
        assertThat(SsnGerman.calculateChecksumOfGermanSsn("65180539W00")).isEqualTo(1);
    }

    @Test
    void build() {

        assertThat(SsnGerman.build("14", "Wagner", LocalDate.parse("1939-12-31"), "00"))
            .isEqualTo("14311239W008");
        assertThat(SsnGerman.build("14","Muster", LocalDate.parse("2000-01-01"), "00"))
            .isEqualTo("14010100M007");
        assertThat(SsnGerman.build("14","Ährens", LocalDate.parse("2000-01-01"), "00"))
            .isEqualTo("14010100A003");
    }
}
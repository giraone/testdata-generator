package com.giraone.testdata.fields;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SsnUsTest {

    @Test
    void build() {

        assertThat(SsnUs.build()).matches("[0-9]{3}-[0-9]{2}-[0-9]{4}");
    }
}
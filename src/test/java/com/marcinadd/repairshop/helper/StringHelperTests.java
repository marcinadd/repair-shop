package com.marcinadd.repairshop.helper;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringHelperTests {
    @Test
    public void whenReplaceChars_shouldReturnStringWithReplacedChars() {
        String s = "ABCDEF";
        assertThat(StringHelper.replaceCharsWithAsterisks(s, 1, 2), is("A***EF"));
    }

    @Test
    public void whenReplaceAndPreserveMoreCharsThanStringLength_shouldReturnPlainString() {
        String s = "sample";
        assertThat(StringHelper.replaceCharsWithAsterisks(s, 4, 4), is("sample"));
    }

    @Test
    public void whenPreserveZeroLetters_shouldReturnOnlyAsterisks() {
        String s = "sample";
        assertThat(StringHelper.replaceCharsWithAsterisks(s, 0, 0), is("******"));
    }
}

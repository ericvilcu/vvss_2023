package org.example.features.internet;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import org.example.steps.serenity.InternetSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
public class InternetStory {

    @Managed(uniqueSession = true, driver="chrome")
    public WebDriver webdriver;

    @Steps
    public InternetSteps anna;
    private static final String expectedAB="Also known as split testing. This is a way in which businesses are able to simultaneously test and learn different versions of a page to see which text and/or functionality works best towards a desired outcome (e.g. a user action such as a click-through).";

    @Test
    public void testAB() {
        anna.reset();
        anna.go_to_AB();
        assertEquals(expectedAB,anna.AB_TEXT());
    }

    @Test
    public void testAdr() {
        anna.reset();
        anna.go_to_adr();
        assertEquals(0,anna.adr_count());
        anna.adr_add();
        assertEquals(1,anna.adr_count());
        anna.adr_remove();
        assertEquals(0,anna.adr_count());
        for (int i=1;i<=20;++i) {
            anna.adr_add();
            assertEquals(i, anna.adr_count());
        }
        for (int i=19;i>=0;--i) {
            anna.adr_remove();
            assertEquals(i,anna.adr_count());
        }
    }

    @Test
    public void testDropdown() {
        anna.reset();
        anna.go_to_dropdown();
        assertEquals("", anna.dropdownState());
        anna.dropdownSelect(1);
        assertEquals("1", anna.dropdownState());
        anna.dropdownSelect(2);
        assertEquals("2", anna.dropdownState());
        anna.dropdownSelect(1);
        assertEquals("1", anna.dropdownState());
    }

    @Test
    public void testAd() {
        anna.reset();
        anna.gotoAd();
        if(!anna.adExists()) {
            anna.re_enable_ad();
            anna.reset();
            anna.gotoAd();
        }
        assertTrue(anna.adExists());
        anna.closeAd();
        assertFalse(anna.adExists());
    }

    @Test
    public void plimbare(){
        anna.reset();
        anna.go_to_AB();
        assertEquals(expectedAB,anna.AB_TEXT());
        anna.back();

        anna.go_to_adr();
        assertEquals(0,anna.adr_count());
        anna.adr_add();
        assertEquals(1,anna.adr_count());
        anna.adr_add();
        assertEquals(2,anna.adr_count());
        anna.adr_remove();
        assertEquals(1,anna.adr_count());
        anna.back();
        anna.go_to_adr();
        assertEquals(0,anna.adr_count());
        anna.back();

        anna.gotoAd();
        if(!anna.adExists()) {
            anna.re_enable_ad();
            anna.reset();
            anna.gotoAd();
        }
        assertTrue(anna.adExists());
        anna.closeAd();
        assertFalse(anna.adExists());
        anna.back();
        anna.gotoAd();
        assertFalse(anna.adExists());


        anna.back();
        anna.go_to_dropdown();
        assertEquals("", anna.dropdownState());
        anna.dropdownSelect(2);
        assertEquals("2", anna.dropdownState());
        anna.dropdownSelect(2);
        assertEquals("2", anna.dropdownState());
        anna.dropdownSelect(1);
        assertEquals("1", anna.dropdownState());

        anna.back();
        anna.go_to_dropdown();
        assertEquals("", anna.dropdownState());

        anna.back();
    }
}

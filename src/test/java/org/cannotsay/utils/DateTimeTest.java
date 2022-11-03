package org.cannotsay.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeTest {

    @Test
    void givenDescriptionWithoutStartDateWhenGetStartDateThenOK() {
        String endDate = DateTime.getEndDate("hellow storing message Dit is een test storing\\nmet CM en ZM storingen.\\u003cbr/\\u003eStarttijd: onbekend\\u0026nbsp;Eindtijd: onbekend\\u0026nbsp;");
        assertEquals(endDate, "");
    }

    @Test
    void givenDescriptionWhenGetStartDateThenOK() {
        String endDate = DateTime.getStartDate("Als gevolg van een storing kunt u mogelijk in enkele gebieden in de regio Stein niet bij ons inloggen via het ADSL-netwerk. Indien u telefonie of televisie via internet van ons afneemt dan kunnen deze diensten door de storing verstoord zijn. Onze excuses voor dit ongemak. Wij stellen alles in het werk om de storing zo spoedig mogelijk op te lossen.<br><br><br/>Starttijd: 2011-06-22 10:00&nbsp;Eindtijd: onbekend&nbsp;");
        assertEquals(endDate, "2011-06-22 10:00");
    }

    @Test
    void givenDescriptionWithoutEndDateWhenGetEndDateThenOK() {
        String endDate = DateTime.getEndDate("hellow storing message Dit is een test storing\\nmet CM en ZM storingen.\\u003cbr/\\u003eStarttijd: 2015-01-08 06:39\\u0026nbsp;Eindtijd: onbekend\\u0026nbsp;");
        assertEquals(endDate, "");
    }

    @Test
    void givenDescriptionWhenGetEndDateThenOK() {
        String endDate = DateTime.getEndDate("Als gevolg van een storing kunt u mogelijk in enkele gebieden in de regio Stein niet bij ons inloggen via het ADSL-netwerk. Indien u telefonie of televisie via internet van ons afneemt dan kunnen deze diensten door de storing verstoord zijn. Onze excuses voor dit ongemak. Wij stellen alles in het werk om de storing zo spoedig mogelijk op te lossen.<br><br><br/>Starttijd: 2011-06-22 10:00&nbsp;Eindtijd: 2012-06-22 10:00&nbsp;");
        assertEquals(endDate, "2012-06-22 10:00");
    }
}
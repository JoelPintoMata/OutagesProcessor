package org.cannotsay.outages.processor.util;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.cannotsay.outages.schema.Item;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OutagesProcessorTest {

    private static SparkSession spark;

    @BeforeAll
    public static void init() throws IllegalArgumentException {
        spark = SparkSession
                .builder()
                .appName("junit")
                .master("local[*]")
                .getOrCreate();
    }

    @AfterAll
    public static void close() throws IllegalArgumentException {
        spark.close();
    }

    @Test
    void givenDatasetWhenGetStartEndDateThenOk() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStartEndDate.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("startDate == '2011-06-22 10:00'").count(), 1);
        Assertions.assertEquals(resultDS.filter("endDate == 'onbekend'").count(), 1);
    }

    @Test
    void givenStartDateIsOnbekendWhenGetStatusThenUppercase() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStartDateOnbekend.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("startDate == 'Onbekend'").count(), 1);
    }

    @Test
    void givenEndDateIsOnbekendWhenGetStatusThenUppercase() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestEndDateOnbekend.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("endDate == 'Onbekend'").count(), 1);
    }

    @Test
    void givenItemEndDateOnbekendWhenGetStatusThenOkTest() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStatusActueel1.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("status == 'Actueel'").count(), 1);
    }

    @Test
    void givenItemEndDateFutureWhenGetStatusThenStatusActueelTest() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStatusActueel2.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("status == 'Actueel'").count(), 1);
    }

    @Test
    void givenItemStartDateFutureWhenGetStatusThenStatusGeplandTest() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStatusGepland.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("status == 'Gepland'").count(), 1);
    }

    @Test
    void givenItemEndDatePastWhenGetStatusThenStatusOpgelostTest() {
        Dataset<org.cannotsay.outages.model.Item> itemDS = spark.read()
                .schema(new Item().item)
                .json("./src/test/resources/outagesProcessorTestStatusOpgelost.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(org.cannotsay.outages.model.Item.class));

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        Dataset<org.cannotsay.outages.model.Item> resultDS = outagesProcessor.setStartEndDate(itemDS);
        resultDS = outagesProcessor.setStatus(resultDS);

        Assertions.assertEquals(resultDS.count(), 1);
        Assertions.assertEquals(resultDS.filter("status == 'Opgelost'").count(), 1);
    }
}
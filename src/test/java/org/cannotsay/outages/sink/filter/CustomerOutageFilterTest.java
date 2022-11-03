package org.cannotsay.outages.sink.filter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.cannotsay.outages.model.Item;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerOutageFilterTest {

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
    void givenItemLocationKPNWhenFilterThenCustomerTest() {
        Dataset<Item> itemDS = spark.read()
                .schema(new org.cannotsay.outages.schema.Item().item)
                .json("./src/test/resources/customerFilterTest.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(Item.class));

        CustomerOutageFilter customerOutageFilter = new CustomerOutageFilter();
        itemDS = itemDS.filter(customerOutageFilter.filter);
        assertEquals(itemDS.count(), 1);
    }

    @Test
    void givenItemLocationZMOHWhenFilterThenCustomerTest() {
        Dataset<Item> itemDS = spark.read()
                .schema(new org.cannotsay.outages.schema.Item().item)
                .json("./src/test/resources/businessFilterZMOHTest.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(Item.class));

        CustomerOutageFilter customerOutageFilter = new CustomerOutageFilter();
        itemDS = itemDS.filter(customerOutageFilter.filter);
        assertEquals(itemDS.count(), 0);
    }

    @Test
    void givenItemLocationZMSTWhenFilterThenCustomerTest() {
        Dataset<Item> itemDS = spark.read()
                .schema(new org.cannotsay.outages.schema.Item().item)
                .json("./src/test/resources/businessFilterZMSTTest.json")
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber")
                .as(Encoders.bean(Item.class));

        CustomerOutageFilter customerOutageFilter = new CustomerOutageFilter();
        itemDS = itemDS.filter(customerOutageFilter.filter);
        assertEquals(itemDS.count(), 0);
    }
}
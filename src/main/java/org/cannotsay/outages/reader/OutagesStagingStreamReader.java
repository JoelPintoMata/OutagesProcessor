package org.cannotsay.outages.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.cannotsay.outages.schema.Item;

/**
 * Outages spark stream reader
 */
public class OutagesStagingStreamReader {

    /**
     * Reads an input outages file into a stream
     * @param spark a spark session
     * @param path  a path to the source file
     * @return a dataset with read xml nodes
     */
    public Dataset<Row> read(SparkSession spark, String path) {
        return spark
                .readStream()
                .schema(new Item().item)
                .json(path)
//                sets the number of partitions by the number of cores -1
                .coalesce(spark.sparkContext().defaultParallelism())
                .withColumnRenamed("james:category", "category")
                .withColumnRenamed("james:expectedEndDate", "expectedEndDate")
                .withColumnRenamed("james:locations", "locations")
                .withColumnRenamed("james:postalCodes", "postalCodes")
                .withColumnRenamed("james:ticketNumber", "ticketNumber");
    }
}

package org.cannotsay.outages.reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.cannotsay.outages.schema.Item;

/**
 * Outages XML file spark reader
 */
public class OutagesRawXMLReader {

    /**
     * Reads an input outages XML file
     * @param spark a spark session
     * @param path  a path to the source file
     * @return a dataset with read xml nodes
     */
    public Dataset<Row> read(SparkSession spark, String path) {
        return spark
                .read()
                .schema(new Item().item)
                .format("xml")
                .option("rowTag", "item")
                .load(path)
//                sets the number of partitions by the number of cores -1
                .coalesce(spark.sparkContext().defaultParallelism());
    }
}

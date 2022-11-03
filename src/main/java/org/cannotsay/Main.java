package org.cannotsay;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.cannotsay.outages.model.Item;
import org.cannotsay.outages.processor.OutagesStreamProcessor;
import org.cannotsay.outages.reader.OutagesRawXMLReader;
import org.cannotsay.outages.reader.OutagesStagingStreamReader;
import org.cannotsay.outages.sink.OutagesSink;
import org.cannotsay.outages.writer.OutagesWriter;

import java.util.concurrent.TimeoutException;

public class Main {

    private static final String RAW_ZONE_PATH = "src/main/resources/raw";
    private static final String STAGING_ZONE_PATH = "src/main/resources/staging";

    public static void main(String[] args) throws TimeoutException, StreamingQueryException {

        // Set logging level
        Configurator.setRootLevel(Level.WARN);

        SparkSession spark = SparkSession
                .builder()
                .appName("XMLOutagesProcessor")
                .master("local[*]")
                .getOrCreate();

        OutagesRawXMLReader outagesRawXMLReader = new OutagesRawXMLReader();
        Dataset<Row> xmlOutagesRowDS = outagesRawXMLReader.read(spark, RAW_ZONE_PATH);

        OutagesWriter outagesStagingWriter = new OutagesWriter();
        outagesStagingWriter.write(xmlOutagesRowDS, STAGING_ZONE_PATH);

        OutagesStagingStreamReader outagesStagingStreamReader = new OutagesStagingStreamReader();
        Dataset<Row> rowItemOutagesDS = outagesStagingStreamReader.read(spark, STAGING_ZONE_PATH);

        OutagesStreamProcessor outagesStreamProcessor = new OutagesStreamProcessor();
        Dataset<Item> itemOutagesDS = outagesStreamProcessor.process(rowItemOutagesDS);

        OutagesSink outagesSink = new OutagesSink();

        itemOutagesDS
                .writeStream()
                .foreachBatch(outagesSink.sink)
//                 enabled checkpointing for fault-tolerance and at-least once guarantees
//                .option("checkpointLocation", "./checkpoint")
                .start()
                .awaitTermination();
    }
}
package org.cannotsay.outages.processor;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.cannotsay.outages.model.Item;
import org.cannotsay.outages.processor.util.OutagesProcessor;

/**
 * Outages Spark StreamReader data processor
 */
public class OutagesStreamProcessor {

    /**
     * Processes the contents of the outages stream
     * @param itemRowDS a dataset to process
     * @return a processed dataset
     */
    public Dataset<Item> process(Dataset<Row> itemRowDS) {
        Dataset<Item> itemDS = encodeToItem(itemRowDS);

        OutagesProcessor outagesProcessor = new OutagesProcessor();
        itemDS = outagesProcessor.setStartEndDate(itemDS);
        itemDS = outagesProcessor.setStatus(itemDS);

        return itemDS;
    }

    private Dataset<Item> encodeToItem(Dataset<Row> itemDS) {
        return itemDS
                .as(Encoders.bean(Item.class));
    }
}
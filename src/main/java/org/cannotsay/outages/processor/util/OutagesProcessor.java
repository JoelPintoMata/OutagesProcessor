package org.cannotsay.outages.processor.util;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.cannotsay.outages.model.Item;
import org.cannotsay.utils.DateTime;

import java.time.Instant;

public class OutagesProcessor {

    public Dataset<Item> setStartEndDate(Dataset<Item> itemDS) {
        return itemDS
                .map((MapFunction<Item, Item>) item -> {
                    item.setStartDate(DateTime.getStartDate(item.getDescription()));
                    item.setEndDate(DateTime.getEndDate(item.getDescription()));
                    return item;
                }, Encoders.bean(Item.class));
    }

    public Dataset<Item> setStatus(Dataset<Item> itemDS) {
        return itemDS
                .map((MapFunction<Item, Item>) item -> {
                    String status = null;
                    if (item.getEndDate().equals("onbekend")
                            || (DateTime.getMiliseconds(item.getStartDate()) < Instant.now().toEpochMilli() && DateTime.getMiliseconds(item.getEndDate()) > Instant.now().toEpochMilli())) {
                        status = "Actueel";
                    } else if (DateTime.getMiliseconds(item.getStartDate()) > Instant.now().toEpochMilli()) {
                        status = "Gepland";
                    } else if (DateTime.getMiliseconds(item.getEndDate()) < Instant.now().toEpochMilli()) {
                        status = "Opgelost";
                    }
                    // if either the beginning (Starttijd:) or the end (Eindtijd:) is "onbekend" (unknown) then the mapping startDate and/or endDate will need to have the value "Onbekend"
                    if (item.getStartDate().equals("onbekend")) {
                        item.setStartDate("Onbekend");
                    }
                    if (item.getEndDate().equals("onbekend")) {
                        item.setEndDate("Onbekend");
                    }
                    item.setStatus(status);
                    return item;
                }, Encoders.bean(Item.class));
    }
}

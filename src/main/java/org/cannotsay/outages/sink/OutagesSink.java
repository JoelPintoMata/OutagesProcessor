package org.cannotsay.outages.sink;

import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.cannotsay.outages.sink.filter.BusinessOutageFilter;
import org.cannotsay.outages.sink.filter.CustomerOutageFilter;
import org.cannotsay.outages.model.Item;
import org.cannotsay.outages.writer.OutagesWriter;

public class OutagesSink {

    private static final String TRUSTED_BUSINESS_ZONE_PATH = "src/main/resources/trusted/business_outages";
    private static final String TRUSTED_CUSTOMER_ZONE_PATH = "src/main/resources/trusted/customer_outages";

    private final BusinessOutageFilter businessOutageFilter = new BusinessOutageFilter();
    private final CustomerOutageFilter customerOutageFilter = new CustomerOutageFilter();

    public final VoidFunction2<Dataset<Item>, Long> sink = (itemDataset, aLong) -> {
        Dataset<Row> itemsRowDS = itemDataset
                .filter(businessOutageFilter.filter)
                .drop("locations", "ticket_number", "category", "link")
                .select("endDate", "title", "postalCodes", "status", "startDate", "description");
        OutagesWriter outagesWriter = new OutagesWriter();
        outagesWriter.write(itemsRowDS, TRUSTED_BUSINESS_ZONE_PATH);

        itemsRowDS = itemDataset
                .filter(customerOutageFilter.filter)
                .drop("locations", "ticket_number", "category", "link")
                .select("endDate", "title", "postalCodes", "status", "startDate", "description");
        outagesWriter.write(itemsRowDS, TRUSTED_CUSTOMER_ZONE_PATH);
    };
}



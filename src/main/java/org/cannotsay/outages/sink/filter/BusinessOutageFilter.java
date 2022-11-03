package org.cannotsay.outages.sink.filter;

import org.apache.spark.api.java.function.FilterFunction;
import org.cannotsay.outages.model.Item;

public class BusinessOutageFilter {
    public final FilterFunction<Item> filter = item -> item.getLocations() != null
            && (item.getLocations().contains("ZMST")
            || item.getLocations().contains("ZMOH"));
}

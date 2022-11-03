package org.cannotsay.outages.schema;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.Serializable;

/**
 * Item schema
 */
public class Item implements Serializable {

    public final StructType item = new StructType(
            new StructField[]{
                    new StructField("description", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("james:category", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("james:expectedEndDate", DataTypes.TimestampType, true, Metadata.empty()),
                    new StructField("james:locations", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("james:postalCodes", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("james:ticketNumber", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("link", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("title", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("status", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("startDate", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("endDate", DataTypes.StringType, true, Metadata.empty()),
            });
}
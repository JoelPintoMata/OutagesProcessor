package org.cannotsay.outages.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

/**
 * Outages writer
 */
public class OutagesWriter {

    /**
     * Writes dataset contents to a given path
     * @param outagesDS a dataset
     * @param path      a path where to write
     */
    public void write(Dataset<Row> outagesDS, String path) {
        outagesDS
                .write()
                .mode(SaveMode.Overwrite)
                .json(path);
    }
}

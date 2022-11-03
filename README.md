# Meetup trends Stream

## Tech stack

- Java 1.8
- Spark 3.3.0
- Kryo serializers

## How to run
__(make sure to these with Java 8, check with `mvn -v`)__

Run the tests:
```shell
mvn clean test
```

Build the application package
```shell
mvn clean package
```

Next, submit your job
```shell
mvn clean package && spark-submit --class org.cannotsay.Main --master local[*] --packages com.databricks:spark-xml_2.12:0.15.0 --conf spark.serializer=org.apache.spark.serializer.KryoSerializer ./target/XMLOutagesSparkReader-1.0-SNAPSHOT.jar
```

## Configurations

__Input path__

Add your json files under the `raw` zone at : `src/java/resources`.

### Zones

- Raw, where the raw inputs live
- Staging, where the processed/in-process data lives
- Trusted, where the final/trusted/processed data lives

## About

- Use Spark `OutagesRawXMLReader` to read the XML file 
- Use Spark `OutagesWriter` to write contents to the `staing` area in `json` format. 
  - Using json for its readability, should use `parquet` instead
- Use Spark `OutagesStagingStreamReader` to read `json` contents to a Spark stream.
- Process data with `OutagesStreamProcessor`
- Start stream querying
- Per each stream batch call `OutageSink` for filtering and driving contents to wither `business` or `customer` `trust` areas.

## Future improvements

- Parse `postal codes` as a list of elements
- Parse `locations` as a list of elements
- Replace formats from `json` to `parquet`

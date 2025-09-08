# Java Ethereum Exporter

A command-line tool to export transaction history of an Ethereum address to a CSV file using Alchemy API.

## Prerequisites

- Java 21
- Maven

## Build

To build the project, run the following command:

```bash
mvn package
```

This will create a JAR file with all the dependencies in the `target` directory.

## Usage

To export the transaction history, run the following command:

```bash
java -jar target/java-ethereum-exporter-1.0-SNAPSHOT-jar-with-dependencies.jar <ETHEREUM_ADDRESS> <YOUR_ALCHEMY_API_KEY>
```

Replace `<ETHEREUM_ADDRESS>` with the Ethereum address you want to export and `<YOUR_ALCHEMY_API_KEY>` with your Alchemy API key. You can get a free API key from [Alchemy](https://www.alchemy.com/).

For example:

```bash
java -jar target/java-ethereum-exporter-1.0-SNAPSHOT-jar-with-dependencies.jar 0xa39b189482f984388a34460636fea9eb181ad1a6 <YOUR_ALCHEMY_API_KEY>
```

This will create a CSV file named `<ETHEREUM_ADDRESS>_transactions.csv` in the root directory of the project.

## Dependencies

- [Google Gson](https://github.com/google/gson): For parsing JSON data from the Alchemy API.
- [OkHttp](https://square.github.io/okhttp/): For making HTTP requests to the Alchemy API.
- [OpenCSV](http://opencsv.sourceforge.net/): For writing data to the CSV file.
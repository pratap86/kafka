# kafka
Apache Kafka with Spring Boot
#### Kafka Introduction & Internals
- Kafka has four APIs ProducerAPI, ConsumerAPI, ConnectAPI and StreamsAPI
#### Building Enterprise Standard Kafka Clients using Spring-kafka/SpringBoot
#### Resilient Kafka client Applications using Error-Handling/Retry/Recovery
#### Unit/Integration JUnit test cases

#### Basic Kafka Commands
|Commands|Description|
|---|---|
|`.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties`|Start up the Zookeeper.|
|`.\bin\windows\kafka-server-start.bat .\config\server.properties`|Start up the Kafka Broker|
|`.\bin\windows\kafka-topics.bat --create --topic test-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 4`|Create topic|
|`.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic`|instantiate a Console Producer without key|
|`.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning`|instantiate a Console Consumer without key|
|`.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic --property "key.separator=-" --property "parse.key=true"`|instantiate a Console Producer without with key|
|` .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning -property "key.separator= - " --property "print.key=true"`|instantiate a Console Consumer with key|

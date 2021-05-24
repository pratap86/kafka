# kafka
Apache Kafka with Spring Boot
#### Kafka Introduction & Internals
- Kafka has four APIs ProducerAPI, ConsumerAPI, ConnectAPI and StreamsAPI
#### Building Enterprise Standard Kafka Clients using Spring-kafka/SpringBoot
#### Resilient Kafka client Applications using Error-Handling/Retry/Recovery
#### Unit/Integration JUnit test cases

# Basic Kafka Commands

#### Start Zookeeper and Kafka Broker
- Start up the Zookeeper.
```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
- Start up the Kafka Broker
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
#### How to create a topic ?
```
.\bin\windows\kafka-topics.bat --create --topic test-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 4
```
### How to instantiate a Console Producer?
#### Without Key
```
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic
```
#### With Key
```
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic --property "key.separator=-" --property "parse.key=true"
```
### How to instantiate a Console Consumer?
#### Without Key
```
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```
#### With Key
```
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning -property "key.separator= - " --property "print.key=true"
```
#### With Consumer Group
```
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --group <group-name>
```

### Advanced Kafka CLI operations:

#### List the topics in a cluster
``` ruby
.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --list
```
#### Describe topic
- The below command can be used to describe all the topics.
``` ruby
.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --describe
```
- The below command can be used to describe a specific topic.
``` ruby
.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --describe --topic <topic-name>
```

#### Delete a topic
``` ruby
.\bin\windows\kafka-topics.bat --zookeeper localhost:2181 --delete --topic <topic-name>
```
#### How to view consumer groups
``` ruby
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
#### Consumer Groups and their Offset
``` ruby
.\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group console-consumer-27773
```



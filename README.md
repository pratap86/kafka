# Kafka - As a Distributed Streaming System
#### What is Distributed system?
<p>Distributed systems are a collection of systems working together to deliver a value.</p>

#### What is a Messaging System?
<p>A Messaging System is responsible for transferring data from one application to another, so the applications can focus on data, but not worry about how to share it. Distributed messaging is based on the concept of reliable message queuing. Messages are queued asynchronously between client applications and messaging system. Two types of messaging patterns are available − one is point to point and the other is publish-subscribe (pub-sub) messaging system. Most of the messaging patterns follow pub-sub.</p>

#### Point to Point Messaging System
<p>In a point-to-point system, messages are persisted in a queue. One or more consumers can consume the messages in the queue, but a particular message can be consumed by a maximum of one consumer only. Once a consumer reads a message in the queue, it disappears from that queue. The typical example of this system is an Order Processing System, where each order will be processed by one Order Processor, but Multiple Order Processors can work as well at the same time.</p>

#### Publish-Subscribe Messaging System
<p>In the publish-subscribe system, messages are persisted in a topic. Unlike point-to-point system, consumers can subscribe to one or more topic and consume all the messages in that topic. In the Publish-Subscribe system, message producers are called publishers and message consumers are called subscribers. A real-life example is Dish TV, which publishes different channels like sports, movies, music, etc., and anyone can subscribe to their own set of channels and get them whenever their subscribed channels are available.</p>

#### What is Kafka?
<p>Kafka is written in Scala and Java. Apache Kafka is publish-subscribe based fault tolerant messaging system. It is fast, scalable and distributed by design.</p>
<p>Apache Kafka is a distributed publish-subscribe messaging system and a robust queue that can handle a high volume of data and enables you to pass messages from one end-point to another. Kafka is suitable for both offline and online message consumption. Kafka messages are persisted on the disk and replicated within the cluster to prevent data loss. Kafka is built on top of the ZooKeeper synchronization service. It integrates very well with Apache Storm and Spark for real-time streaming data analysis.</p>

#### Benifits of Kafka
|Key|Description|
|---|---|
|`Reliability`|Kafka is distributed, partitioned, replicated and fault tolerance.|
|`Scalability`|Kafka messaging system scales easily without down time.|
|`Durability`|Kafka uses Distributed commit log which means messages persists on disk as fast as possible, hence it is durable.|
|`Performance`|Kafka has high throughput for both publishing and subscribing messages. It maintains stable performance even many TB of messages are stored.|

### Apache Kafka with Spring Boot

#### Kafka Introduction & Internals
- Kafka has four APIs ProducerAPI, ConsumerAPI, ConnectAPI and StreamsAPI
#### Building Enterprise Standard Kafka Clients using Spring-kafka/SpringBoot
#### Resilient Kafka client Applications using Error-Handling/Retry/Recovery
#### Unit/Integration JUnit test cases

### Basic Kafka Commands

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
### Kafka Behind the Scenes
#### KafkaTemplate.send()



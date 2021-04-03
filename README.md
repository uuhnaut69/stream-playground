# Stream Playground

Stream playground demo

## Prerequisites
- Java 11 +
- Docker
- Docker-compose

## Demo projects

| Demo | Link |
| --- | --- |
| Enriched data via Kafka join operator using Java, Spring Kafka Streams| [Link](https://github.com/uuhnaut69/stream-playground/tree/main/aggregator/java-spring-kafka-streams-aggregator)|
| Enriched data via Kafka join operator using Java, Spring Cloud Streams| [Link](https://github.com/uuhnaut69/stream-playground/tree/main/aggregator/java-spring-cloud-streams-aggregator)|
| Enriched data via Kafka join operator using Kotlin, String Kafka Streams| [Link](https://github.com/uuhnaut69/stream-playground/tree/main/aggregator/kotlin-spring-kafka-streams-aggregator)|
| Enriched data via Kafka join operator using Kotlin, Spring Cloud Streams| [Link](https://github.com/uuhnaut69/stream-playground/tree/main/aggregator/kotlin-spring-cloud-streams-aggregator)|

## Get Started

### 1. Start env

```shell
docker-compose up -d
```

### 2. Build projects
```shell
sh build-all.sh
```
### 3. Start api project

- Start api project to create some data (2 option version: Java-Javer, Kotlin-Kover) 

### 4. Create connectors

```shell
sh create-connectors.sh
```

### 5. Choose demo via link above to start
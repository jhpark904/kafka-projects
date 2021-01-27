# Kafka Producers & Consumers

## Project Overview

Kafka Producers & Consumers is a series of producers and consumers created with Python, Node.js, and Java. I worked on this project to gain a better understanding of Apache Kafka. Kafka Producers & Consumers lets users to produce and consume messages in a down-resistance and reliable Apache Kafka cluster created with both a replication factor and a partition greater than 1.

## Project Demo

[See Project](https://www.youtube.com/watch?v=DKHE9M59lEk)


## Scope of Functionality

The application supports:
 - replication of messages produced into multiple partitions
 - a Java kafka producer that sends formatted messages
 - a Java kafka consumer that consumes messages with auto commit
 - a Java kafka consumer that consumes messages with manual commit
 - a Java kafka consumer that consumes messages from specific partitions
 - a Node.js kafka producer that unlimitedly sends out randomly generated IDs
 - a Node.js kafka consumer that consumes the android IDs
 - a Python kafka producer that sends a batch of 100 fake profiles with features including geolocation, residence, SSN, birthday, email, website url, etc based on user input
 - a Python kafka consumer that reads fake profiles

The application does not support:
 - aesthetically pleasing frontend

## Technologies Used
 
 - Apache Kafka: for creating consumers and providing communication between producers and consumers
 - Java & Node.js & Python: for developing producers and consumers

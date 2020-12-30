from kafka import KafkaConsumer

#create consumer object
consumer = KafkaConsumer(
    'profiles',
    bootstrap_servers=['localhost:9092', 'localhost:9093', 'localhost:9094'],
    group_id='profiles-consumer-group'
)

#read messages
for message in consumer:
    print(message)
import time
from kafka import KafkaProducer
from faker import Faker

faker = Faker()

producer = KafkaProducer(bootstrap_servers=['localhost:9092', 'localhost:9093', 'localhost:9094'])

#generate 100 fake profiles until the user stops
while True:
    inp = input("Generate fake profile [Y / N]: ")

    if inp.lower() == 'n':
        break

    # send 100 fake profiles
    for _ in range(100):
        profile = faker.profile()
        producer.send('profiles', str(faker.profile()).encode('utf-8'))
        print(profile)

#add delay to since message is received asynchronously by the consumer
time.sleep(20)
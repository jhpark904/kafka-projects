const { Kafka } = require('kafkajs')
const Chance = require('chance')

const chance = new Chance()
 
const kafka = new Kafka({
  clientId: 'producer1',
  brokers: ['localhost:9092', 'localhost:9093', 'localhost:9094']
})
 
const producer = kafka.producer()
const topic = 'android-ids'
 
//produce messages and log
const produceMessage = async () => {
    //generate random android_id
    const value = chance.android_id()
    console.log(value)
    try {
        await producer.send({
            topic,
            messages: [
                { value },
            ],
        })

    } catch (error) {
        console.log(error)
    }
}

const run = async () => {
  // Producing
  await producer.connect()

  // send message every sec
  setInterval(produceMessage, 1000)
 
}
 
run().catch(console.error)
const { Kafka } = require('kafkajs')
 
const kafka = new Kafka({
  clientId: 'consumer1',
  brokers: ['localhost:9092', 'localhost:9093', 'localhost:9094']
})
 
//make sure to specify groupId when creating consumer object
const consumer = kafka.consumer({ groupId: 'group1' })
const topic = 'android-ids'
 
const run = async () => {
  // Consuming
  await consumer.connect()
  //read messages from beginning
  await consumer.subscribe({ topic, fromBeginning: true })
 
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log({
        partition,
        offset: message.offset,
        value: message.value.toString(),
      })
    },
  })
 
}
 
run().catch(console.error)
package com.a35ksyd.kafka.demoproducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import com.a35ksyd.kafka.model.DemoModel;

/**
 * A Demo producer which will post DemoModel to Queue with following fields
 * variables protected int val1; protected int val2; protected String op;
 *
 */
@Service
public class SimpleProducer {

	protected Producer<String, DemoModel> producer;
	protected String topicName = "demo";

	public SimpleProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.50.205:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);

		try {
			props.put("key.serializer", Class.forName("org.apache.kafka.common.serialization.StringSerializer"));
			props.put("value.serializer", Class.forName("com.a35ksyd.kafka.serializer.DemoModelSerializer"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		producer = new KafkaProducer<String, DemoModel>(props);
	}

	public void sendModelToKafkaMessage(DemoModel currentModel) {
		producer.send(new ProducerRecord<String, DemoModel>(topicName, "1", currentModel));

	}

	public void closeProducer() {
		if (producer != null) {
			producer.close();

		}
	}

}
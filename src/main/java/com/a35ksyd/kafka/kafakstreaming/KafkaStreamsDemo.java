package com.a35ksyd.kafka.kafakstreaming;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import com.a35ksyd.kafka.model.DemoModel;
import com.a35ksyd.kafka.serializer.DemoModelDeSerializer;
import com.a35ksyd.kafka.serializer.DemoModelSerializer;

public class KafkaStreamsDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KafkaStreamsDemo");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.50.205:9092");
        StreamsConfig streamingConfig = new StreamsConfig(props);
        Serde<String> stringSerde = Serdes.String();
        Serde<DemoModel> demoModelSerde = Serdes.serdeFrom(new DemoModelSerializer(), new DemoModelDeSerializer());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, DemoModel> firstStream = builder.stream("demo",
                Consumed.with(stringSerde, demoModelSerde));

        firstStream.foreach((key, value) -> System.out.println("Stream value ==> " + value));

        KStream<String, DemoModel> calculationStream = firstStream.map((key, value) -> {
            int result = 0;
            if (value != null) {
                switch (value.op().toUpperCase()) {
                    case "+":
                        result = value.val1() + value.val2();
                        break;
                    case "-":
                        result = value.val1() - value.val2();
                        break;
                    case "*":
                        result = value.val1() * value.val2();
                        break;
                    case "/":
                        result = value.val1() / value.val2();
                        break;
                }
                System.out.println(value.val1() + " " + value.op() + " " + value.val2() + "=" + result);
            }
            return new KeyValue<>(key, new DemoModel(result, result, "answer"));
        });

        calculationStream.to("calculated-results", Produced.with(stringSerde, demoModelSerde));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamingConfig);
        kafkaStreams.start();
        try {
            Thread.sleep(35000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kafkaStreams.close();
    }
}
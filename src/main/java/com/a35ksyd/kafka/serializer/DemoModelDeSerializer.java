package com.a35ksyd.kafka.serializer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;

import com.a35ksyd.kafka.model.DemoModel;

public class DemoModelDeSerializer implements Deserializer<DemoModel> {

    private Schema schema;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

        try {
        	File schemaFile = new File("DataModelSchema.avsc");
			this.schema = new Schema.Parser().parse(schemaFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @Override
    public DemoModel deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(data), null);
        try {
            GenericRecord record = datumReader.read(null, decoder);
            return new DemoModel(
                    (int) record.get("val1"),
                    (int) record.get("val2"),
                    (String) record.get("op")
            );
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing DemoModel", e);
        }
    }

    @Override
    public void close() {
    }
}
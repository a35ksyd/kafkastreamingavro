package com.a35ksyd.kafka.serializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.serialization.Serializer;

import com.a35ksyd.kafka.model.DemoModel;

public class DemoModelSerializer implements Serializer<DemoModel> {

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
    public byte[] serialize(String topic, DemoModel data) {
        if (data == null) {
            return null;
        }

        GenericRecord record = new GenericData.Record(schema);
        record.put("val1", data.val1());
        record.put("val2", data.val2());
        record.put("op", data.op());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);

        try {
            writer.write(record, encoder);
            encoder.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error serializing DemoModel", e);
        }
    }

    @Override
    public void close() {
        // No resources to close in this simple example
    }
}
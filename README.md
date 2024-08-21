## Example code for kafka streaming 

This example showcases the integration of a Spring Boot RESTful service with Apache Kafka and Kafka Streams to perform simple calculations.

**Functionality**

1. **RESTful Service:**
   - A Spring Boot endpoint (`http://localhost:8080/demo`) receives JSON messages containing two integer values (`val1`, `val2`) and an operator (`op`: +, -, *, /).

2. **Kafka Producer:**
   - The RESTful service publishes the received JSON message to a Kafka topic named "demo".

3. **Kafka Streams Processing:**
   - The first stream process consumes messages from the "demo" topic and simply logs the received data.
   - The second stream process consumes the same messages, performs the specified calculation, and produces a new message to a topic named "calculated-results".
   - The new message contains the calculated result as both `val1` and `val2`, with the `op` set to "Answer".

**Note:**

- Custom serializer and deserializer classes (`DemoModelSerializer`, `DemoModelDeSerializer`) are used to handle the `DemoModel` object during Kafka communication.

**Sample Output**

```bash
{
	"val1": 122,
	"val2": 25,
	"op": "+"
}

Stream value ==> DemoModel [val1=122, val2=25, op=+]
...
122 + 25 = 147

{
	"val1": 122,
	"val2": 25,
	"op": "-"
}

Stream value ==> DemoModel [val1=122, val2=25, op=-]
...
122 - 25 = 97
```

**Explanation**

1. The Spring Boot RESTful service provides an endpoint to receive calculation requests.
2. Upon receiving a request, it sends the data to a Kafka topic.
3. Kafka Streams processes the data, first logging the original message and then performing the calculation.
4. The calculated result is packaged into a new `DemoModel` object and sent to another Kafka topic.
5. This demo illustrates a basic data flow using Kafka and Kafka Streams for simple computations. 

**Remember:**

- Make sure to implement the `DemoModelSerializer` and `DemoModelDeSerializer` classes appropriately to handle the serialization and deserialization of your `DemoModel` objects.

Feel free to ask if you have any specific questions or modifications you'd like to make to the demo! 

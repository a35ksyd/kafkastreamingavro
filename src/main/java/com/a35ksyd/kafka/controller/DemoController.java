package com.a35ksyd.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.a35ksyd.kafka.demoproducer.SimpleProducer;
import com.a35ksyd.kafka.model.DemoModel;
@RestController
public class DemoController {
	 @Autowired
	  private SimpleProducer producer;
	 
	@RequestMapping(path = "/demo",method = RequestMethod.POST, produces =  MediaType.APPLICATION_PROBLEM_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public void demo(@RequestBody DemoModel model)
	{
		producer.sendModelToKafkaMessage(model);
	}
	
	
	

}

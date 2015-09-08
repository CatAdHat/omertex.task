package com.omertex.task.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.service.RepositoryTopicService;

public class InquiryDeserializer extends JsonDeserializer<Inquiry>
{
    RepositoryTopicService topicService;


    @Autowired
    public InquiryDeserializer (RepositoryTopicService topicService)
    {
	this.topicService = topicService;
    }


    @Override
    public Inquiry deserialize (JsonParser jsonParser, DeserializationContext arg1)
	    throws IOException, JsonProcessingException
    {
	ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
	Long id = null;
        if ( node.get ("id") != null)
	    id = node.get ("id").asLong ();
        String description = node.get ("description").asText ();
        String customer = node.get ("customer").asText ();
	List<InquiryAttribute> inquiryAttributes = new ArrayList<InquiryAttribute> (0);
	if (node.get ("inquiryAttributes") != null)
	{
	    Iterator<JsonNode> attributes = node.get ("inquiryAttributes").elements ();
	    while (attributes.hasNext ())
	    {
		JsonNode attr = attributes.next ();
		inquiryAttributes.add (InquiryAttribute.getBuilder ().name (attr.get ("name").asText ())
			.value (attr.get ("value").asText ()).build ());
	    }
	}
	Topic topic = topicService.getTopic (node.get ("topic").get ("id").asLong ());

	return Inquiry.getBuilder ().id (id).customer (customer).description (description)
		.inquiryAttributes (inquiryAttributes).topic (topic).build ();
    }

}

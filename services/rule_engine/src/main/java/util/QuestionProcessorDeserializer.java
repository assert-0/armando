package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.ruleEngine.util.Log;

import util.processors.*;


public class QuestionProcessorDeserializer extends JsonDeserializer<QuestionProcessor> {
    @Override
    public QuestionProcessor deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        switch (node.get("name").asText()) {
            case BasicQuestionProcessor.PROCESSOR_NAME:
                return new BasicQuestionProcessor();
            case TemplateQuestionProcessor.PROCESSOR_NAME:
                return new TemplateQuestionProcessor(node.get("className").asText());
            default:
                return null;
        } 
    }
}

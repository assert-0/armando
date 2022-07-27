package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import util.processors.*;


public class QuestionProcessorDeserializer extends JsonDeserializer<QuestionProcessor> {
    @Override
    public QuestionProcessor deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        switch (node.get("name").asText()) {
            case BasicQuestionProcessor.PROCESSOR_NAME:
                return new BasicQuestionProcessor();
            case TemplateQuestionProcessor.PROCESSOR_NAME:
                return new TemplateQuestionProcessor(node.get("className").asText());
            default:
                throw new RuntimeException("Unable to deserialize QuestionProcessor instance");
        } 
    }
}

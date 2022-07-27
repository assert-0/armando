package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import util.processors.*;


public class QuestionProcessorSerializer extends JsonSerializer<QuestionProcessor> {
    @Override
    public void serialize(QuestionProcessor value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        
    }
}

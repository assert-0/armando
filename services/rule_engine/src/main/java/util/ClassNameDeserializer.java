package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.ruleEngine.util.Log;


public class ClassNameDeserializer<T> extends JsonDeserializer<T> {
    @Override
    public T deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.readValueAsTree();
        Log.warn(node.toString());
        var className = node.get("ClassName").asText();
        Class<T> objectClass;
        try {
            objectClass = (Class<T>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
        return context.readTreeAsValue(node, objectClass);
    }
}

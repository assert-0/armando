package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;


public class ClassNameDeserializer<T> extends JsonDeserializer<T> {
    private static final String NAME = "@class";

    @Override
    public T deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.readValueAsTree();
        var className = node.get(NAME).asText();
        try {
            Class<T> objectClass = (Class<T>)Class.forName(className);
            for (var constructor : objectClass.getConstructors()) {
                var values = new LinkedList<Object>();
                for (var param : constructor.getParameters()) {
                    values.add(context.readTreeAsValue(node.get(param.getName()), param.getType()));
                }
                return (T)constructor.newInstance(values.toArray(new Object[0]));
            }
        } catch (ClassNotFoundException ignored) {
            return null;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException ignored) {
            return null;
        }
        return null;
    }
}

package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mindsmiths.ruleEngine.util.Log;

import org.apache.commons.lang.StringUtils;


public class ClassNameSerializer<T> extends JsonSerializer<T> {
    private static final String NAME = "@class";

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        Class<?> cls = value.getClass();
        gen.writeStringField(NAME, value.getClass().getName());
        while (!cls.equals(Object.class)) {
            var fields = cls.getDeclaredFields();
            for (var field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) != 0) continue;
                var methodName = "get" + StringUtils.capitalize(field.getName());
                try {
                    var method = value.getClass().getMethod(methodName);
                    var val = method.invoke(value);
                    gen.writeObjectField(field.getName(), val);
                } catch (NoSuchMethodException | SecurityException ignored) {
                    throw new RuntimeException("Unable to find method: " + methodName);
                } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException ignored) {
                    throw new RuntimeException("Unable to invoke method: " + methodName);
                }
            }
            cls = cls.getSuperclass();
        }
        gen.writeEndObject();
    }
}

package util;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.TypeDeserializerBase;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.jsontype.impl.TypeSerializerBase;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class SuperCoolTypeResolver extends StdTypeResolverBuilder {
    public static class SuperCoolTypeIdResolver extends TypeIdResolverBase {
        private JavaType superType;

        public SuperCoolTypeIdResolver(JavaType baseType, TypeFactory typeFactory) {
            super(baseType, typeFactory);
        }
    
        @Override
        public void init(JavaType baseType) {
            superType = baseType;
        }
    
        @Override
        public Id getMechanism() {
            return Id.CLASS;
        }
    
        @Override
        public String idFromValue(Object value) {
            return value.getClass().getName();
        }
    
        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return suggestedType.getName();
        }
    
        @Override
        public JavaType typeFromId(DatabindContext context, String id)  throws IOException {
            try {
                var subType = Class.forName(id);
                return context.constructSpecializedType(superType, subType);
            } catch (ClassNotFoundException ignored) {
                throw new RuntimeException("Unable to find class");
            }
        }    
    }

    public static class SuperCoolTypeSerializer extends TypeSerializerBase {
        public SuperCoolTypeSerializer(TypeIdResolver idRes, BeanProperty property) {
            super(idRes, property);
        }

        @Override
        public As getTypeInclusion() {
            return As.PROPERTY;
        }

        @Override
        public TypeSerializer forProperty(BeanProperty prop) {
            return this;
        }
    }

    public static class SuperCoolTypeDeserializer extends TypeDeserializerBase {
        private Collection<NamedType> subtypes;
        
        public SuperCoolTypeDeserializer(JavaType baseType, Collection<NamedType> subtypes, TypeIdResolver idRes, String typePropertyName,
                boolean typeIdVisible, JavaType defaultImpl) {
            super(baseType, idRes, typePropertyName, typeIdVisible, defaultImpl);
            this.subtypes = subtypes;
        }

        @Override
        public TypeDeserializer forProperty(BeanProperty prop) {
            return this;
        }

        @Override
        public As getTypeInclusion() {
            return As.PROPERTY;
        }

        @Override
        public Object deserializeTypedFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
            return deserializeTypedFromAny(p, ctxt);
        }

        @Override
        public Object deserializeTypedFromArray(JsonParser p, DeserializationContext ctxt) throws IOException {
            return deserializeTypedFromAny(p, ctxt);
        }

        @Override
        public Object deserializeTypedFromScalar(JsonParser p, DeserializationContext ctxt) throws IOException {
            return deserializeTypedFromAny(p, ctxt);
        }

        @Override
        public Object deserializeTypedFromAny(JsonParser p, DeserializationContext ctxt) throws IOException {
            throw new RuntimeException(p.currentToken().asString());
        }

    }

    @Override
    public TypeSerializer buildTypeSerializer(SerializationConfig config,
            JavaType baseType, Collection<NamedType> subtypes)
    {
        var superCoolResolver = new SuperCoolTypeIdResolver(baseType, TypeFactory.defaultInstance());
        return new SuperCoolTypeSerializer(superCoolResolver, null);
    }

    @Override
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig config,
            JavaType baseType, Collection<NamedType> subtypes)
    {
        var superCoolResolver = new SuperCoolTypeIdResolver(baseType, TypeFactory.defaultInstance());
        return new SuperCoolTypeDeserializer(baseType, subtypes, superCoolResolver, "", true, TypeFactory.unknownType());
    }
}

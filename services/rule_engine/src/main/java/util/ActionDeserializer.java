package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import util.actions.*;


public class ActionDeserializer extends JsonDeserializer<Action> {
    @Override
    public Action deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        switch (node.get("name").asText()) {
            case CallAgentAction.ACTION_NAME:
                return new CallAgentAction(node.get("agentId").asText());
            case SendMessageAction.ACTION_NAME:
                return new SendMessageAction(node.get("message").asText());
            default:
                throw new RuntimeException("Unable to deserialize Action instance");
        }
    }
}

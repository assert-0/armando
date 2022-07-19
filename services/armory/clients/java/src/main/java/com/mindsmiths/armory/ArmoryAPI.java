package com.mindsmiths.armory;

import com.mindsmiths.armory.templates.BaseTemplate;
import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.messaging.Messaging;

public class ArmoryAPI {

    private static final String topic = Messaging.getInputTopicName("armory");


    public static void updateTemplate(String connectionId, String referenceId, BaseTemplate template) {
        updateTemplate(connectionId, referenceId, template.getClass().getSimpleName(), template);
    }

    public static void updateTemplate(String connectionId, String referenceId, String templateName, Object templateData) {
        UpdateTemplatePayload payload = new UpdateTemplatePayload(connectionId, referenceId, templateName, templateData);
        BaseMessage message = new BaseMessage("UPDATE_TEMPLATE", payload);
        message.send(topic);
    }

    public static void resetUser(String connectionId) {
        ResetUserPayload payload = new ResetUserPayload(connectionId);
        BaseMessage message = new BaseMessage("RESET_USER", payload);
        message.send(topic);
    }
}

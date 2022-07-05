package com.mindsmiths.db_adapter;

import java.io.Serializable;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.core.api.CallbackResult;
import com.mindsmiths.sdk.messaging.Messaging;


public class Db_AdapterAPI {
    private static final String topic = Messaging.getInputTopicName("db_adapter");

    public static void fetch_user(String phone) {
        Serializable payload = new PhonePayload(phone);
        BaseMessage message = new BaseMessage("fetch_user", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }

    public static void fetch_user() {
        BaseMessage message = new BaseMessage("fetch_users", new EmptyPayload());
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }
}

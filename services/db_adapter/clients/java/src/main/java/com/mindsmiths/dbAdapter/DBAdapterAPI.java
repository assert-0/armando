package com.mindsmiths.dbAdapter;

import java.io.Serializable;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.core.api.CallbackResult;
import com.mindsmiths.sdk.messaging.Messaging;


public class DBAdapterAPI {
    private static final String topic = Messaging.getInputTopicName("db_adapter");

    public static void fetchUser(String userId) {
        Serializable payload = new DBQueryPayload(userId);
        BaseMessage message = new BaseMessage("FETCH_USER", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }

    public static void fetchAllUsers() {
        BaseMessage message = new BaseMessage("FETCH_ALL_USERS", null);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }

    public static void updateUser(User newUser) {
        Serializable payload = new DBUpdatePayload(newUser);
        BaseMessage message = new BaseMessage("UPDATE_USER", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), Boolean.class).save();
    }
}

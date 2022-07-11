package com.mindsmiths.dbAdapter;

import java.io.Serializable;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.core.api.CallbackResult;
import com.mindsmiths.sdk.messaging.Messaging;


public class DBAdapterAPI {
    private static final String topic = Messaging.getInputTopicName("db_adapter");

    public static void fetchUser(int requestId, String userId) {
        Serializable payload = new DBFetchPayload(requestId, userId);
        BaseMessage message = new BaseMessage("FETCH_USER", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }

    public static void fetchUser(String userId) {
        fetchUser(0, userId);
    }

    public static void fetchAllUsers(int requestId) {
        Serializable payload = new DBFetchAllPayload(requestId);
        BaseMessage message = new BaseMessage("FETCH_ALL_USERS", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), FetchResult.class).save();
    }

    public static void fetchAllUsers() {
        fetchAllUsers(0);
    }

    public static void updateUser(int requestId, User newUser) {
        Serializable payload = new DBUpdatePayload(requestId, newUser);
        BaseMessage message = new BaseMessage("UPDATE_USER", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), UpdateResult.class).save();
    }

    public static void updateUser(User newUser) {
        updateUser(0, newUser);
    }
}

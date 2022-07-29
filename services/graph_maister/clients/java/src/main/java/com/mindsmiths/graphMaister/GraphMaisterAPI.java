package com.mindsmiths.graphMaister;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.mindsmiths.sdk.core.api.BaseMessage;
import com.mindsmiths.sdk.core.api.CallbackResult;
import com.mindsmiths.sdk.messaging.Messaging;


public class GraphMaisterAPI {
    private static final String topic = Messaging.getInputTopicName("graph_maister");

    public static void createGraph(int requestId, List<Double> x, List<Double> y,
        String graphFmt, String title, String xlabel, String ylabel, String imageFormat)
    {
        Serializable payload = new CreateGraphPayload(requestId, x, y, graphFmt, title, xlabel, ylabel, imageFormat);
        BaseMessage message = new BaseMessage("CREATE_GRAPH", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), GraphResult.class).save();
    }

    public static void createGraph(int requestId, List<Double> y,
        String fmt, String title, String xlabel, String ylabel, String imageFormat)
    {
        var x = new LinkedList<Double>();
        for (double i = 0; i < y.size(); ++i)
            x.add(i);
        createGraph(requestId, x, y, fmt, title, xlabel, ylabel, imageFormat);
    }

    public static void createGraphDate(int requestId, String startDate, String dateFmt, List<Double> y,
        String graphFmt, String title, String xlabel, String ylabel, String imageFormat)
    {
        Serializable payload = new CreateGraphDatePayload(requestId, startDate, dateFmt, y, graphFmt, title, xlabel, ylabel, imageFormat);
        BaseMessage message = new BaseMessage("CREATE_GRAPH_DATE", payload);
        message.send(topic);
        new CallbackResult(message.getConfiguration().getMessageId(), GraphResult.class).save();
    }
}

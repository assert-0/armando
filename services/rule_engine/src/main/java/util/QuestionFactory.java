package util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import util.actions.CallAgentAction;


public abstract class QuestionFactory {
    public static Stack<Question> getQuestionTree() {
        Action callAgentAction = new CallAgentAction("AGENT");
        Action callHITLAction = new CallAgentAction("HITL");
        Question interestedQuestion = new Question(
            "4",
            "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
            false,
            Arrays.asList(
                new Answer("Sure!", callAgentAction, null),
                new Answer("Not really, no.", callHITLAction, null)
            )
        );
        Question botheringQuestion = new Question(
            "3",
            "Sorry to hear that :/ What exactly is bothering you with your current real estate?",
            true,
            Arrays.asList(
                new Answer("Too much/too little space...", null, null),
                new Answer("The balcony or lack of it...", null, null),
                new Answer("The not ideal location...", null, null),
                new Answer("The construction year...", null, null),
                new Answer("The elevator or lack of it...", null, null),
                new Answer("The bad housing infrastructure or lack of it...", null, null),
                new Answer("The bad infrastructure or lack of it...", null, null),
                new Answer("The furniture or lack of it...", null, null),
                new Answer("Too many/too few rooms..." , null, null),
                new Answer("The neighbours...", null, null),
                new Answer("Something else...", callHITLAction, null)
            )
        );
        Question difficultiesQuestion = new Question(
            "2",
            "Were there maybe some difficulties that you discovered with the real estate?",
            true,
            Arrays.asList(
                new Answer("Too much/too little space...", null, null),
                new Answer("The balcony or lack of it...", null, null),
                new Answer("The not ideal location...", null, null),
                new Answer("The construction year...", null, null),
                new Answer("The elevator or lack of it...", null, null),
                new Answer("The bad housing infrastructure or lack of it...", null, null),
                new Answer("The bad infrastructure or lack of it...", null, null),
                new Answer("The furniture or lack of it...", null, null),
                new Answer("Too many/too few rooms..." , null, null),
                new Answer("The neighbours...", null, null),
                new Answer("Something else...", callHITLAction, null)
            )
        );
        Question likeQuestion = new Question(
            "1",
            "Glad to hear that! What exactly do you like with your new real estate?",
            true,
            Arrays.asList(
                new Answer("The amount of space!", null, difficultiesQuestion),
                new Answer("The beautiful balcony!", null, difficultiesQuestion),
                new Answer("The great location!", null, difficultiesQuestion),
                new Answer("The construction year!", null, difficultiesQuestion),
                new Answer("The elevator!", null, difficultiesQuestion),
                new Answer("The housing infrastructure that comes with it!", null, difficultiesQuestion),
                new Answer("The civil infrastructure!", null, difficultiesQuestion),
                new Answer("The fact that it was furnished already!", null, difficultiesQuestion),
                new Answer("The number of rooms!", null, difficultiesQuestion),
                new Answer("Something else!", callHITLAction, null)
            )
        );
        Question checkQuestion = new Question(
            "0",
            "Is everything okay with your real estate?",
            false,
            Arrays.asList(
                new Answer("YES", null, likeQuestion),
                new Answer("NO", null, botheringQuestion)
            )
        );
        Stack<Question> out = new Stack<>();
        out.push(interestedQuestion);
        out.push(checkQuestion);
        return out;
    }

    public static Stack<Question> createConversation(String ...messages) {
        Stack<Question> out = new Stack<>();
        for (int i = messages.length - 1; i >= 0; i--) {
            out.push(new Question(messages[i]));
        }
        return out;
    }

    public static Stack<Question> createRandomConversation(List<String> ...conversations) {
        var rand = new Random(LocalDateTime.now().getNano());
        var index = rand.nextInt(conversations.length);
        return createConversation(conversations[index].toArray(new String[0]));
    }
}

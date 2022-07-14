package classes;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


public abstract class QuestionFactory {
    public static Question getQuestionTree() {
        Question interestedQuestion = new Question(
            "4",
            "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
            false,
            Arrays.asList(
                new Answer("Sure!", Answer.Action.CALL_AGENT, null),
                new Answer("Not really, no.", Answer.Action.CALL_HITL, null)
            )
        );
        Question botheringQuestion = new Question(
            "3",
            "Sorry to hear that :/ What exactly is bothering you with your current real estate?",
            true,
            Arrays.asList(
                new Answer("Too much/too little space...", Answer.Action.NONE, interestedQuestion),
                new Answer("The balcony or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The not ideal location...", Answer.Action.NONE, interestedQuestion),
                new Answer("The construction year...", Answer.Action.NONE, interestedQuestion),
                new Answer("The elevator or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The bad infrastructure or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The furniture or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("Too many/too few rooms..." , Answer.Action.NONE, interestedQuestion),
                new Answer("The neighbours...", Answer.Action.NONE, interestedQuestion),
                new Answer("Something else...", Answer.Action.CALL_HITL, null)
            )
        );
        Question difficultiesQuestion = new Question(
            "2",
            "Were there maybe some difficulties that you discovered with the real estate?",
            true,
            Arrays.asList(
                new Answer("Too much/too little space...", Answer.Action.NONE, interestedQuestion),
                new Answer("The balcony or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The not ideal location...", Answer.Action.NONE, interestedQuestion),
                new Answer("The construction year...", Answer.Action.NONE, interestedQuestion),
                new Answer("The elevator or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The bad infrastructure or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("The furniture or lack of it...", Answer.Action.NONE, interestedQuestion),
                new Answer("Too many/too few rooms..." , Answer.Action.NONE, interestedQuestion),
                new Answer("The neighbours...", Answer.Action.NONE, interestedQuestion),
                new Answer("Something else...", Answer.Action.CALL_HITL, null)
            )
        );
        Question likeQuestion = new Question(
            "1",
            "Glad to hear that! What exactly do you like with your new real estate?",
            true,
            Arrays.asList(
                new Answer("The amount of space!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The beautiful balcony!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The great location!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The construction year!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The elevator!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The housing infrastructure that comes with it!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The civil infrastructure!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The fact that it was furnished already!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("The number of rooms!", Answer.Action.NONE, difficultiesQuestion),
                new Answer("Something else!", Answer.Action.CALL_HITL, null)
            )
        );
        Question checkQuestion = new Question(
            "0",
            "Is everything okay with your real estate?",
            false,
            Arrays.asList(
                new Answer("YES", Answer.Action.NONE, likeQuestion),
                new Answer("NO", Answer.Action.NONE, botheringQuestion)
            )
        );
        return checkQuestion;
    }
}

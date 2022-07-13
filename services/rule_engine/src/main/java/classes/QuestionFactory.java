package classes;

import java.util.List;

import classes.Question;
import classes.Answer;

public class QuestionFactory {
    public static void fillQuestions(List<Question> list, String type) {
        if(type.equals("YES")) {
                list.add(
                new Question(
                    "Glad to hear that! What exactly do you like with your new real estate?",
                    Arrays.asList(
                        new Answer("The amount of space!", Answer.Action.NEXT_QUESTION),
                        new Answer("The beautiful balcony!", Answer.Action.NEXT_QUESTION),
                        new Answer("The great location!", Answer.Action.NEXT_QUESTION),
                        new Answer("The construction year!", Answer.Action.NEXT_QUESTION),
                        new Answer("The elevator!", Answer.Action.NEXT_QUESTION),
                        new Answer("The housing infrastructure that comes with it!", Answer.Action.NEXT_QUESTION),
                        new Answer("The civil infrastructure!", Answer.Action.NEXT_QUESTION),
                        new Answer("The fact that it was furnished already!", Answer.Action.NEXT_QUESTION),
                        new Answer("The number of rooms!", Answer.Action.NEXT_QUESTION),
                        new Answer("Something else!", Answer.Action.CALL_HITL)
                    )
                )
            );
            list.add(
                new Question(
                    "Were there maybe some difficulties that you discovered with the real estate?",
                    Arrays.asList(
                        new Answer("Too much/too little space...", Answer.Action.NEXT_QUESTION),
                        new Answer("The balcony or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The not ideal location...", Answer.Action.NEXT_QUESTION),
                        new Answer("The construction year...", Answer.Action.NEXT_QUESTION),
                        new Answer("The elevator or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The bad infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The furniture or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("Too many/too few rooms..." , Answer.Action.NEXT_QUESTION),
                        new Answer("The neighbours...", Answer.Action.NEXT_QUESTION),
                        new Answer("Something else...", Answer.Action.CALL_HITL)
                    )
                )
            );        
            list.add(
                new Question(
                    "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
                    Arrays.asList(
                        new Answer("Sure!", Answer.Action.CALL_AGENT),
                        new Answer("Not really, no.", Answer.Action.CALL_HITL)
                    )
                )
            );
        } else {
            list.add(
                new Question(
                    "Sorry to hear that :/ What exactly is bothering you with your current real estate?",
                    Arrays.asList(
                        new Answer("Too much/too little space...", Answer.Action.NEXT_QUESTION),
                        new Answer("The balcony or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The not ideal location...", Answer.Action.NEXT_QUESTION),
                        new Answer("The construction year...", Answer.Action.NEXT_QUESTION),
                        new Answer("The elevator or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The bad housing infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The bad infrastructure or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("The furniture or lack of it...", Answer.Action.NEXT_QUESTION),
                        new Answer("Too many/too few rooms..." , Answer.Action.NEXT_QUESTION),
                        new Answer("The neighbours...", Answer.Action.NEXT_QUESTION),
                        new Answer("Something else...", Answer.Action.CALL_HITL)
                    )
                )
            );
            list.add(
                new Question(
                    "Yikes, maybe I can help with that! Are you perhaps interested in buying a new real estate?",
                    Arrays.asList(
                        new Answer("Sure!", Answer.Action.CALL_AGENT),
                        new Answer("Not really, no.", Answer.Action.CALL_HITL)
                    )
                )
            );    
        }
    }
}
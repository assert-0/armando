package util.actions;

import lombok.*;

import util.Action;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import agents.Armando;


@JsonDeserialize(as = SendMessageAction.class)
@Data
@NoArgsConstructor
public class SendMessageAction extends Action {
    public static final String ACTION_NAME = "SendMessage";

    private String message;

    public SendMessageAction(String message) {
        super(ACTION_NAME);
        this.message = message;
    }

    public void act(Armando agent) {
        agent.sendMessage(message);
    }
}

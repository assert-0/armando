package util.actions;

import lombok.*;

import util.Action;

import agents.Armando;


@Data
@NoArgsConstructor
public class SendMessageAction extends Action {
    public static final String ACTION_NAME = "SendMessage";

    private String message;

    public SendMessageAction(String message) {
        super(ACTION_NAME);
        this.message = message;
    }

    public void act(Object value) {
        var agent = (Armando)value;
        agent.sendMessage(message);
    }
}

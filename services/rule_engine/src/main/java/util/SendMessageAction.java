package util;

import lombok.*;

import util.Action;
import agents.Armando;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageAction extends Action {
    private String message;

    public void act(Armando agent) {
        agent.sendMessage(message);
    }
}

package util.processors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.*;

import com.mindsmiths.dbAdapter.User;

import agents.Armando;
import util.Question;
import util.QuestionProcessor;


@Data
public class TemplateQuestionProcessor extends QuestionProcessor {
    public static final String PROCESSOR_NAME = "TemplateProcessor";

    public static final String TEMPLATE_START = "${";
    public static final String TEMPLATE_END = "}";

    public TemplateQuestionProcessor() {
        super(PROCESSOR_NAME);
    }

    private void processTemplate(StringBuilder textBuilder, String template, Armando agent) {
        Method method;
        try {
            method = User.class.getMethod("get" + template.substring(0, 1).toUpperCase() + template.substring(1));
        } catch(NoSuchMethodException | SecurityException ignored) {
            return;
        }
        Object result;
        try {
            result = method.invoke(agent.getUser());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
            return;
        }
        textBuilder.append(result.toString());
    }

    @Override
    public Question process(Question question, Object value) {
        var agent = (Armando)value;
        var newQuestion = new Question(question);
        //
        boolean inTemplate = false;
        int startCount = 0;
        int endCount = 0;
        var textBuilder = new StringBuilder();
        var templateBuilder = new StringBuilder();
        //
        for (var ch : question.getText().toCharArray()) {
            if (inTemplate) {
                templateBuilder.append(ch);
                if (ch == TEMPLATE_END.charAt(endCount)) endCount++;
                if (endCount == TEMPLATE_END.length()) {
                    var index = templateBuilder.length() - endCount;
                    processTemplate(textBuilder, templateBuilder.substring(TEMPLATE_START.length(), index), agent);
                    templateBuilder.setLength(0);
                    inTemplate = false;
                    endCount = 0;
                }
            }
            else {
                textBuilder.append(ch);
                if (ch == TEMPLATE_START.charAt(startCount)) startCount++;
                if (startCount == TEMPLATE_START.length()) {
                    var index = textBuilder.length() - startCount;
                    templateBuilder.append(textBuilder.substring(index));
                    textBuilder.setLength(index);
                    inTemplate = true;
                    startCount = 0;
                }
            }
        }
        //
        newQuestion.setText(textBuilder.toString());
        return newQuestion;
    }
}

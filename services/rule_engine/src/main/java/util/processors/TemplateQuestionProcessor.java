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

    private String className;

    public TemplateQuestionProcessor(Class<?> cls) {
        super(PROCESSOR_NAME);
        this.className = cls.getName();
    }

    public TemplateQuestionProcessor(String className) {
        super(PROCESSOR_NAME);
        this.className = className;
    }

    private void processTemplate(Class<?> cls, StringBuilder textBuilder, String template, Object value) {
        Method method;
        Object result = null;
        Class<?> cls1 = cls;
        for (var str : template.split("\\.")) {
            try {
                method = cls1.getMethod("get" + str.substring(0, 1).toUpperCase() + str.substring(1));
            } catch(NoSuchMethodException | SecurityException ignored) {
                return;
            }
            try {
                result = method.invoke(value);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
                return;
            }
            value = result;
            cls1 = result.getClass();
        }
        if (result != null)
            textBuilder.append(result.toString());
    }

    @Override
    public Question process(Question question, Object value) {
        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException ignored) {
            return question;
        }
        //
        var newQuestion = new Question(question);
        //
        boolean inTemplate = false;
        int count = 0;
        var textBuilder = new StringBuilder();
        var templateBuilder = new StringBuilder();
        //
        for (var ch : question.getText().toCharArray()) {
            if (inTemplate) {
                templateBuilder.append(ch);
                if (ch == TEMPLATE_END.charAt(count)) count++;
                if (count == TEMPLATE_END.length()) {
                    var index = templateBuilder.length() - count;
                    processTemplate(cls, textBuilder, templateBuilder.substring(TEMPLATE_START.length(), index), value);
                    templateBuilder.setLength(0);
                    inTemplate = false;
                    count = 0;
                }
            }
            else {
                textBuilder.append(ch);
                if (ch == TEMPLATE_START.charAt(count)) count++;
                if (count == TEMPLATE_START.length()) {
                    var index = textBuilder.length() - count;
                    templateBuilder.append(textBuilder.substring(index));
                    textBuilder.setLength(index);
                    inTemplate = true;
                    count = 0;
                }
            }
        }
        //
        newQuestion.setText(textBuilder.toString());
        return newQuestion;
    }
}

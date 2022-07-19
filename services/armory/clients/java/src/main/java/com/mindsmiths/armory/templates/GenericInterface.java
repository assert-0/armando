package com.mindsmiths.armory.templates;

import com.mindsmiths.armory.components.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericInterface extends BaseTemplate {
    protected Title title;
    protected Image image;
    protected ShadowImage shadowImage;
    protected Description description;
    protected TextArea textArea;
    protected List<SubmitButton> actions;
    protected Boolean allowsUndo;


    public GenericInterface(Title title) {
        this.title = title;
    }

    public GenericInterface(Title title, Description description) {
        this.title = title;
        this.description = description;
    }

    public GenericInterface(Title title, Image image) {
        this.title = title;
        this.image = image;
    }

    public GenericInterface(Title title, Image image, Description description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public GenericInterface(Title title, Description description, List<SubmitButton> actions) {
        this.title = title;
        this.description = description;
        this.actions = actions;
    }

    public GenericInterface(Title title, Description description, TextArea textArea, List<SubmitButton> actions) {
        this.title = title;
        this.description = description;
        this.textArea = textArea;
        this.actions = actions;
    }
}

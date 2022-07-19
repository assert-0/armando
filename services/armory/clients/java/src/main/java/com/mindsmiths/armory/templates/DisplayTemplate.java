package com.mindsmiths.armory.templates;

import com.mindsmiths.armory.components.*;
import com.mindsmiths.armory.templates.BaseTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayTemplate extends BaseTemplate {
    protected Title title;
    protected Image image;
    protected Description description;
    protected List<SubmitButton> actions;

    public DisplayTemplate(Title title, Description description, Image image, List<SubmitButton> actions) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.actions = actions;
    }
}

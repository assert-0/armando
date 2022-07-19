package com.mindsmiths.armory.templates;

import armory.classes.ScaledImage;
import com.mindsmiths.armory.components.Description;
import com.mindsmiths.armory.components.SubmitButton;
import com.mindsmiths.armory.components.TextInput;
import com.mindsmiths.armory.components.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DefaultInterface extends BaseTemplate {
    private ScaledImage image;
    private Title title;
    private Description description;
    private TextInput textInput;
    private List<SubmitButton> actions;
}

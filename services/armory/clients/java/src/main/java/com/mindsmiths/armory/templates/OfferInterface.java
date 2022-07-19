package com.mindsmiths.armory.templates;

import armory.classes.ScaledImage;
import com.mindsmiths.armory.components.Description;
import com.mindsmiths.armory.components.SubmitButton;
import com.mindsmiths.armory.components.Title;
import com.mindsmiths.armory.components.Popup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OfferInterface extends BaseTemplate {
    private ScaledImage image;
    private Title title;
    private Description description;
    private List<SubmitButton> actions;
    private boolean allowsUndo = true;
    private Popup popup;
    private Description additionalDescription;
}

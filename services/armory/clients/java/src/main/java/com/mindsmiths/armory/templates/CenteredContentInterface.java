package com.mindsmiths.armory.templates;

import armory.classes.ScaledImage;
import com.mindsmiths.armory.components.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CenteredContentInterface extends BaseTemplate {
    private ScaledImage image;
    private Title text;
}

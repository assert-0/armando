package com.mindsmiths.armory.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitButton extends BaseComponent {
    protected String inputId;
    protected String text;
    protected Object value;
}

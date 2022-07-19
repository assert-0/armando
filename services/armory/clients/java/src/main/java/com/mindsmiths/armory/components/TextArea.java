package com.mindsmiths.armory.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextArea extends BaseComponent {
    protected String inputId;
    protected String placeholder;
    protected String value;
    protected Boolean required;

    public TextArea(String inputId, String value) {
        this.inputId = inputId;
        this.value = value;
    }

    public TextArea(String inputId, String value, Boolean required) {
        this.inputId = inputId;
        this.value = value;
        this.required = required;
    }

    public TextArea(String inputId, String placeholder, String value) {
        this.inputId = inputId;
        this.placeholder = placeholder;
        this.value = value;
    }
}

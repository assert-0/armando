package com.mindsmiths.armory.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextInput extends BaseComponent {
    protected String inputId;
    protected String value = "";

    protected String placeholder = "";
    protected String type;
    protected Boolean required;

    public TextInput(String inputId, String value) {
        this.inputId = inputId;
        this.value = value;
    }

    public TextInput(String inputId, String value, Boolean required) {
        this.inputId = inputId;
        this.value = value;
        this.required = required;
    }

    public TextInput(String inputId, Boolean required, String placeholder) {
        this.inputId = inputId;
        this.placeholder = placeholder;
        this.required = required;
    }

    public TextInput(String inputId, String type, String value) {
        this.inputId = inputId;
        this.type = type;
        this.value = value;
    }
}

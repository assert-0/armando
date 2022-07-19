package com.mindsmiths.armory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTemplatePayload implements Serializable {
    private String connectionId;
    private String referenceId;
    private String template;
    private Object data;
}

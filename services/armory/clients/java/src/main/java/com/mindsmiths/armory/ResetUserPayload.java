package com.mindsmiths.armory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetUserPayload implements Serializable {
    private String connectionId;
}

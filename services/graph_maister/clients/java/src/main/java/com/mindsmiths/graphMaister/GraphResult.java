package com.mindsmiths.graphMaister;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphResult implements Serializable {
    private int requestId;
    private boolean success;
    private String base64Graph;
}

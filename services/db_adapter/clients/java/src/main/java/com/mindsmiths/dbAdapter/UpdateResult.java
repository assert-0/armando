package com.mindsmiths.dbAdapter;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResult implements Serializable {
    private int requestId;
    private boolean success;
}

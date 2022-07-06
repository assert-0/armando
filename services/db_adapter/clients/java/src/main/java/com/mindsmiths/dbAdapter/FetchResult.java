package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchResult implements Serializable {
    private boolean success;
    private List<User> users;
}

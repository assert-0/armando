package com.mindsmiths.dbAdapter;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    private String text;
    private List<String> answers;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Question)) return false;
        Question q = (Question)obj;
        return text.equals(q.text);
    }
}

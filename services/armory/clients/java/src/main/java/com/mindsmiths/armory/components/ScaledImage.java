package armory.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScaledImage {
    String src;
    boolean hasMargin;

    public ScaledImage(String src){
        this.src = src;
    }
}

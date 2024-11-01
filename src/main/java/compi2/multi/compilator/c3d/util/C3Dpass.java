
package compi2.multi.compilator.c3d.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter @NoArgsConstructor
public class C3Dpass {
    private int endLabel;
    private int startBucleLabel;
    private int endIfLabel;
    private int endMethod;

    public C3Dpass(int endLabel) {
        this.endLabel = endLabel;
    }

    public C3Dpass(int endLabel, int startBucleLabel) {
        this.endLabel = endLabel;
        this.startBucleLabel = startBucleLabel;
    }
    
}

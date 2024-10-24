
package compi2.multi.compilator.c3d.util;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class C3Dpass {
    private int endLabel;
    private int startBucleLabel;
    private int endIfLabel;

    public C3Dpass(int endLabel) {
        this.endLabel = endLabel;
    }

    public C3Dpass(int endLabel, int startBucleLabel) {
        this.endLabel = endLabel;
        this.startBucleLabel = startBucleLabel;
    }
    
}

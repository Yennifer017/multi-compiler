
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ReturnRow extends RowST{
    
    private int relativeDir;

    public ReturnRow(int relativeDir) {
        super(AdditionalInfoST.DIR_RETORNO_ROW.getNameRow(), Category.Retorno, null);
        this.relativeDir = relativeDir;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}

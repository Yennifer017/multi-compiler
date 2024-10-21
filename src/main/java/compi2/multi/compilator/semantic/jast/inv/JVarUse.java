package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class JVarUse extends JInvocation {

    private String name;

    public JVarUse(Position position, String name, JContextRef context) {
        super(position, context);
        this.name = name;
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors) {
        switch (context) {
            case JContextRef.Local:
                SymbolTable currentST = symbolTable;
                while (currentST != null) {
                    if (currentST.containsKey(name)) {
                        RowST rowST = currentST.get(name);
                        if (rowST instanceof SingleData) {
                            SingleData singleData = (SingleData) rowST;
                            return new Label(singleData.getType(), position);
                        } else {
                            //add error
                            return new Label(Analyzator.ERROR_TYPE, position);
                        }
                    }
                    currentST = symbolTable.getFather();
                }
            case JContextRef.FromObject:
                currentST = jerar.getClassST().getInternalST();
                if (currentST.containsKey(name)) {
                    RowST rowST = currentST.get(name);
                    if (rowST instanceof FieldST) {
                        FieldST fieldST = (FieldST) rowST;
                        return new Label(fieldST.getType(), position);
                    } else {
                        return continueFind(symbolTable);
                    }
                }
            default: //from father
                NodeJerarTree currentNode = jerar.getFather();
                while (currentNode != null) {
                    currentST = currentNode.getClassST().getInternalST();
                    if (currentST.containsKey(name)) {
                        RowST rowST = currentST.get(name);
                        if (rowST instanceof FieldST) {
                            FieldST fieldST = (FieldST) rowST;
                            return new Label(fieldST.getType(), position);
                        } else {
                            return continueFind(currentST);
                        }
                    }
                    currentNode = currentNode.getFather();
                }
                //add error
                return new Label(Analyzator.ERROR_TYPE, position);
        }
    }

    private Label continueFind(SymbolTable symbolTable) {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name, index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name, index));
            if (anotherRowST instanceof FieldST) {
                return new Label(anotherRowST.getType(), position);
            }
            index++;
        }
        //add error
        return new Label(Analyzator.ERROR_TYPE, position);
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrorrs, Label previus) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

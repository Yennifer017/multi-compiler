
package compi2.multi.compilator.semantic.past;


import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class SimpleCase{
    private List<Expression> labels;
    private List<Statement> statements;
    
    private StmtsAnalizator stmtsAnalizator;
    private TConvertidor tConvertidor;
    private ErrorsRep errorsRep;

    
    public SimpleCase(){
        stmtsAnalizator = new StmtsAnalizator();
        tConvertidor = new TConvertidor();
        errorsRep = new ErrorsRep();
    }

    public SimpleCase(List<Expression> labels, List<Statement> statements) {
        this.labels = labels;
        this.statements = statements;
        stmtsAnalizator = new StmtsAnalizator();
        tConvertidor = new TConvertidor();
        errorsRep = new ErrorsRep();
    }
    
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions, String typeLabel) {
        validateLabels(symbolTable, typeTable, semanticErrors, typeLabel);
        return stmtsAnalizator.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                restrictions, statements);
    }
    
    private void validateLabels(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, String typeLabel){
        if(labels != null && !labels.isEmpty()){
            for (Expression expLabel : labels) {
                Label type = expLabel.validateSimpleData(symbolTable, semanticErrors);
                if(!type.getName().equals(typeLabel)
                        && tConvertidor.canUpgradeType(typeLabel, type.getName())
                        ){
                    semanticErrors.add(errorsRep.incorrectTypeError(
                            type.getName(),
                            typeLabel,
                            type.getPosition()
                    ));
                }
            }
        }
    }

    
}

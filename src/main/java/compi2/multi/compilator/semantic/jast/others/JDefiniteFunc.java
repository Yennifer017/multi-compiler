/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.util.JPassExp;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JDefiniteFunc extends Statement{
    
    private boolean withLn;
    private List<JPassExp> listToPrint;

    public JDefiniteFunc(Position initPos, List<JPassExp> listToPrint, boolean withLn) {
        super(initPos);
        this.listToPrint = listToPrint;
        this.withLn = withLn;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

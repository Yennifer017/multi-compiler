/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly.components;

import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class Literal {
    private final String value;
    private final String label;
    private final String lenghtLabel;
    
    protected Literal(String value, int id) {
        this.value = value;
        this.label = "lit_" + id;
        this.lenghtLabel = "lit_len_" + id;
    }
    
}

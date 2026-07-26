/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package compi2.multi.compilator.assembly.interfaces;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.components.Literal;

/**
 *
 * @author blue-dragon
 */
public interface IImmediateValue extends AssemblyPreparable{
    Literal getLiteral(AssemblyComps ac);
}

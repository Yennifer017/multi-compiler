/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package compi2.multi.compilator.assembly.interfaces;

import compi2.multi.compilator.assembly.AssemblyComps;

/**
 *
 * @author blue-dragon
 */
public interface AssemblyTransformable{
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac);
}

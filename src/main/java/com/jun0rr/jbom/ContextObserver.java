/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbom;

/**
 *
 * @author F6036477
 */
public interface ContextObserver {
  
  public void write(WriteEvent e);
  
  public void read(BinType t);
  
}

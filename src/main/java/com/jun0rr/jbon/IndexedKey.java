/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jun0rr.jbon;

/**
 *
 * @author Juno
 */
public interface IndexedKey<T> {
  
  public int index();
  
  public T key();
  
  public IndexedKey<T> with(int index);
  
}

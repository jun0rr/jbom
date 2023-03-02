/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author F6036477
 */
public class FieldMethodExtractStrategy extends AbstractInvokeStrategy<ExtractFunction> {

  @Override
  public List<ExtractFunction> invokers(Class cls) {
    List<ExtractFunction> fns = cache.get(cls);
    if(fns == null) {
      List<Field> fls = List.of(cls.getDeclaredFields());
      fns = List.of(cls.getDeclaredMethods()).stream()
          .filter(m->m.getParameterCount() == 0)
          .filter(m->fls.stream().anyMatch(f->
              m.getName().equals(f.getName()) 
                  && m.getReturnType().equals(f.getType())))
          .map(ExtractFunction::of)
          .collect(Collectors.toList());
      cache.put(cls, fns);
    }
    return fns;
  }
  
}

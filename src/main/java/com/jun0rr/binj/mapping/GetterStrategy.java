/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.binj.mapping;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author F6036477
 */
public class GetterStrategy extends AbstractExtractStrategy {

  @Override
  public List<ExtractFunction> extractors(Class cls) {
    List<ExtractFunction> fns = cache.get(cls);
    if(fns == null) {
      fns = new LinkedList<>();
      Class sup = cls;
      while(sup != null && sup != Object.class) {
        List.of(cls.getDeclaredMethods()).stream()
            .filter(m->m.getName().startsWith("get"))
            .filter(m->m.getParameterCount() == 0)
            .filter(m->m.getReturnType() != void.class)
            .map(DefaultExtractFunction::of)
            .forEach(fns::add);
        List.of(cls.getInterfaces()).stream()
            .flatMap(c->List.of(c.getDeclaredMethods()).stream())
            .filter(m->m.getName().startsWith("get"))
            .filter(m->m.getParameterCount() == 0)
            .filter(m->m.getReturnType() != void.class)
            .map(DefaultExtractFunction::of)
            .forEach(fns::add);
        sup = sup.getSuperclass();
      }
      cache.put(cls, fns);
    }
    return fns;
  }
  
}

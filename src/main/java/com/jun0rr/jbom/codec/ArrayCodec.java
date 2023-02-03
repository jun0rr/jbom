/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jun0rr.jbom.codec;

import com.jun0rr.jbom.BinCodec;
import com.jun0rr.jbom.BinContext;
import com.jun0rr.jbom.BinType;
import com.jun0rr.jbom.UnknownBinTypeException;
import com.jun0rr.jbom.buffer.BinBuffer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author F6036477
 */
public class ArrayCodec<T> implements BinCodec<T[]> {
  
  private final BinContext ctx;
  
  private final BinType<T[]> type;
  
  public ArrayCodec(BinContext ctx, BinType<T[]> type) {
    this.ctx = Objects.requireNonNull(ctx);
    this.type = Objects.requireNonNull(type);
  }
  
  @Override
  public BinType<T[]> bintype() {
    return type;
  }

  @Override
  public T[] read(BinBuffer buf) {
    long id = buf.getLong();
    if(id != bintype().id()) {
      throw new UnknownBinTypeException(id);
    }
    int size = buf.getShort();
    buf.position(buf.position() + Short.BYTES * size);
    T[] array = (T[]) Array.newInstance(bintype().type().getComponentType(), size);
    for(int i = 0; i < size; i++) {
      array[i] = ctx.read(buf);
    }
    return array;
  }
  
  @Override
  public void write(BinBuffer buf, T[] array) {
    buf.putLong(bintype().id());
    buf.putShort((short)array.length);
    int kpos = buf.position();
    buf.position(kpos + Short.BYTES * array.length);
    List<Integer> idx = new ArrayList<>(array.length);
    Iterator it = List.of(array).iterator();
    int lpos = buf.position();
    while(it.hasNext()) {
      idx.add(buf.position());
      ctx.write(buf, it.next());
      lpos = buf.position();
    }
    buf.position(kpos);
    idx.forEach(i->buf.putShort(i.shortValue()));
    buf.position(lpos);
  }

  @Override
  public int calcSize(T[] array) {
    int size = List.of(array).stream().mapToInt(o->ctx.calcSize(o)).sum();
    return Long.BYTES + Short.BYTES + Short.BYTES * array.length + size;
  }

}

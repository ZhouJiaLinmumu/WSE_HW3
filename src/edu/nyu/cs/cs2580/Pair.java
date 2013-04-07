package edu.nyu.cs.cs2580;

/**
 * This class represents a pair of values of any type.
 * @author sujal
 * 
 * @param <T>
 * @param <U>
 */
public class Pair<T, U> {         
    private final T t;
    private final U u;

    public Pair(T t, U u) {         
        this.t= t;
        this.u= u;
     }
    
    public T getFirstElement() {
    	return t;
    }
    
    public U getSecondElement() {
    	return u;
    }
 }

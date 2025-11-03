package it.unibo.inner.test.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate; 

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private T[] array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] array){
        this.filter = new Predicate<T>() {
            public boolean test(T t){
                return true;
            }
        };
        this.array=array;
    }

    public IterableWithPolicyImpl(final T[] array, final Predicate<T> filter){
        if(array == null){
            throw new NullPointerException("L'array non contiene elementi");
        } else if (filter == null){
            throw new NullPointerException("Il filtro è nullo");
        }
        this.array = array;
        this.filter = filter;
    }

    public void setIterationPolicy(Predicate<T> filter){
        this.filter = filter;
    }

    public java.util.Iterator<T> iterator(){
        return new Iterator();
    }

    public class Iterator implements java.util.Iterator<T> {

        private int index = -1;
        private int nextIndex;

        public Iterator(){

        }

        public boolean hasNext(){
            nextIndex=index+1;
            while(nextIndex<array.length){
                if(filter.test(array[nextIndex])) {
                    return true;
                }
                nextIndex++;
            }
            return false;
        }

        public T next(){
            if(hasNext()){
                index=nextIndex;
                return array[index];
            } else {
                throw new UnsupportedOperationException("Il prossimo elemento non esiste");
            }
        }
    }
}

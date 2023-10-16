package io.meltwin.scyblaster.common.types;

/**
 * Definition of a pair structure
 */
public class Pair<T, Y> {
    private T a;
    private Y b;

    public Pair(T a, Y b) {
        this.a = a;
        this.b = b;
    }

    public void setFirst(T a) {
        this.a = a;
    }

    public T getFirst() {
        return a;
    }

    public void setSecond(Y b) {
        this.b = b;
    }

    public Y getSecond() {
        return b;
    }

}

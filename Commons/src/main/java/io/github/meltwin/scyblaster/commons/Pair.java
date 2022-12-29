package io.github.meltwin.scyblaster.commons;

/**
 * Container to contain two different elements
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class Pair<A, B> {

    private A a; private B b;

    public Pair(final A a, final B b) {
        this.a = a;
        this.b = b;
    }

    public A getFirst() {return a;}
    public B getSecond() {return b;}

    public void setFirst(A a) {this.a = a;}
    public void setSecond(B b) {this.b = b;}
}

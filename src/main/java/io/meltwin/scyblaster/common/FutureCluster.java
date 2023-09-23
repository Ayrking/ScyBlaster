package io.meltwin.scyblaster.common;

import java.util.ArrayList;
import java.util.concurrent.Future;

public class FutureCluster<T> extends ArrayList<Future<T>> {
    public boolean allReady() {
        for (Future<T> obj : this) {
            if (!obj.isDone())
                return false;
        }
        return true;
    }
}

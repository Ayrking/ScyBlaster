package io.meltwin.scyblaster.common.types;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * Definition of a cluster of futures waiting to be completed
 */
public class FutureCluster<T> extends ArrayList<Future<T>> {
    public boolean allReady() {
        for (Future<T> obj : this) {
            if (!obj.isDone())
                return false;
        }
        return true;
    }
}

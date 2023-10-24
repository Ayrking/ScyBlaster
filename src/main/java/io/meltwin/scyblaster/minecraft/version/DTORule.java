/**
 * MC Version DTO descriptor
 * Type : Rule
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.version;

import java.util.HashMap;

class DTORule {

    DTORule(String action, OS os) {
        this.action = action;
        this.os = os;
    }

    public class FeatureMap extends HashMap<String, String> {
    }

    public class OS {
        public final String name = null;
        public final String arch = null;
    }

    public final String action;
    public final FeatureMap features = new FeatureMap();
    public final OS os;
}

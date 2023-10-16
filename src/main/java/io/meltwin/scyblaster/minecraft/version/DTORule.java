/**
 * MC Version DTO descriptor
 * Type : Rule
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.version;

import java.util.HashMap;

class DTORule {
    public class FeatureMap extends HashMap<String, String> {
    }

    public class OS {
        public String name;
        public String arch;
    }

    public String action;
    public FeatureMap features = new FeatureMap();
    public OS os;
}

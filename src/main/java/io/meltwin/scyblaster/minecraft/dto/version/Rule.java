/**
 * MC Version DTO descriptor
 * Type : Rule
 * Author: Meltwin
 */
package io.meltwin.scyblaster.minecraft.dto.version;

import java.util.HashMap;

public class Rule {
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

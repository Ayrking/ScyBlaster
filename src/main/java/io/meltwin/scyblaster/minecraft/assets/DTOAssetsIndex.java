package io.meltwin.scyblaster.minecraft.assets;

import java.util.HashMap;
import java.util.Map;

class DTOAssetsIndex {
    public class Asset {
        public String hash = "";
        public long size = 0;
    }

    public Map<String, Asset> objects = new HashMap<>();
}

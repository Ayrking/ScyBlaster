package io.meltwin.scyblaster.minecraft.dto.assets;

import java.util.HashMap;

public class AssetsIndex {
    public class Asset {
        public String hash;
        public long size;
    }

    public HashMap<String, Asset> objects;
}

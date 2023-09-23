package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import io.meltwin.scyblaster.common.Pair;
import io.meltwin.scyblaster.minecraft.MCVersions;

public class VersionAdapter extends XmlAdapter<String, Pair<Integer, Integer>> {

    @Override
    public String marshal(Pair<Integer, Integer> v) throws Exception {
        return String.format("%s|%s", MCVersions.versionList.versions[v.a].id,
                MCVersions.versionList.versions[v.b].id);
    }

    @Override
    public Pair<Integer, Integer> unmarshal(String version) throws Exception {
        String[] parts = version.split(",");
        Pair<Integer, Integer> out = new Pair<>(0, 0);
        if (parts.length >= 2) {
            out.a = MCVersions.getVersionID(parts[0]);
            out.b = MCVersions.getVersionID(parts[1]);

            if (out.b < out.a)
                out.b = out.a;
        } else {
            out.a = MCVersions.getVersionID(version);
            out.b = out.a;
        }
        return out;
    }

}

package io.meltwin.scyblaster.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.common.types.Pair;

class VersionAdapter extends XmlAdapter<String, Pair<String, String>> implements Logging {

    @Override
    public String marshal(Pair<String, String> v) throws Exception {
        return String.format("%s|%s", v.getFirst(), v.getSecond());
    }

    @Override
    public Pair<String, String> unmarshal(String version) throws Exception {
        String[] parts = version.split("->");

        // Export
        Pair<String, String> out = new Pair<>("", "");
        if (parts.length >= 2) {
            out.setFirst(parts[0]);
            out.setSecond(parts[1]);
        } else {
            out.setFirst(version);
            out.setSecond(out.getFirst());
        }
        return out;
    }

}

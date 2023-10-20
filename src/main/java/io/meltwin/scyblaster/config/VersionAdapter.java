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
        // Logging unmarshal
        ftrace("Unmarshalling version: %s", version);
        for (String s : parts) {
            ftrace("\t %s", s);
        }

        // Export
        Pair<String, String> out = new Pair<>("", "");
        if (parts.length >= 2) {
            out.setFirst(parts[0]);
            out.setSecond(parts[1]);

            // if (out.getSecond() < out.getFirst())
            // out.setSecond(out.getFirst());
        } else {
            out.setFirst(version);
            out.setSecond(out.getFirst());
        }
        return out;
    }

}

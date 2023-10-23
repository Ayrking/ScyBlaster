package io.meltwin.scyblaster.config.project;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import io.meltwin.scyblaster.common.types.Logging;

class VersionTypeAdapter extends XmlAdapter<String, VersionType> implements Logging {

    @Override
    public String marshal(VersionType v) throws Exception {
        return v.toString();
    }

    @Override
    public VersionType unmarshal(String v) throws Exception {
        try {
            return VersionType.fromValue(v.toUpperCase());
        } catch (Exception e) {
            fwarn("The value %s is invalid for the version type !", v);
            e.printStackTrace();
        }
        return VersionType.RANGE;

    }
}

package no.unit.transformer;

import org.json.JSONObject;
import org.json.XML;

public class XMLParser implements Parser {

    @Override
    public String toXML(String xml) {
        JSONObject obj = XML.toJSONObject(xml);

        // add code here to do whatever needed to the obj

        return XML.toString(obj);
    }

    @Override
    public String toJSON(String xml) {
        JSONObject obj = XML.toJSONObject(xml);

        // add code here to do whatever needed to the obj

        return obj.toString();
    }


}

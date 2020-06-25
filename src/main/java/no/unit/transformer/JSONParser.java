package no.unit.transformer;

import org.json.JSONObject;
import org.json.XML;

public class JSONParser implements Parser {

    @Override
    public String toJSON(String json) {
        JSONObject obj = new JSONObject(json);

        // add code here to do whatever needed to the obj

        return obj.toString();
    }

    @Override
    public String toXML(String json) {
        JSONObject obj = new JSONObject(json);

        // add code here to do whatever needed to the obj

        return XML.toString(obj);
    }


}

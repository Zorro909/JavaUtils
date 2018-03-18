package javautils.RestAPI;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Pattern;

import javautils.Parser.CustomParseAble;
import javautils.Parser.ParseObject;
import javautils.XML.XmlElement;

public class RestAPIActionSet {

    HashMap<Pattern, Action> actions = new HashMap<Pattern, Action>();
    HashMap<Pattern, File> files = new HashMap<Pattern, File>();
    Action everyAction;

    public void addAction(String url, Action a) {
        if (url.equalsIgnoreCase("*") || url.equalsIgnoreCase("/*")) {
            everyAction = a;
            return;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        actions.put(Pattern.compile(url), a);
    }

    public XmlElement request(HashMap<String, String> conf) throws Exception {
        String url = URLDecoder.decode(conf.get("Request-URL"), "UTF-8");
        HashMap<String, String> vars = new HashMap<String, String>();
        if (url.contains("?")) {
            String variables = url.split("\\?", 2)[1];
            boolean more = true;
            while (more) {
                String v = "";
                if (!variables.contains("&")) {
                    more = false;
                    v = variables;
                } else {
                    v = variables.split("&", 2)[0];
                    variables = variables.split("&", 2)[1];
                }
                if (v.endsWith("=")) {
                    vars.put(v.split("=")[0], "");
                } else {
                    vars.put(v.split("=")[0], v.split("=")[1]);
                }
            }
            url = url.split("\\?", 2)[0];
        }
        CustomParseAble co = null;
        boolean found = false;
        boolean raw = false;
        String r = "error_404";
        Action a = null;
        ParseObject po = null;

        for (Pattern p : actions.keySet()) {
            if (p.matcher(url).matches()) {
                a = actions.get(p);
                if (actions.get(p).isRaw()) {
                    po = actions.get(p).executeRequest(conf, vars);
                    co = new CustomParseAble(po, "");
                    found = true;
                    raw = true;
                    if (po.objects.get("raw") instanceof byte[]) {
                        final byte[] d = (byte[]) po.objects.get("raw");
                        return new bxml(co = new CustomParseAble(po, "")) {

                            @Override
                            public byte[] toBytes() {
                                return d;
                            }
                        };
                    }
                    r = (String) po.objects.get("raw");
                    break;
                } else {
                    po = actions.get(p).executeRequest(conf, vars);
                    co = new CustomParseAble(po, "");
                    found = true;
                    break;
                }
            }
        }
        if (!found && everyAction == null) {
            throw new Exception();
        } else if (!found && everyAction != null) {
            if (everyAction.isRaw()) {
                po = everyAction.executeRequest(conf, vars);
                co = new CustomParseAble(po, "");
                found = true;
                raw = true;
                r = (String) po.objects.get("raw");
            } else {
                po = everyAction.executeRequest(conf, vars);
                co = new CustomParseAble(po, "");
                found = true;
            }
            a = everyAction;
        }
        if (!raw) {
            return new XmlElement(co);
        } else {
            final String ra = (po.getObjects().containsKey("content_type")
                            ? "http_content_type=" + po.getObjects().get("content_type") + ":" : "")
                            + (po.getObjects().containsKey("header")
                                            ? "header:" + po.getObjects().get("header") + "&%" : "")
                            + (po.getObjects().containsKey("status")
                                            ? "status:" + po.getObjects().get("status") + "&%" : "")
                            + r;
            return new XmlElement(co) {
                @Override
                public String decode() {
                    return ra;
                }
            };
        }
    }

    public void addFile(String string, File file) {
        if (!string.startsWith("/")) string = "/" + string;
        files.put(Pattern.compile(string), file);
    }

    public boolean isFile(String s) {
        for (Pattern p : files.keySet()) {
            if (p.matcher(s).matches()){
                return true;
            }
        }
        return false;
    }

    public File getFile(String s) {
        for (Pattern p : files.keySet()) {
            if (p.matcher(s).matches()) return files.get(p);
        }
        return null;
    }

}

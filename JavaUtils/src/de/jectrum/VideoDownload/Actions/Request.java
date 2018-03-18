package de.jectrum.VideoDownload.Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.jectrum.VideoDownload.Exceptions.HosterNotFoundException;

public class Request {
    private String url;
    private HashMap<String, String> variables = new HashMap<String, String>();
    private String http_method = "POST";
    private JsonElement result;

    public Request(String url) {
        this.url = url;
    }

    public static Request createEmptyRequest() {
        return new EmptyRequest();
    }

    public void addVariable(String key, String value) {
        variables.put(key, value);
    }

    public void setHttpMethod(String name) {
        http_method = name;
    }
    int tr = 0;
    
    public void fetch() throws MalformedURLException, IOException, HosterNotFoundException {
        String request = "";
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
        if (http_method.equals("POST")) {
            Gson gson = new Gson();
            JsonObject ele = new JsonObject();
            for (String property : variables.keySet()) {
                ele.addProperty(property, variables.get(property));
            }
            ele.addProperty("secure", "true");
            request = gson.toJson(ele);
            con.setRequestProperty("Content-Type", "application/json");
        }
        con.setRequestMethod(http_method);
        con.setDoInput(true);
        con.setDoOutput(true);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(con.getOutputStream()));
        pw.println(request);
        pw.flush();
        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        result = new JsonParser().parse(br);
        if(result.getAsJsonObject().has("error")){
            tr++;
            if(tr==4){
            throw new HosterNotFoundException(variables.get("url"));
            }else{
                try {
                    Thread.sleep(250L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                fetch();
            }
        }
    }

    public JsonElement getResult() {
        return result;
    }

}

class EmptyRequest extends Request {

    public EmptyRequest() {
        super(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.jectrum.VideoDownload.Actions.Request#addVariable(java.lang.String,
     * java.lang.String) Do Nothing
     */
    @Override
    public void addVariable(String key, String value) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.jectrum.VideoDownload.Actions.Request#setHttpMethod(java.lang.String)
     * Do Nothing
     */
    @Override
    public void setHttpMethod(String name) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jectrum.VideoDownload.Actions.Request#fetch() Do Nothing
     */
    @Override
    public void fetch() throws MalformedURLException, IOException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jectrum.VideoDownload.Actions.Request#getResult() Do Nothing
     */
    @Override
    public JsonElement getResult() {
        return new JsonObject();
    }

}
package javautils.Html.Extended.Client.Modules;

import javautils.Html.Client;
import javautils.Html.Extended.Client.Module;

public class AlertUserModule implements Module{

    @Override
    public String createModuleFunction() {
        return "function(message){alert(message);}";
    }

    @Override
    public String getName() {
        return "AlertUserModule";
    }

    @Override
    public String[] getRequiredModules() {
        return new String[0];
    }

    public void alertUser(Client c, String message){
        c.send("AlertUserModule " + message);
    }

    @Override
    public String parseArguments(String args) {
        return args;
    }

}

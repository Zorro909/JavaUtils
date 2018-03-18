package javautils.Html.Extended.Client.Modules;

import javautils.Html.Client;
import javautils.Html.Extended.Client.Module;

public class UserInputModule implements Module {

  @Override
  public String createModuleFunction() {
    return "function(message){sendFeedback(window.prompt(message));}";
  }

  @Override
  public String getName() {
    return "UserInputModule";
  }

  @Override
  public String[] getRequiredModules() {
    return new String[0];
  }

  public String userInput(Client c, String message) {
    c.send("UserInputModule " + message);
    try {
      return c.read();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String parseArguments(String args) {
    return args;
  }
}

package javautils.Html.Extended.Tags;

import javautils.Html.HtmlAttribute;

public class LinkTag extends ExtendedTag implements Cloneable{

    public LinkTag(String url, String displayText) {
        super("a");
        addAttribute("href", new HtmlAttribute(url));
        setValue(displayText);
    }

}

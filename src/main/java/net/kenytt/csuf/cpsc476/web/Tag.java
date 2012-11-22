package net.kenytt.csuf.cpsc476.web;

public class Tag {

    public static String generate(String tagName, String body) {
        return "<" + tagName + ">" + body + "</" + tagName + ">";
    }

    public static String input(String type, String name) {
        return input(type, name, "");
    }
    
    public static String input(String type, String name, String value) {
        return "<input type=\"" + type + "\" name=\"" + name + "\" value=\""
                + value + "\" />";
    }
}

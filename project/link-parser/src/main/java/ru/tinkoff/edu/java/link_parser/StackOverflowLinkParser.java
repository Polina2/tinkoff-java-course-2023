package ru.tinkoff.edu.java.link_parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowLinkParser extends LinkParser{

    private static final String PATTERN = "^(https://stackoverflow\\.com/questions/)(\\d{1,8})(.*)";

    public StackOverflowLinkParser(LinkParser successor) {
        this.successor = successor;
    }

    public StackOverflowLinkParser(){
        super();
    }

    @Override
    public String parse(String link) {
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(link);
        if (m.find()){
            return m.group(2);
        } else if (successor != null){
            return successor.parse(link);
        } else
            return null;
    }

}

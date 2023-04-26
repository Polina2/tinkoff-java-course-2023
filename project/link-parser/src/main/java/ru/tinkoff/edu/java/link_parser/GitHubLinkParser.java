package ru.tinkoff.edu.java.link_parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubLinkParser extends LinkParser{

    private static final String PATTERN =
            "^(https://github\\.com/)([A-Za-z\\d](?:[A-Za-z\\d]|-(?=[A-Za-z\\d])){0,38}/([\\w.-]+))(.*)";

    public GitHubLinkParser(LinkParser successor) {
        this.successor = successor;
    }

    public GitHubLinkParser(){
        super();
    }

    @Override
    public String parse(String link) {
        Pattern p = Pattern.compile(PATTERN);
        Matcher m = p.matcher(link);
        if (m.find()) {
            return m.group(2);
        } else if (successor != null){
            return successor.parse(link);
        } else
            return null;
    }

}

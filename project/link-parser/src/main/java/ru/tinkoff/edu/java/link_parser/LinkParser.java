package ru.tinkoff.edu.java.link_parser;

public abstract sealed class LinkParser permits GitHubLinkParser, StackOverflowLinkParser{

    protected LinkParser successor = null;

    public abstract String parse(String link);
}

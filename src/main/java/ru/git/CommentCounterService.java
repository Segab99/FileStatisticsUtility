package ru.git;

import ru.git.interfaces.CommentParser;

public class CommentCounterService {
    private CommentParserFactory commentParserFactory;

    public CommentCounterService() {
        commentParserFactory = new CommentParserFactory();
    }

    public int countComments(String line) {
        CommentParser javaCommentParser = commentParserFactory.createCommentParser("Java");
        CommentParser bashCommentParser = commentParserFactory.createCommentParser("Bash");

        return javaCommentParser.countCommentLines(line) +
                bashCommentParser.countCommentLines(line);
    }
}
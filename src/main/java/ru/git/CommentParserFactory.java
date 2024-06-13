package ru.git;

import ru.git.comment_parsers.BashCommentParser;
import ru.git.comment_parsers.JavaCommentParser;
import ru.git.interfaces.CommentParser;

public class CommentParserFactory {
    public CommentParser createCommentParser(String type) {
        if (type.equals("Java")) {
            return new JavaCommentParser();
        } else if (type.equals("Bash")) {
            return new BashCommentParser();
        }
        throw new IllegalArgumentException("Unknown comment type");
    }
}

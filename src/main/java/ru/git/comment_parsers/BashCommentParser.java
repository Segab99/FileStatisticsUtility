package ru.git.comment_parsers;

import ru.git.interfaces.CommentParser;

/**
 *   Реализует интерфейс CommentParser для определения однострочных комментариев в Bash скриптах.
 */
public class BashCommentParser implements CommentParser {
    @Override
    public int countCommentLines(String line) {
        if (line.startsWith("#")) return 1;
        return 0;
    }
}

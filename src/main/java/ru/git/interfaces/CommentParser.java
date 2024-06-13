package ru.git.interfaces;

/**
 *    Определят метод для подсчета комментариев файле.
 */
public interface CommentParser {
    int countCommentLines(String line);
}

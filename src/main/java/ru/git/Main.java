package ru.git;

import ru.git.formatters.JsonOutputFormatter;
import ru.git.formatters.PlainOutputFormatter;
import ru.git.formatters.XMLOutputFormatter;
import ru.git.interfaces.OutputFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Обработка аргументов командной строки
        String path = args[0];
        boolean recursive = false;
        int maxDepth = Integer.MAX_VALUE;
        int threadCount = 1;
        Set<String> includeExtensions = new HashSet<>();
        Set<String> excludeExtensions = new HashSet<>();
        String outputFormat = "plain";
        OutputFormatter outputFormatter;

        //Разбор параметров
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--recursive":
                    recursive = true;
                    break;
                case "--max-depth=":
                    maxDepth = Integer.parseInt(args[i + 1]);
                    i++;
                    break;
                case "--thread=":
                    threadCount = Integer.parseInt(args[i + 1]);
                    i++;
                    break;
                case "--include-ext=":
                    includeExtensions.addAll(Arrays.asList(args[i + 1].split(",")));
                    i++;
                    break;
                case "--exclude-ext=":
                    excludeExtensions.addAll(Arrays.asList(args[i + 1].split(",")));
                    i++;
                    break;
                case "--output=":
                    outputFormat = args[i + 1];
                    i++;
                    break;
            }
        }

        // Вывод статистики в соответствии с форматом вывода
        switch (outputFormat) {
            case "plain":
                // Вывод статистики в текстовом формате
                outputFormatter = new PlainOutputFormatter();
                break;
            case "xml":
                // Генерация XML-отчета
                outputFormatter = new XMLOutputFormatter();
                break;
            case "json":
                // Вывод статистики в JSON
                outputFormatter = new JsonOutputFormatter();
                break;
            default:
                throw new IllegalStateException("Неопознаный параметр: " + outputFormat);
        }
        CommentCounterService commentCounterService = new CommentCounterService();
        FileProcessorImpl fileProcessor = new FileProcessorImpl(outputFormatter, recursive, maxDepth, threadCount,
                includeExtensions, excludeExtensions, commentCounterService);
        fileProcessor.processFiles(path);
    }
}

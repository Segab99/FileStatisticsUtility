package ru.git;

import ru.git.interfaces.FileProcessor;
import ru.git.interfaces.OutputFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *   - Реализует интерфейс FileProcessor.
 *   - Отвечает за обход файлов, сбор статистики и вывод результатов в заданном формате.
 */
public class FileProcessorImpl implements FileProcessor {
    private OutputFormatter outputFormatter;
    private boolean recursive;
    private int maxDepth;
    private int threadCount;
    private Set<String> includeExtensions;
    private Set<String> excludeExtensions;
    private CommentCounterService commentCounterService;

    /**
     * @param outputFormatter Класс для форматирования статистики в различных форматах (plain, xml, json)
     * @param recursive Определяет, нужно ли выполнять обход дерева рекурсивно
     * @param maxDepth Глубина рекурсивного обхода
     * @param threadCount Количество потоков используемого для обхода
     * @param includeExtensions Расширения файлов, которые надо обрабатывать
     * @param excludeExtensions Расширения файлов, которые не надо обрабатывать
     * @param commentCounterService Класс, отвечающий за подсчет комментариев в файле
     */
    public FileProcessorImpl(OutputFormatter outputFormatter,
                             boolean recursive, int maxDepth, int threadCount, Set<String> includeExtensions,
            Set<String> excludeExtensions, CommentCounterService commentCounterService) {
        this.commentCounterService = commentCounterService;
        this.outputFormatter = outputFormatter;
        this.recursive = recursive;
        this.maxDepth = maxDepth;
        this.threadCount = threadCount;
        this.includeExtensions = includeExtensions;
        this.excludeExtensions = excludeExtensions;
    }

    /**
     * @param directoryPath путь до директории
     */
    @Override
    public void processFiles(String directoryPath) {
        File rootDir = new File(directoryPath);
        Map<String, FileStatistics> statisticsMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        if (recursive) {
            traverseDirectoryWithRecursive(rootDir, executor, 0, statisticsMap);
        } else {
            traverseDirectory(rootDir, executor, statisticsMap);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // ждём, когда остановятся все потоки
        }
        outputFormatter.printFileStatistic(statisticsMap);
    }

    /**
     * @param directory текущая директория
     * @param executor ExecutorService
     * @param depth глибина обхода рекурсивно
     * @param statisticsMap статистика по файлам, в разрезе расширений
     */
    //Обход дерева каталогов с рекурсией
    private void traverseDirectoryWithRecursive(File directory, ExecutorService executor,
                                                int depth, Map<String, FileStatistics> statisticsMap) {
        if (depth > maxDepth) {
            return;
        }
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Рекурсивный вызов для обхода подкаталогов
                        traverseDirectoryWithRecursive(file, executor, depth + 1, statisticsMap);
                    } else {
                        String fileExtension = getFileExtension(file);
                        if (needProcessFile(fileExtension)) {
                            if (!statisticsMap.containsKey(fileExtension)) {
                                statisticsMap.put(fileExtension, new FileStatistics());
                            }
                            // Запуск нового потока для обработки файла
                            executor.execute(() -> analyzeFile(file, statisticsMap.get(fileExtension)));
                        }
                    }
                }
            }
        }
    }

    /**
     * @param directory текущая директория
     * @param executor ExecutorService
     * @param statisticsMap статистика по файлам, в разрезе расширений
     */
    //Обход дерева каталогов без рекурсии
    private void traverseDirectory(File directory, ExecutorService executor, Map<String, FileStatistics> statisticsMap) {
        Queue<File> directoriesToTraverse = new ArrayDeque<>();
        directoriesToTraverse.add(directory);

        while (!directoriesToTraverse.isEmpty()) {
            File currentDir = directoriesToTraverse.poll();
            File[] files = currentDir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        directoriesToTraverse.add(file);
                    } else {
                        String fileExtension = getFileExtension(file);
                        if (needProcessFile(fileExtension)) {
                            if (!statisticsMap.containsKey(fileExtension)) {
                                statisticsMap.put(fileExtension, new FileStatistics());
                            }
                            // Запуск нового потока для обработки файла
                            executor.execute(() -> analyzeFile(file, statisticsMap.get(fileExtension)));
                        }
                    }
                }
            }
        }
    }

    //метод анализа файла
    private void analyzeFile(File file, FileStatistics statistics) {
        statistics.incrementFileSize(file.length());
        statistics.incrementFileCount();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                statistics.incrementTotalLines();
                if (!line.isEmpty()) {
                    statistics.incrementNonEmptyLines();
                }
                statistics.sumCommentLines(commentCounterService.countComments(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Обработка файла: " + file.getName() + " в потоке " + Thread.currentThread().getName());
    }

    //метод определения расширения файла
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    //метод определения необходимости обработки указанного расширения файла
    private boolean needProcessFile(String fileExtension) {
        if (excludeExtensions.contains(fileExtension)) {
            return false;
        }
        return includeExtensions.isEmpty() || includeExtensions.contains(fileExtension);
    }
}

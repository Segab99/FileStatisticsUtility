package ru.git.formatters;

import ru.git.FileStatistics;
import ru.git.interfaces.OutputFormatter;

import java.util.Map;

/**
 *  Реализует интерфейс OutputFormatter для текстового формата вывода.
 */
public class PlainOutputFormatter implements OutputFormatter {
    @Override
    public void printFileStatistic(Map<String, FileStatistics> fileStatisticsMap) {
        for (Map.Entry<String, FileStatistics> entry : fileStatisticsMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("|- ").append(entry.getKey()).append(": ");
            System.out.print(sb.toString());
            System.out.println(entry.getValue());
        }
    }
}

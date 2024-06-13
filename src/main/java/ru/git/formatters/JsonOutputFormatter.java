package ru.git.formatters;

import org.json.JSONObject;
import ru.git.FileStatistics;
import ru.git.interfaces.OutputFormatter;

import java.util.Map;

/**
 *  Реализует интерфейс OutputFormatter для вывода статистики в формате JSON.
 */
public class JsonOutputFormatter  implements OutputFormatter {
    @Override
    public void printFileStatistic(Map<String, FileStatistics> fileStatisticsMap) {
        JSONObject json = new JSONObject(fileStatisticsMap);
        System.out.println(json.toString(2));
    }
}

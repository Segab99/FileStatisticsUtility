package ru.git.interfaces;

import ru.git.FileStatistics;

import java.util.Map;

/**
 *   Содержит методы для форматирования статистики в различные форматы (plain, xml, json).
 */
public interface OutputFormatter {
    void printFileStatistic(Map<String, FileStatistics> fileStatisticsMap);
}

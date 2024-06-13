package ru.git;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Класс, который используется для обертывания FileStatistics при маршалинге в XML
 */
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileStatisticsMapWrapper {
    @XmlElement(name = "statistic")
    private Map<String, FileStatistics> map;

    public Map<String, FileStatistics> getMap() {
        return map;
    }

    public void setMap(Map<String, FileStatistics> map) {
        this.map = map;
    }
}

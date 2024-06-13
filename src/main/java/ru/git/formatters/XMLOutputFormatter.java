package ru.git.formatters;

import ru.git.FileStatistics;
import ru.git.FileStatisticsMapWrapper;
import ru.git.interfaces.OutputFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Map;

/**
 *  Реализует интерфейс OutputFormatter для вывода статистики в формате JSON.
 */
public class XMLOutputFormatter implements OutputFormatter {
    @Override
    public void printFileStatistic(Map<String, FileStatistics> fileStatisticsMap) {
        String xmlString = convertHashMapToXML(fileStatisticsMap);
        if (xmlString != null) System.out.println(xmlString);
    }

    private String convertHashMapToXML(Map<String, FileStatistics> map) {
        try {
            JAXBContext context = JAXBContext.newInstance(FileStatisticsMapWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            FileStatisticsMapWrapper wrapper = new FileStatisticsMapWrapper();
            wrapper.setMap(map);

            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package eu.itreegroup.spark.application.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class BirtReportGenerator {

    private final static Logger logger = Logger.getLogger(BirtReportGenerator.class);

    /**
     * 
     * @param reportTemplateFileName
     * @param type
     * @param reportXml
     * @param reportOutput
     * @throws IOException
     */
    public void generateReport(String reportTemplateFileName, ReportType type, InputStream reportXml, OutputStream reportOutput) throws IOException {
        Properties properties = new Properties();
        generateReport(reportTemplateFileName, type, reportXml, reportOutput, properties);
    }

    /**
     * 
     * @param reportTemplateFileName - absolute report template file name
     * @param type - report output type
     * @param reportXml - data in xml format which will be used to fill report template
     * @param reportOutput - output stream to write generated report to
     * @throws IOException
     */
    public void generateReport(String reportTemplateFileName, ReportType type, InputStream xmlFileSrc, OutputStream reportOutput, Properties properties) throws IOException {
        if (xmlFileSrc == null) {
            throw new NullPointerException("XmlFileSrc has to be specified!");
        }
        if (reportOutput == null) {
            throw new NullPointerException("ReportOutput has to be specified!");
        }
        if (null != reportTemplateFileName && !reportTemplateFileName.isEmpty()) {
            throw new NullPointerException("ReportTemplateFileName has to be specified!");
        }
        logger.info("---- START");
        File xmlFile = ReportHelper.copySourceFile(xmlFileSrc);
        String xmlFileUrl = xmlFile.getAbsolutePath();
        try {
            URL url = getReportUrl(type, xmlFileUrl, reportTemplateFileName, properties);
            logger.info("---------------------------");
            logger.info("Calling Birt report Engine!");
            logger.info("URL = " + url);

            URLConnection c = url.openConnection();
            c.connect();
            ReportHelper.out(c.getInputStream(), reportOutput);
        } finally {
            xmlFile.delete();
        }
        logger.info("---- FINISH");
    }

    private static void appendProperties(StringBuilder builder, Properties properties) throws UnsupportedEncodingException {
        for (Object key : properties.keySet()) {
            builder.append("&param:");
            builder.append(key.toString());
            builder.append("=");
            Object value = properties.get(key);
            builder.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
    }

    public URL getReportUrl(ReportType type, String xmlFilePath, String templatePath) {
        Properties properties = new Properties();
        return getReportUrl(type, xmlFilePath, templatePath, properties);
    }

    private URL getReportUrl(ReportType type, String xmlFilePath, String templatePath, Properties properties) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(getReportEngineUrl());
            builder.append("/report?");
            builder.append("type=");
            builder.append(type.getCode());
            builder.append("&param:xml_file=");
            builder.append(URLEncoder.encode(xmlFilePath, "UTF-8"));
            appendProperties(builder, properties);
            builder.append("&report=");
            builder.append(URLEncoder.encode(templatePath, "UTF-8"));
            String urlStr = builder.toString();
            URL url = new URL(urlStr);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getReportEngineUrl();

}

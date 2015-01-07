package eu.itreegroup.spark.application.report;

public enum ReportType {

    XLS("xls"), XLSX("xlsx"), PDF("pdf"), DOC("doc"), DOCX("docx"), HTML("html"), PPT("ppt");

    private String code;

    private ReportType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

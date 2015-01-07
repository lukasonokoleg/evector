package eu.itreegroup.spark.application.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReportHelper {

    public static String MIME_TYPE_PDF = "application/pdf";
    private static final String PREFIX = "PREFIX";
    private static final String SUFFIX = "SUFFIX";

    public static File copySourceFile(InputStream inputStream, File parent, String fileName) throws IOException {
        File retVal = new File(parent, fileName);
        OutputStream outPutStream = new FileOutputStream(retVal);
        out(inputStream, outPutStream);
        return retVal;
    }

    public static File copySourceFile(InputStream inputStream) throws IOException {
        File retVal = createTempFile();
        OutputStream outPutStream = new FileOutputStream(retVal);
        out(inputStream, outPutStream);
        return retVal;
    }

    public static void out(InputStream input, OutputStream output) throws IOException {
        byte[] buff = new byte[1024];
        int l = 0;
        while ((l = input.read(buff)) > 0) {
            output.write(buff, 0, l);
        }
    }

    public static File createTempFile() throws IOException {
        return File.createTempFile(PREFIX, SUFFIX);
    }
}

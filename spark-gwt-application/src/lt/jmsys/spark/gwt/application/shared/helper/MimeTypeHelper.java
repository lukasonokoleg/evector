package lt.jmsys.spark.gwt.application.shared.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MimeTypeHelper {

    private static Set<String> WORD_MIME_TYPES = new HashSet<String>();
    {
        WORD_MIME_TYPES.add("application/msword");
    }

    private static Set<String> EXCEL_MIME_TYPES = new HashSet<String>();
    {
        EXCEL_MIME_TYPES.add("application/vnd.ms-excel");
    }

    private static Set<String> PDF_MIME_TYPES = new HashSet<String>();
    {
        PDF_MIME_TYPES.add("application/pdf");
    }

    protected static Map<String, String> mimeTypes = new HashMap<String, String>();
    {
        mimeTypes.put("ai", "application/postscript");
        mimeTypes.put("aif", "audio/x-aiff");
        mimeTypes.put("aifc", "audio/x-aiff");
        mimeTypes.put("aiff", "audio/x-aiff");
        mimeTypes.put("asc", "text/plain");
        mimeTypes.put("asf", "video/x.ms.asf");
        mimeTypes.put("asx", "video/x.ms.asx");
        mimeTypes.put("au", "audio/basic");
        mimeTypes.put("avi", "video/x-msvideo");
        mimeTypes.put("bcpio", "application/x-bcpio");
        mimeTypes.put("bin", "application/octet-stream");
        mimeTypes.put("cab", "application/x-cabinet");
        mimeTypes.put("cdf", "application/x-netcdf");
        mimeTypes.put("class", "application/java-vm");
        mimeTypes.put("cpio", "application/x-cpio");
        mimeTypes.put("cpt", "application/mac-compactpro");
        mimeTypes.put("crt", "application/x-x509-ca-cert");
        mimeTypes.put("csh", "application/x-csh");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("csv", "text/comma-separated-values");
        mimeTypes.put("dcr", "application/x-director");
        mimeTypes.put("dir", "application/x-director");
        mimeTypes.put("dll", "application/x-msdownload");
        mimeTypes.put("dms", "application/octet-stream");
        mimeTypes.put("doc", "application/msword");
        mimeTypes.put("dtd", "application/xml-dtd");
        mimeTypes.put("dvi", "application/x-dvi");
        mimeTypes.put("dxr", "application/x-director");
        mimeTypes.put("eps", "application/postscript");
        mimeTypes.put("etx", "text/x-setext");
        mimeTypes.put("exe", "application/octet-stream");
        mimeTypes.put("ez", "application/andrew-inset");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("gtar", "application/x-gtar");
        mimeTypes.put("gz", "application/gzip");
        mimeTypes.put("gzip", "application/gzip");
        mimeTypes.put("hdf", "application/x-hdf");
        mimeTypes.put("htc", "text/x-component");
        mimeTypes.put("hqx", "application/mac-binhex40");
        mimeTypes.put("html", "text/html");
        mimeTypes.put("htm", "text/html");
        mimeTypes.put("ice", "x-conference/x-cooltalk");
        mimeTypes.put("ief", "image/ief");
        mimeTypes.put("iges", "model/iges");
        mimeTypes.put("igs", "model/iges");
        mimeTypes.put("jar", "application/java-archive");
        mimeTypes.put("java", "text/plain");
        mimeTypes.put("jnlp", "application/x-java-jnlp-file");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("jpe", "image/jpeg");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("js", "application/x-javascript");
        mimeTypes.put("jsp", "text/plain");
        mimeTypes.put("kar", "audio/midi");
        mimeTypes.put("latex", "application/x-latex");
        mimeTypes.put("lha", "application/octet-stream");
        mimeTypes.put("lzh", "application/octet-stream");
        mimeTypes.put("man", "application/x-troff-man");
        mimeTypes.put("mathml", "application/mathml+xml");
        mimeTypes.put("me", "application/x-troff-me");
        mimeTypes.put("mesh", "model/mesh");
        mimeTypes.put("mid", "audio/midi");
        mimeTypes.put("midi", "audio/midi");
        mimeTypes.put("mif", "application/vnd.mif");
        mimeTypes.put("mol", "chemical/x-mdl-molfile");
        mimeTypes.put("movie", "video/x-sgi-movie");
        mimeTypes.put("mov", "video/quicktime");
        mimeTypes.put("mp2", "audio/mpeg");
        mimeTypes.put("mp3", "audio/mpeg");
        mimeTypes.put("mpeg", "video/mpeg");
        mimeTypes.put("mpe", "video/mpeg");
        mimeTypes.put("mpga", "audio/mpeg");
        mimeTypes.put("mpg", "video/mpeg");
        mimeTypes.put("ms", "application/x-troff-ms");
        mimeTypes.put("msh", "model/mesh");
        mimeTypes.put("msi", "application/octet-stream");
        mimeTypes.put("nc", "application/x-netcdf");
        mimeTypes.put("oda", "application/oda");
        mimeTypes.put("ogg", "application/ogg");
        mimeTypes.put("pbm", "image/x-portable-bitmap");
        mimeTypes.put("pdb", "chemical/x-pdb");
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("pgm", "image/x-portable-graymap");
        mimeTypes.put("pgn", "application/x-chess-pgn");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("pnm", "image/x-portable-anymap");
        mimeTypes.put("ppm", "image/x-portable-pixmap");
        mimeTypes.put("ppt", "application/vnd.ms-powerpoint");
        mimeTypes.put("ps", "application/postscript");
        mimeTypes.put("qt", "video/quicktime");
        mimeTypes.put("ra", "audio/x-pn-realaudio");
        mimeTypes.put("ra", "audio/x-realaudio");
        mimeTypes.put("ram", "audio/x-pn-realaudio");
        mimeTypes.put("ras", "image/x-cmu-raster");
        mimeTypes.put("rdf", "application/rdf+xml");
        mimeTypes.put("rgb", "image/x-rgb");
        mimeTypes.put("rm", "audio/x-pn-realaudio");
        mimeTypes.put("roff", "application/x-troff");
        mimeTypes.put("rpm", "application/x-rpm");
        mimeTypes.put("rpm", "audio/x-pn-realaudio");
        mimeTypes.put("rtf", "application/rtf");
        mimeTypes.put("rtx", "text/richtext");
        mimeTypes.put("ser", "application/java-serialized-object");
        mimeTypes.put("sgml", "text/sgml");
        mimeTypes.put("sgm", "text/sgml");
        mimeTypes.put("sh", "application/x-sh");
        mimeTypes.put("shar", "application/x-shar");
        mimeTypes.put("silo", "model/mesh");
        mimeTypes.put("sit", "application/x-stuffit");
        mimeTypes.put("skd", "application/x-koan");
        mimeTypes.put("skm", "application/x-koan");
        mimeTypes.put("skp", "application/x-koan");
        mimeTypes.put("skt", "application/x-koan");
        mimeTypes.put("smi", "application/smil");
        mimeTypes.put("smil", "application/smil");
        mimeTypes.put("snd", "audio/basic");
        mimeTypes.put("spl", "application/x-futuresplash");
        mimeTypes.put("src", "application/x-wais-source");
        mimeTypes.put("sv4cpio", "application/x-sv4cpio");
        mimeTypes.put("sv4crc", "application/x-sv4crc");
        mimeTypes.put("svg", "image/svg+xml");
        mimeTypes.put("swf", "application/x-shockwave-flash");
        mimeTypes.put("t", "application/x-troff");
        mimeTypes.put("tar", "application/x-tar");
        mimeTypes.put("tar.gz", "application/x-gtar");
        mimeTypes.put("tcl", "application/x-tcl");
        mimeTypes.put("tex", "application/x-tex");
        mimeTypes.put("texi", "application/x-texinfo");
        mimeTypes.put("texinfo", "application/x-texinfo");
        mimeTypes.put("tgz", "application/x-gtar");
        mimeTypes.put("tiff", "image/tiff");
        mimeTypes.put("tif", "image/tiff");
        mimeTypes.put("tr", "application/x-troff");
        mimeTypes.put("tsv", "text/tab-separated-values");
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("ustar", "application/x-ustar");
        mimeTypes.put("vcd", "application/x-cdlink");
        mimeTypes.put("vrml", "model/vrml");
        mimeTypes.put("vxml", "application/voicexml+xml");
        mimeTypes.put("wav", "audio/x-wav");
        mimeTypes.put("wbmp", "image/vnd.wap.wbmp");
        mimeTypes.put("wmlc", "application/vnd.wap.wmlc");
        mimeTypes.put("wmlsc", "application/vnd.wap.wmlscriptc");
        mimeTypes.put("wmls", "text/vnd.wap.wmlscript");
        mimeTypes.put("wml", "text/vnd.wap.wml");
        mimeTypes.put("wrl", "model/vrml");
        mimeTypes.put("wtls-ca-certificate", "application/vnd.wap.wtls-ca-certificate");
        mimeTypes.put("xbm", "image/x-xbitmap");
        mimeTypes.put("xht", "application/xhtml+xml");
        mimeTypes.put("xhtml", "application/xhtml+xml");
        mimeTypes.put("xls", "application/vnd.ms-excel");
        mimeTypes.put("xml", "application/xml");
        mimeTypes.put("xpm", "image/x-xpixmap");
        mimeTypes.put("xpm", "image/x-xpixmap");
        mimeTypes.put("xsl", "application/xml");
        mimeTypes.put("xslt", "application/xslt+xml");
        mimeTypes.put("xul", "application/vnd.mozilla.xul+xml");
        mimeTypes.put("xwd", "image/x-xwindowdump");
        mimeTypes.put("xyz", "chemical/x-xyz");
        mimeTypes.put("z", "application/compress");
        mimeTypes.put("zip", "application/zip");
    }

    public static boolean isMsExcel(String mimeType) {
        boolean retVal = hasMimeType(mimeType, EXCEL_MIME_TYPES);
        return retVal;
    }

    public static boolean isMsWord(String mimeType) {
        boolean retVal = hasMimeType(mimeType, WORD_MIME_TYPES);
        return retVal;
    }

    public static boolean isPdf(String mimeType) {
        boolean retVal = hasMimeType(mimeType, PDF_MIME_TYPES);
        return retVal;
    }

    private static boolean hasMimeType(String mimeType, Set<String> store) {
        boolean retVal = false;
        if (mimeType != null && store != null) {
            retVal = store.contains(mimeType);
        }
        return retVal;
    }
}

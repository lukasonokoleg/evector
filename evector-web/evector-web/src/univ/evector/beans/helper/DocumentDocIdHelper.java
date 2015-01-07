package univ.evector.beans.helper;

import univ.evector.beans.Document;

public class DocumentDocIdHelper {

    public static boolean hasDocId(Document document) {
        boolean retVal = document != null && document.getDoc_id() != null;
        return retVal;
    }

}

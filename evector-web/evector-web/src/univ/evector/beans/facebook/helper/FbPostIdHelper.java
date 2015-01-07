package univ.evector.beans.facebook.helper;

import univ.evector.beans.facebook.FbPost;

public class FbPostIdHelper {

    public static boolean hasId(FbPost post) {
        boolean retVal = post != null && post.getPst_id() != null;
        return retVal;
    }

}

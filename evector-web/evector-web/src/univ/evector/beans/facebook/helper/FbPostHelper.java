package univ.evector.beans.facebook.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import univ.evector.beans.facebook.FbPost;
import facebook4j.Post;
import facebook4j.ResponseList;

public class FbPostHelper {

    public static List<FbPost> convert(ResponseList<Post> postResponse) {
        List<FbPost> retVal = new ArrayList<>();
        if (postResponse != null) {
            List<FbPost> tmpRetVal = convert(postResponse.iterator());
            if (tmpRetVal != null) {
                retVal.addAll(tmpRetVal);
            }
        }
        return retVal;
    }

    public static List<FbPost> convert(Iterator<Post> postIterator) {
        List<FbPost> retVal = new ArrayList<>();
        if (postIterator != null) {
            Post post = null;
            FbPost fbPost = null;
            while (postIterator.hasNext()) {
                post = postIterator.next();
                fbPost = convert(post);
                if (fbPost != null) {
                    retVal.add(fbPost);
                }
            }
        }
        return retVal;
    }

    public static FbPost convert(Post post) {
        FbPost retVal = null;
        if (post != null) {
            retVal = new FbPost();

            Date pst_date = post.getCreatedTime();
            String pst_fb_id = post.getId();
            String pst_message = post.getMessage();

            retVal.setPst_date(pst_date);
            retVal.setPst_fb_id(pst_fb_id);
            retVal.setPst_message(pst_message);
        }

        return retVal;
    }
}

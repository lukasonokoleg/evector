package eu.itreegroup.spark.application.helper;

import org.junit.Assert;
import org.junit.Test;

import eu.itreegroup.spark.application.bean.Role;
import eu.itreegroup.spark.application.bean.UserBean;
import eu.itreegroup.spark.application.helper.EqualsHelper.EqualsWrapper;

public class EqualsHelperTest {
    
    @Test
    public void equalsScalarsSameEmpty() throws Exception {
        UserBean user1 = new UserBean();
        UserBean user2 = new UserBean();
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(w1.equalsScalars(w2));
        Assert.assertTrue(w2.equalsScalars(w1));
        Assert.assertTrue(w1.equalsCollections(w2));
        Assert.assertTrue(w2.equalsCollections(w1));
        Assert.assertTrue(EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
    
    @Test
    public void equalsScalarsSameCollectionsDiff() throws Exception {
        UserBean user1 = new UserBean();        
        UserBean user2 = new UserBean();
        user1.setEmail("a");
        user2.setEmail("a");
        user1.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return "r";
            }
        }});
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(w1.equalsScalars(w2));
        Assert.assertTrue(w2.equalsScalars(w1));
        Assert.assertTrue(!w1.equalsCollections(w2));
        Assert.assertTrue(!w2.equalsCollections(w1));
        Assert.assertTrue(!EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
    
    @Test
    public void equalsScalarsDiffCollectionsNull() throws Exception {
        UserBean user1 = new UserBean();        
        UserBean user2 = new UserBean();
        user1.setEmail("b");
        user2.setEmail("a");
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(!w1.equalsScalars(w2));
        Assert.assertTrue(!w2.equalsScalars(w1));
        Assert.assertTrue(w1.equalsCollections(w2));
        Assert.assertTrue(w2.equalsCollections(w1));
        Assert.assertTrue(!EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
       
   
    @Test
    public void equalsScalarsDiffCollectionsDiff() throws Exception {
        UserBean user1 = new UserBean();        
        UserBean user2 = new UserBean();
        user1.setEmail("a");
        user2.setEmail(null);
        user1.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return "r";
            }
        }});
        
        user2.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return null;
            }
        }});
        
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(!w1.equalsScalars(w2));
        Assert.assertTrue(!w2.equalsScalars(w1));
        Assert.assertTrue(!w1.equalsCollections(w2));
        Assert.assertTrue(!w2.equalsCollections(w1));
        Assert.assertTrue(!EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
    
    @Test
    public void equalsScalarsDiffCollectionsDiff2() throws Exception {
        UserBean user1 = new UserBean();        
        UserBean user2 = new UserBean();
        user1.setEmail("a");
        user2.setEmail("a");
        user1.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return "r";
            }
        }});
        
        user2.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return "rr";
            }
        }});
        
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(w1.equalsScalars(w2));
        Assert.assertTrue(w2.equalsScalars(w1));
        Assert.assertTrue(!w1.equalsCollections(w2));
        Assert.assertTrue(!w2.equalsCollections(w1));
        Assert.assertTrue(!EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
    
    @Test
    public void equalsScalarsDiffCollectionsDiff3() throws Exception {
        UserBean user1 = new UserBean();        
        UserBean user2 = new UserBean();
        user1.setEmail("a");
        user2.setEmail(null);
        user1.setRoles(new Role[]{new Role() {
            
            @Override
            public String asString() {
                return "r";
            }
        }});
        
        user2.setRoles(new Role[]{null});
        
        EqualsWrapper w1 = new EqualsWrapper(user1);
        EqualsWrapper w2 = new EqualsWrapper(user2);
        Assert.assertTrue(!w1.equalsScalars(w2));
        Assert.assertTrue(!w2.equalsScalars(w1));
        Assert.assertTrue(!w1.equalsCollections(w2));
        Assert.assertTrue(!w2.equalsCollections(w1));
        Assert.assertTrue(!EqualsHelper.equalsIgnoreOrder(user1, user2));
    }
    
    @Test
    public void equalsCollections() throws Exception {
        EqualsWrapper w1 = new EqualsWrapper(new String[]{"a"});
        EqualsWrapper w2 = new EqualsWrapper(new String[]{"b"});
        Assert.assertTrue(!w1.equals(w2));
    }
    
    @Test
    public void equalsScalars() throws Exception {
        EqualsWrapper w1 = new EqualsWrapper("a");
        EqualsWrapper w2 = new EqualsWrapper("b");
        Assert.assertTrue(!w1.equals(w2));
    }

}

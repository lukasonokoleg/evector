package com.google.gwt.user.client.rpc.core.java.sql;

import java.sql.Date;

import com.google.gwt.user.client.rpc.CustomFieldSerializer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;


public class Date_CustomFieldSerializer extends CustomFieldSerializer<Date>{
    
    public static Date instantiate(SerializationStreamReader streamReader) throws SerializationException {
        return new Date(0);
    }
    
    public static boolean hasCustomInstantiate() {
        return true;
    }
    
    public static void serialize(SerializationStreamWriter streamWriter, Date instance) throws SerializationException {        
        com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer.serialize(streamWriter, instance);
    }
    
    public static void deserialize(SerializationStreamReader streamReader, Date instance) throws SerializationException {
        com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer.deserialize(streamReader, instance);
    }
    
    @Override
    public Date instantiateInstance(SerializationStreamReader streamReader) throws SerializationException {
        return instantiate(streamReader);
    }
    
    @Override
    public boolean hasCustomInstantiateInstance() {
        return hasCustomInstantiate();
    }
    

    @Override
    public void deserializeInstance(SerializationStreamReader streamReader, Date instance) throws SerializationException {
        deserialize(streamReader, instance);
    }
    
    @Override
    public void serializeInstance(SerializationStreamWriter streamWriter, Date instance) throws SerializationException {        
        serialize(streamWriter, instance)   ;
    }

}

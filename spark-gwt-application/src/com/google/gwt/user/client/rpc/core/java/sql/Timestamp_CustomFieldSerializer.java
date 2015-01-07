package com.google.gwt.user.client.rpc.core.java.sql;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.CustomFieldSerializer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;


public class Timestamp_CustomFieldSerializer extends CustomFieldSerializer<Timestamp>{
    
    public static Timestamp instantiate(SerializationStreamReader streamReader) throws SerializationException {
        return new Timestamp(0);
    }
    
    public static boolean hasCustomInstantiate() {
        return true;
    }
    
    public static void serialize(SerializationStreamWriter streamWriter, Timestamp instance) throws SerializationException {        
        com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer.serialize(streamWriter, instance);
    }
    
    public static void deserialize(SerializationStreamReader streamReader, Timestamp instance) throws SerializationException {
        com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer.deserialize(streamReader, instance);
    }
    
    @Override
    public Timestamp instantiateInstance(SerializationStreamReader streamReader) throws SerializationException {
        return instantiate(streamReader);
    }
    
    @Override
    public boolean hasCustomInstantiateInstance() {
        return hasCustomInstantiate();
    }
    

    @Override
    public void deserializeInstance(SerializationStreamReader streamReader, Timestamp instance) throws SerializationException {
        deserialize(streamReader, instance);
    }
    
    @Override
    public void serializeInstance(SerializationStreamWriter streamWriter, Timestamp instance) throws SerializationException {        
        serialize(streamWriter, instance);
    }

}

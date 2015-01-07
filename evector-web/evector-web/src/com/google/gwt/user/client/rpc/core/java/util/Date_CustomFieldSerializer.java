package com.google.gwt.user.client.rpc.core.java.util;

import java.util.Date;

import com.google.gwt.user.client.rpc.CustomFieldSerializer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;


public class Date_CustomFieldSerializer extends CustomFieldSerializer<Date>{
    
    public static void serialize(SerializationStreamWriter streamWriter, Date instance) throws SerializationException {        
        streamWriter.writeInt(instance.getTimezoneOffset());
        streamWriter.writeLong(instance.getTime());        
    }
    
    public static void deserialize(SerializationStreamReader streamReader, Date instance) throws SerializationException {
        int offset = streamReader.readInt();
        long time = streamReader.readLong();        
        Date d = new Date(time);
        int currentOffset = d.getTimezoneOffset();
        long diff = (offset - currentOffset) * 60 * 1000;       
        instance.setTime(time - diff);
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

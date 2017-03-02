package com.mpyf.lening.interfaces.http;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String serialize(Object object)throws Exception{
        Writer write = new StringWriter();
        MapperFeature arg0=MapperFeature.AUTO_DETECT_GETTERS;
		objectMapper.configure(arg0, false);
        objectMapper.writeValue(write, object);
        return write.toString();
    }


    @SuppressWarnings("unchecked")
	public static <T> T deserialize(String json, Class<T> clazz)throws Exception {
        Object object = null;
        object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        return (T) object;
    }
    public static JavaType getCollectionType(ObjectMapper mapper,Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
    public static <T> List  deserializeList(String json, Class<T> clazz)throws Exception {
        List object = null;
       // MapperFeature arg0=MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS;
		//objectMapper.configure(arg0, false);
        //object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        object = objectMapper.readValue(json, getCollectionType(objectMapper, List.class, clazz));
        return object;
    }


    public static <T> T deserialize(String json, TypeReference<T> typeRef)throws Exception {
    	//MapperFeature arg0=MapperFeature.AUTO_DETECT_GETTERS;
		//objectMapper.configure(arg0, false);
    	return (T) objectMapper.readValue(json, typeRef);
    }
}
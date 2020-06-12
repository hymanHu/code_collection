package com.thornBird.commons.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @Description: Service Call Body Build Util
 * @author: HymanHu
 * @date: 2019-03-26 08:55:03
 */
public class ServiceCallBodyBuildUtil {
	public final static ObjectMapper OBJECTMAPPER = new ObjectMapper();
	static {
        OBJECTMAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
	
	public static MultiValueMap<String, Object> pojo2MultiValueMap(Object bean) {
        // object to JsonNode
        JsonNode jsonNode = OBJECTMAPPER.convertValue(bean, JsonNode.class);

        // JsonNode to MultiValueMap
        MultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<>();
        Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            if (entry.getValue().isArray()) {
                List<Object> values = new ArrayList<>();
                entry.getValue().forEach(value -> values.add(getValue(value)));
                linkedMultiValueMap.addAll(entry.getKey(), values);
            } else {
                linkedMultiValueMap.add(entry.getKey(), getValue(entry.getValue()));
            }
        }

        return linkedMultiValueMap;
    }
	
	private static Object getValue(JsonNode node) {
        if (node.isNull()) {
            return null;
        } else if (node.isInt()) {
            return node.intValue();
        } else if (node.isBigInteger()) {
            return node.bigIntegerValue();
        } else if (node.isLong()) {
            return node.longValue();
        } else if (node.isDouble()) {
            return node.doubleValue();
        } else if (node.isFloat()) {
            return node.floatValue();
        } else {
            return node.asText();
        }
    }
	
	/**
	 * Convert POJO bean to MultiValueMap, use Function for form data.
	 * @param bean					bean
	 * @param transformFunction		transform function
	 * @return multiValueMap
	 */
	public static MultiValueMap<String, String> pojo2MultiValueMap(Object bean, Function<Object, ObjectNode> transformFunction) {
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.convertValue(transformFunction.apply(bean), new TypeReference<Map<String,String>>() {});
		
		// Map to MultiValueMap
		MultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.forEach(linkedMultiValueMap::add);
		
		return linkedMultiValueMap;
	}
	
	/**
	 * Convert POJO bean to MultiValueMap, use SimpleModule for form data.
	 * The POJO is the complicated object, we will override JsonSerializer, and register the simple module.
	 * @param bean				bean
	 * @param simpleModule		simple module
	 * @return multiValueMap
	 */
	public static MultiValueMap<String, String> pojo2MultiValueMap(Object bean, SimpleModule simpleModule) {
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		if (simpleModule != null) {
			objectMapper.registerModule(simpleModule);
		}
    	Map<String, String> map = objectMapper.convertValue(bean, new TypeReference<Map<String,String>>() {});
    	
    	// Map to MultiValueMap
    	MultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
    	map.forEach(linkedMultiValueMap::add);
    	
    	return linkedMultiValueMap;
	}
	
	/**
	 * Convert POJO bean to JsonString, for json data.
	 * The POJO is the complicated object, we will override JsonSerializer, and register the simple module.
	 * @param bean				bean
	 * @param simpleModule		simple module
	 * @return string
	 */
	public static String pojo2JsonString(Object bean, SimpleModule simpleModule) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (simpleModule != null) {
        	objectMapper.registerModule(simpleModule);
        }

        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
	
	
	/**
	 * build Simple Module
	 * @return
	 */
//	import com.fasterxml.jackson.core.JsonGenerator;
//	import com.fasterxml.jackson.databind.JsonSerializer;
//	import com.fasterxml.jackson.databind.SerializerProvider;
//	public SimpleModule buildSimpleModule() {
//		SimpleModule module = new SimpleModule();
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
//		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//		
//		module.addSerializer(Coupon.class, new JsonSerializer<Coupon>() {
//			@Override
//			public void serialize(Coupon coupon, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//				gen.writeStartObject();
//				gen.writeStringField("internalDescription", coupon.getInternalDescription());
//				gen.writeStringField("couponType", String.valueOf(coupon.getCouponType()));
//				gen.writeStringField("minTotal", String.valueOf(coupon.getMinTotal()));
//				gen.writeStringField("code", coupon.getCode());
//				if (null != coupon.getMaxRedemptions()) {
//					gen.writeStringField("maxRedemptions", String.valueOf(coupon.getMaxRedemptions()));
//				}
//				gen.writeStringField("startDate", sdf.format(coupon.getStart()));
//				if (coupon.getEnd() != null) {
//					gen.writeStringField("endDate", sdf.format(coupon.getEnd()));
//				}
//				gen.writeStringField("maxUses", String.valueOf(coupon.getMaxUses()));
//				gen.writeStringField("limitStoreIds", String.valueOf(coupon.getLimitStoreIds()[0]));
//				gen.writeEndObject();
//			}
//			
//		});
//		
//		return module;
//    }
	
	
	/**
	 * call pojo2MultiValueMap, build Function
	 */
//	MultiValueMap<String, String> body = ShopHelper.pojo2MultiValueMap(coupon, (bean) -> {
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			ObjectNode objectNode = objectMapper.readValue(objectMapper.writeValueAsString(bean), ObjectNode.class);
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
//			sdf.setTimeZone(TimeZone.getTimeZone("PST"));
//			objectNode.put("startDate", sdf.format(coupon.getStart()));
//			if (coupon.getEnd() != null) {
//				objectNode.put("endDate", sdf.format(coupon.getEnd()));
//			}
//			objectNode.put("limitStoreIds", String.valueOf(coupon.getLimitStoreIds()[0]));
//			
//			return objectNode;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		} 
//	});

}

package com.thornBird.base.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.thornBird.base.bean.Book;
import com.thornBird.base.bean.Car;
import com.thornBird.base.bean.People;

/**
 * @Description: Json test
 * @author: HymanHu
 * @date: 2019-01-11 15:53:03  
 */
public class JsonTest {
	
	@JsonSerialize( using = FullDateJsonSerializer.class)
	@JsonProperty("endDate")//这个注解提供了序列化和反序列化过程中该java属性所对应的名称
	@JsonAlias({"end", "endDate"})//这个注解只只在反序列化时起作用，指定该java属性可以接受的更多名称
	private Date test;
	
	public static People initObject() {
		Car car = new Car();
		car.setCost(15.23);
		car.setCount(1);
		car.setMadeInChina(true);
		car.setName("buick");
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setName("Live");
		book.setPublisher("Zoro");
		Book book1 = new Book();
		book1.setName("Live1");
		book1.setPublisher("Zoro1");
		books.add(book);
		books.add(book1);
		People people = new People();
		people.setAge(22);
		people.setId(111);
		people.setMarry(false);
		people.setName("Hyman");
		people.setSalary(321.44);
		people.setBooks(books);
		people.setCar(car);
		people.setMale(null);
		return people;
	}
	
	/**
	 * org.json
	 */
	public static void testOrgJson() {
		System.out.println("=============== org.json ==================");
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("name", "HymanHu");
		jsonObject1.put("age", 20);
		jsonObject1.put("marry", false);
		System.out.println(jsonObject1);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("name", "HymanHu1");
		jsonObject2.put("age", 21);
		jsonObject2.put("marry", true);
		jsonArray.put(0, jsonObject1);
		jsonArray.put(1, jsonObject2);
		jsonArray.put(2, true);
		jsonArray.put(3, "fdsafdsafda");
		jsonArray.put(3, 1);
		System.out.println(jsonArray);
		
		JSONObject jsonObject3 = new JSONObject("{\"name\":\"HymanHu\",\"marry\":false,\"age\":20}");
		JSONArray jsonArray1 = new JSONArray("[{\"name\":\"HymanHu\",\"marry\":false,\"age\":20},{\"name\":\"HymanHu1\",\"marry\":true,\"age\":21},true,1]");
		System.out.println(jsonObject3.getInt("age") + "--" + jsonObject3.getBoolean("marry") + "--" + jsonObject3.getString("name"));
		JSONObject jsonObject4 = new JSONObject(jsonArray1.get(0).toString());
		System.out.println(jsonObject4.getString("name") + "--" + jsonArray1.getBoolean(2) + "--" + jsonArray1.getInt(3));
	}
	
	/**
	 * com.google.code.gson
	 */
	public static void testGJson() {
		System.out.println("=============== gson ==================");
		People people = initObject();
		Gson gson = new Gson();
		String gsonString = gson.toJson(people);
		System.out.println(gsonString);
		People fromJson = gson.fromJson(gsonString, People.class);
		System.out.println(fromJson.getId() + "------" + fromJson.getCar().getCost() + "-----" + fromJson.getBooks().get(0).getPublisher());
	}
	
	/**
	 * com.fasterxml.jackson
	 */
	public static void testJackson() {
		System.out.println("=============== jackson ==================");
		People people = initObject();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jacksonString = objectMapper.writeValueAsString(people);
			System.out.println(jacksonString);
			People fromJson = objectMapper.readValue(jacksonString, People.class);
			System.out.println(fromJson.getId() + "------" + fromJson.getCar().getCost() + "-----" + fromJson.getBooks().get(0).getPublisher());
			
			String temp = "[{\"mainHost\":\"dev.thornBird.com\",\"state\":\"en-us\",\"service.hostName\":\"dev-services.shop.com\",\"service.port\":8085},{\"mainHost\":\"staging.thornBird.com\",\"state\":\"en-us\",\"service.hostName\":\"dev-services.shop.com\",\"service.port\":8085}]";
			JsonNode jsonNode = objectMapper.readTree(temp);
			if (jsonNode != null && jsonNode.isArray()) {
				for (JsonNode environmentJson : jsonNode) {
					System.out.println(environmentJson.toString());
				}
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * com.alibaba.fastjson
	 */
	public static void testFastjson() {
		System.out.println("=============== fastjson ==================");
		People people = initObject();
		String fastjsonString = JSON.toJSONString(people);
		System.out.println(fastjsonString);
		People fromJson = JSON.parseObject(fastjsonString, People.class);
		System.out.println(fromJson.getId() + "------" + fromJson.getCar().getCost() + "-----" + fromJson.getBooks().get(0).getPublisher());
		List<Book> books = people.getBooks();
		fastjsonString = JSON.toJSONString(books);
		System.out.println(fastjsonString);
		List<Book> list = com.alibaba.fastjson.JSONArray.parseArray(fastjsonString, Book.class);
		System.out.println(list.get(1).getPublisher());
		
	}
	
	public static void main(String[] args) {
		JsonTest.testOrgJson();
		JsonTest.testGJson();
		JsonTest.testJackson();
		JsonTest.testFastjson();
	}
}

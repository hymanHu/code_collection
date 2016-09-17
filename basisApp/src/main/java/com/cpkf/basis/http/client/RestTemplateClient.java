package com.cpkf.basis.http.client;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateClient {

	public static void main(String[] args) {
//		String url = "http://172.17.20.112:9999/file";
		String url = "https://darwin.affiliatewindow.com/awin/affiliate/63268/report/performance-over-time/export/graphType/total_value/sort/date/order/asc/interval/1/perPage/25/page/1/dateType/transaction/timezone/Europe-London/affiliateId/63268/month/2014-05/reportName/performance-over-time/dynamicDateRange/Last7Days/start/2014-05-30/end/2014-06-06/showTabs/1/region/GB/regionId/1/exportType/excel";
		RestTemplate client = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
//		form.add("1", "1");
		form.add("email", "malcolmt@shop.com");
		form.add("password", "Catalog01");
		String response = client.postForObject("url", form, String.class);
		System.out.println(response);
	}

}

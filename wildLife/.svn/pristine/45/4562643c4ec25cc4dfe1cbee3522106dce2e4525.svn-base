package com.globits.wl.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.FileHandler;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.globits.wl.domain.Farm;
import com.globits.wl.dto.AnimalMapDto;
import com.globits.wl.dto.DeleteMapByAreaDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmMapDeleteDto;
import com.globits.wl.dto.functiondto.FarmMapDto;
import com.globits.wl.dto.functiondto.MapReturnDto;
import com.google.gson.Gson;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;


public class FarmMapServiceUtil {
	
	@Autowired
	private static Environment env;
	
//	public static String host="http://fao.gisgo.vn";
	public static String host= "http://google.com";
	private static TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            return true;
        }
    };
	public static MapReturnDto createFarmMap(FarmMapDto farmMapDto){		
		try {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			final String url = host+"/farms/createFarm";
//			System.out.println(farmMapDto.toMap());
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(farmMapDto.toMap(), headers);
			ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
			
//			System.out.println(result);
			LinkedHashMap hash = (LinkedHashMap)result.getBody();
			Gson gson = new Gson();
			String json = gson.toJson(hash,LinkedHashMap.class);
			/*System.out.print(json);*/
			MapReturnDto p = gson.fromJson(json, MapReturnDto.class);
			if(p!=null && "ERROR".equals(p.getStatus()) && p.getErrors()!=null && p.getErrors().size()>0 && p.getErrors().get(0).getCode().equals(-2)) {
				return updateFarmMap(farmMapDto);
			}
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}		
	}
	
	public static Object createAnimalMap(AnimalMapDto animalMapDto)	{
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		final String url = host+"/animals/create";
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(animalMapDto.toMap(), headers);
		Object result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
		//System.out.println(result);
		return result;
	}
	
	public static Object updateAnimalMap(AnimalMapDto animalMapDto) {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		final String url = host+"/animals/update";
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(animalMapDto.toMap(), headers);
		Object result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
		//System.out.println(result);
		return result;
	}

	public static MapReturnDto updateFarmMap(FarmMapDto farmMapDto) {
		try {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.print("sslContext Error: KeyManagementException");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.print("sslContext Error: NoSuchAlgorithmException");
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.print("sslContext Error: KeyStoreException");
			}
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);

			final String url = host+"/farms/updateFarm";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
			//System.out.println(farmMapDto.toMap());
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(farmMapDto.toMap(), headers);
			ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);		
//			System.out.println(result);
			LinkedHashMap hash = (LinkedHashMap)result.getBody();
			Gson gson = new Gson();
			String json = gson.toJson(hash,LinkedHashMap.class);
			System.out.print(json);
			MapReturnDto p = gson.fromJson(json, MapReturnDto.class);
			if(p!=null && "ERROR".equals(p.getStatus()) && p.getErrors()!=null && p.getErrors().size()>0 && p.getErrors().get(0).getCode().equals(-3)) {
				return createFarmMap(farmMapDto);
			}
			return p;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Đồng bộ Farm sang bản đồ lỗi "+farmMapDto.getCode());
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static Object deleteFarmMap(FarmMapDeleteDto farmMapDto){
		try {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			final String url = host+"/farms/deleteFarm";

			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(farmMapDto.toMap(), headers);
			//System.out.println(farmMapDto.toMap());
			ResponseEntity<?>  result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);		
			LinkedHashMap hash = (LinkedHashMap)result.getBody();
			Gson gson = new Gson();
			String json = gson.toJson(hash,LinkedHashMap.class);
			System.out.print(json);
			MapReturnDto p = gson.fromJson(json, MapReturnDto.class);		
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private static Object pushFarmAnimalTotal(FarmAnimalTotalDto dto) {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		final String url = host+"/farms/animals";

		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

		Object result = restTemplate.postForObject(url, dto, Object.class);
		/*System.out.println(result);*/
		return result;
	}
	public static Object pushFarmAnimalMap(FarmAnimalTotalDto dto) {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		final String url = host+"/farms/animals";
		HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(dto.toMAP(), headers);

		/*System.out.println(dto.toMAP());*/
		Object result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);	
		/*System.out.println(result);*/
		return result;
	}

	public static void main(String[] args) throws JSONException, ClientProtocolException, IOException {
//		createFarmTest();
//		updateFarmTest();
		deleteFarmTest();
//		pushFarmAnimalTotal();
		/*System.out.println("test");*/
	}
	/** Test API  ------------------------**/
	private static void pushFarmAnimalTotal() {
		FarmAnimalTotalDto dto = new FarmAnimalTotalDto();
		dto.setFarmCode("40.431.00001");
		dto.setAnimalCode("A001");
		dto.setTotal(30);
		dto.setYear(2019);
		/*System.out.println(pushFarmAnimalTotal(dto));*/
	}
	
	private static void deleteFarmTest() throws ClientProtocolException, JSONException, IOException {
		FarmMapDeleteDto farmMapDto = new FarmMapDeleteDto();
		farmMapDto.setCode("40.431.00001xxx");
		farmMapDto.setYear(2019);
		deleteFarmMap(farmMapDto);
	}

	private static void updateFarmTest() throws ClientProtocolException, JSONException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateStr = dateFormat.format(new Date());

		FarmMapDto farmMapDto = new FarmMapDto();
		farmMapDto.setAddressDetail("Xóm 3 - Xã Xuân Lam - Huyện Hưng Nguyên - Tỉnh Nghệ An v");
		farmMapDto.setCode("40.431.00001xxx");
		farmMapDto.setDistrictCode("431");
		farmMapDto.setLatitude(11.06154016D);
		farmMapDto.setLongitude(107.1491164D);
		farmMapDto.setName("Vũ Văn Cử");
		farmMapDto.setNewRegistrationCode("IIB-NAN-010");
		farmMapDto.setOwnerCitizenCode("186301655");
		farmMapDto.setOwnerGender("");
		farmMapDto.setOwnerName("Vũ Văn Cử 23");
		farmMapDto.setOwnerPhoneNumber("0985799125");
		farmMapDto.setPhoneNumber("0985799125");
		farmMapDto.setStatus(0);
		farmMapDto.setStopDate("0001-01-01T00:00:00");
		farmMapDto.setVillage("Xóm 3");
		farmMapDto.setProvinceCode("4");
		farmMapDto.setWardCode("");
		farmMapDto.setYear(2019);
		MapReturnDto dto = updateFarmMap(farmMapDto);
		/*System.out.println(dto);*/

	}

	public static void createFarmTest() throws ClientProtocolException, JSONException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateStr = dateFormat.format(new Date());
		FarmMapDto farmMapDto = new FarmMapDto();
		farmMapDto.setAddressDetail("Xóm 3 - Xã Xuân Lam - Huyện Hưng Nguyên - Tỉnh Nghệ A");
		farmMapDto.setCode("40.431.00001xxx");
		farmMapDto.setDistrictCode("431");
		farmMapDto.setLatitude(11.06154016d);
		farmMapDto.setLongitude(107.1491164d);
		farmMapDto.setName("Vũ Văn Cử");
		farmMapDto.setNewRegistrationCode("IIB-NAN-010");
		farmMapDto.setOwnerCitizenCode("186301655");
		farmMapDto.setOwnerGender("1");
		farmMapDto.setOwnerName("Vũ Văn Cử");
		farmMapDto.setOwnerPhoneNumber("0985799125");
		farmMapDto.setPhoneNumber("0985799125");
		farmMapDto.setStatus(0);
		farmMapDto.setStopDate("2020-08-13T07:46:11.841Z");
		farmMapDto.setVillage("Xóm 3");
		farmMapDto.setProvinceCode("4");
		farmMapDto.setWardCode(null);
		farmMapDto.setYear(2019);

		/*System.out.println(createFarmMap(farmMapDto));*/
	}
	
	public static MapReturnDto createFarmTolMap(Farm farmTemp) throws ClientProtocolException, JSONException, IOException {
		FarmMapDto farmMapDto = new FarmMapDto();
		farmMapDto.setAddressDetail(farmTemp.getAdressDetail());
		farmMapDto.setCode(farmTemp.getCode());
		
		try {
			farmMapDto.setLatitude(Double.parseDouble(farmTemp.getLatitude()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			farmMapDto.setLongitude(Double.parseDouble(farmTemp.getLongitude()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		farmMapDto.setName(farmTemp.getName());
		farmMapDto.setNewRegistrationCode(farmTemp.getNewRegistrationCode());
		farmMapDto.setOwnerCitizenCode(farmTemp.getOwnerCitizenCode());
		farmMapDto.setOwnerGender(farmTemp.getOwnerGender());
		farmMapDto.setOwnerName(farmTemp.getOwnerName());
		farmMapDto.setOwnerPhoneNumber(farmTemp.getOwnerPhoneNumber());
		farmMapDto.setPhoneNumber(farmTemp.getPhoneNumber());
		farmMapDto.setStatus(farmTemp.getStatus());
		try {
			farmMapDto.setStopDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(farmTemp.getStopDate()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		farmMapDto.setVillage(farmTemp.getVillage());
		if(farmTemp.getAdministrativeUnit() != null) {
			farmMapDto.setWardCode(farmTemp.getAdministrativeUnit().getCode());
			if(farmTemp.getAdministrativeUnit().getParent() != null) {
				farmMapDto.setDistrictCode(farmTemp.getAdministrativeUnit().getParent().getCode());
				if(farmTemp.getAdministrativeUnit().getParent().getParent() != null) {
					farmMapDto.setProvinceCode(farmTemp.getAdministrativeUnit().getParent().getParent().getCode());
				}
			}
		}
		try {
			farmMapDto.setYear(Integer.parseInt(farmTemp.getYearRegistration()));
		} catch (Exception e) {
			farmMapDto.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		}
		MapReturnDto ret = createFarmMap(farmMapDto);		
		return ret;
	}
	
	public static MapReturnDto updateFarmToMap(Farm farmTemp) {
		FarmMapDto farmMapDto = new FarmMapDto();
		if(StringUtils.hasText(farmTemp.getAdressDetail())) {
			farmMapDto.setAddressDetail(farmTemp.getAdressDetail());
		}
		else {
			String adressDetail="";
			if(StringUtils.hasText(farmTemp.getVillage())) {
				adressDetail+=farmTemp.getVillage()+",";
			}
			if(farmTemp.getAdministrativeUnit()!=null) {
				adressDetail+=farmTemp.getAdministrativeUnit().getName()+",";
			}
			if(farmTemp.getAdministrativeUnit().getParent()!=null) {
				adressDetail+=farmTemp.getAdministrativeUnit().getParent().getName()+",";
			}
			if(farmTemp.getAdministrativeUnit().getParent().getParent()!=null) {
				adressDetail+=farmTemp.getAdministrativeUnit().getParent().getParent().getName()+",";
			}
			farmMapDto.setAddressDetail(adressDetail);
		}
		farmMapDto.setCode(farmTemp.getCode());		
		try {
			farmMapDto.setLatitude(Double.parseDouble(farmTemp.getLatitude()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			farmMapDto.setLongitude(Double.parseDouble(farmTemp.getLongitude()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		farmMapDto.setName(farmTemp.getName());
		farmMapDto.setNewRegistrationCode(farmTemp.getNewRegistrationCode());
		farmMapDto.setOwnerCitizenCode(farmTemp.getOwnerCitizenCode());
		farmMapDto.setOwnerGender(farmTemp.getOwnerGender());
		farmMapDto.setOwnerName(farmTemp.getOwnerName());
		farmMapDto.setOwnerPhoneNumber(farmTemp.getOwnerPhoneNumber());
		farmMapDto.setPhoneNumber(farmTemp.getPhoneNumber());
		farmMapDto.setStatus(farmTemp.getStatus());
		try {
			farmMapDto.setStopDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(farmTemp.getStopDate()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		farmMapDto.setVillage(farmTemp.getVillage());
		if(farmTemp.getAdministrativeUnit() != null) {
			farmMapDto.setWardCode(farmTemp.getAdministrativeUnit().getCode());
			if(farmTemp.getAdministrativeUnit().getParent() != null) {
				farmMapDto.setDistrictCode(farmTemp.getAdministrativeUnit().getParent().getCode());
				if(farmTemp.getAdministrativeUnit().getParent().getParent() != null) {
					farmMapDto.setProvinceCode(farmTemp.getAdministrativeUnit().getParent().getParent().getCode());
				}
			}
		}
		try {
			farmMapDto.setYear(Integer.parseInt(farmTemp.getYearRegistration()));
		} catch (Exception e) {
			farmMapDto.setYear(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		}
		MapReturnDto obj = updateFarmMap(farmMapDto);
		return obj;
	}

	
	public static MapReturnDto deleteDataMapByAdministrativeUnit(DeleteMapByAreaDto dto) {
		try {
			SSLContext sslContext = null;
			try {
				sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    RestTemplate restTemplate = new RestTemplate(requestFactory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			final String url = host+"/farms/deleteByArea";

			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
			HttpEntity<Map<String,Object>> request = new HttpEntity<Map<String,Object>>(dto.toMap(), headers);
			ResponseEntity<?>  result = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);		
			LinkedHashMap hash = (LinkedHashMap)result.getBody();
			Gson gson = new Gson();
			String json = gson.toJson(hash,LinkedHashMap.class);
			/*System.out.print(json);*/
			MapReturnDto p = gson.fromJson(json, MapReturnDto.class);		
			return p;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
}

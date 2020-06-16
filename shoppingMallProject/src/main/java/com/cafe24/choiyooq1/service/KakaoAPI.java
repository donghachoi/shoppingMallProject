package com.cafe24.choiyooq1.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoAPI {
	
	//로그아웃
	public void logout(String accessToken) {
		String reqURL = "https://kauth.kakao.com/v1/user/unlink";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//	POST요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization",  "Bearer " +accessToken);
			int result = conn.getResponseCode();
			System.out.println(result);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println(br.readLine());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @param authorizeCode 로그인성공시 받는 code를 파라미터로받습니다.
	 * @return accessToken를 리턴
	 */
	public String getAccessToken(String authorizeCode) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//	POST요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			//	POST요청에 필요한 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter 	bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder 	sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=c932284d059f7038774366ddb7ec28d3");
			sb.append("&redirect_uri=http://localhost:80/login");
			sb.append("&code="+authorizeCode);
			System.out.println("test");
			bw.write(sb.toString());
			bw.flush();
			
			//	결과코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode in get Token: " + responseCode);
			
			//	요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			//System.out.println("result in get Token: " + result);
			
			//	Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser 	= new JsonParser();
			JsonElement element = parser.parse(result);
			
			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
			
			//System.out.println("accessToken in get Token: " + accessToken);
			//System.out.println("refreshToken in get Token: " + refreshToken);
			br.close();
			bw.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return accessToken;
	}
	
	/**
	 * @param accessToken를 파라미터로 받고  정보를 받아 가공합니다.
	 * @return 유저 닉네임을 리턴합니다.
	 */
	public HashMap<String, Object> getUserInfo(String accessToken){
		//System.out.println(accessToken);
		HashMap<String,Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			//	요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer "+accessToken);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			int responseCode = conn.getResponseCode();
			System.out.println(responseCode +"<<<<<<<<<<<<<<<<<<<<<<<< responseCode");
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
			while((line = br.readLine())!= null) {
				result += line;
			}
			System.out.println("response body : " + result);
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			JsonObject peroperties = element.getAsJsonObject().get("properties").getAsJsonObject();
			String nickName = peroperties.getAsJsonObject().get("nickname").getAsString();
			String profileImage = peroperties.getAsJsonObject().get("profile_image").getAsString();
			System.out.println(profileImage);
			userInfo.put("nickName", nickName);
			userInfo.put("profileImage", profileImage);
		}catch (IOException e) {
			// TODO: handle exception
		}
		return userInfo;
	}
}

package com.MRP.MRP_project;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.MRP.utils.GetFile;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class Gemini_Request {

	// Method for calling Gemini API for getting the predicted values for the Inventory
	
	/*   Here Gemini AI API Mocks as Inventory Prediction ML model*/
	public static void callGemini() throws IOException {

		/*
		 * Getting the API Key for the Google Account As it Key is private data so I am
		 * providing it explicitly
		 */

		Properties prop = GetFile.loadProperty("config.properties");
		String requestBody = FileUtils.readFileToString(GetFile.getFile("GeminiAPIRequestBody.txt"), "UTF-8");

		// Rest Call for the Gemini AI API

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		String response = RestAssured.given().baseUri(prop.getProperty("baseURI")).urlEncodingEnabled(false)
				.body(requestBody).queryParam("key", System.getenv("APIKey")).contentType(ContentType.JSON).when()
				.post().then().statusCode(200).contentType(ContentType.JSON).extract().response().body().asString();
		
		//Extraction of prediction values from the Gemini Model
		JsonPath body = new JsonPath(response);
		String prediction[] = body.getString("candidates[0].content.parts[0].text").split("[, ]");
		System.out.println("Item 1:"+Integer.parseInt(prediction[0])+"\n"+"Item 2:"+Integer.parseInt(prediction[1]));
	}
	
	@Test(invocationCount = 10)
	public static void Main() {
		try {
				callGemini();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}

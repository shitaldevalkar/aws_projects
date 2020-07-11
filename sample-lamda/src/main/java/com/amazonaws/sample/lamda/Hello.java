package com.amazonaws.sample.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Hello implements RequestHandler<String, String> {

	@Override
	public String handleRequest(String input, Context context) {
		context.getLogger().log("Input: " + input);
		String output = "Hello, " + input + "!";
		return output;
	}

}
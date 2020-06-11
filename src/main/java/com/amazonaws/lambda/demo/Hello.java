package com.amazonaws.lambda.demo;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.amazonaws.services.lambda.model.ServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Hello implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		
		
		 LambdaLogger logger = context.getLogger(); logger.log("handler started ...: ");
		 /*List all functions in aws lambda
		 * ListFunctionsResult functionResult = null;
		 * 
		 * try { AWSLambda awsLambda = AWSLambdaClientBuilder.standard() //
		 * .withCredentials(new ProfileCredentialsProvider())
		 * .withRegion(Regions.AP_SOUTH_1).build();
		 * 
		 * functionResult = awsLambda.listFunctions(); logger.log("The function list : "
		 * + functionResult); List<FunctionConfiguration> list =
		 * functionResult.getFunctions();
		 * 
		 * for (Iterator iter = list.iterator(); iter.hasNext();) {
		 * FunctionConfiguration config = (FunctionConfiguration) iter.next();
		 * logger.log("The function name is " + config.getFunctionName()); }
		 * 
		 * } catch (ServiceException e) { System.out.println(e); }
		 */
		
		 // invoke aws lambda function 
		 InvokeRequest invokeRequest = new InvokeRequest()
	                .withFunctionName("test-python")
	                .withPayload("{\n" +
	                        " \"Hello \": \"Paris\",\n" +
	                        " \"countryCode\": \"FR\"\n" +
	                        "}");
	        InvokeResult invokeResult = null;

	        try {

	            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
	                    .withRegion(Regions.AP_SOUTH_1).build();

	            invokeResult = awsLambda.invoke(invokeRequest);

	            String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);
	            logger.log("response " + ans);
	        } catch (ServiceException e) {
	            logger.log(e.getErrorMessage());
	        }
		
		return "ok";
	}

}

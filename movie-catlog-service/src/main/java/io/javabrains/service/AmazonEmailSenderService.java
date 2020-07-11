package io.javabrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import io.javabrains.config.AWSCrdentialsConfig;
import io.javabrains.config.CustomAWSCredentialsProvider;

@Service
public class AmazonEmailSenderService {

	@Autowired
	private CustomAWSCredentialsProvider customCredendentialProvider;

	@Autowired
	private AWSCrdentialsConfig awsConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonEmailSenderService.class);

	static final String FROM = "shital.devalkar@globant.com"; // Replace with your "From" address. This address must be

	// production access, this address must be verified.
	static final String BODY = "Shital - This email was sent through Amazon SES by using the AWS SDK for Java.";
	static final String SUBJECT = "Shital - Amazon SES test";

	// Create the subject and body of the message.
	Content subject = new Content().withData(SUBJECT);
	Content textBody = new Content().withData(BODY);
	Body body = new Body().withText(textBody);

	// Create a message with the specified subject and body.
	Message message = new Message().withSubject(subject).withBody(body);

	public void sendEmail(String emailId) {
		// Construct an object to contain the recipient address.
		Destination destination = new Destination().withToAddresses(new String[] { emailId });
		// Assemble the email.
		SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination)
				.withMessage(message);

		try {
			AmazonSimpleEmailService client = null;
			LOGGER.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
			ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			try {
				credentialsProvider.getCredentials();

				// Instantiate an Amazon SES client, which will make the service call with the
				// supplied AWS credentials.
				client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(credentialsProvider)
						.withRegion(awsConfig.getAwsAccessRegion()).build();

			} catch (Exception e) {
				LOGGER.error("Cannot load the credentials from the credential profiles file. "
						+ "Using from docker-compose file", e);

				client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(customCredendentialProvider)
						.withRegion(awsConfig.getAwsAccessRegion()).build();

				LOGGER.error("Build Email client " + client);
			}

			// Send the email.
			client.sendEmail(request);
			LOGGER.info("Email sent!");

		} catch (Exception ex) {
			LOGGER.info("The email was not sent.");
			LOGGER.info("Error message: " + ex.getMessage(), ex);
			throw ex;
		}
	}
}

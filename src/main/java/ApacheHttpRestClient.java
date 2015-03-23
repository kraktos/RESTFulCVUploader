import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

public class ApacheHttpRestClient {

	public static void main(String args[]) throws Exception {
		ApacheHttpRestClient fileUpload = new ApacheHttpRestClient();
		File file = new File("/home/adutta/cv_Dutta.pdf");
		// Upload the file
		fileUpload.executeMultiPartRequest(
				"http://cvu.zanox.com/rest/upload/cv", file, file.getName(),
				"File Uploaded :: cv_Dutta.pdf");
	}

	public void executeMultiPartRequest(String urlString, File file,
			String fileName, String fileDescription) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost postRequest = new HttpPost(urlString);
		try {
			// Set various attributes
			MultipartEntityBuilder multiPartEntity = MultipartEntityBuilder
					.create();
			multiPartEntity.addPart("file", new FileBody(file));
			multiPartEntity.addTextBody("firstname", "Arnab",
					ContentType.TEXT_PLAIN);

			multiPartEntity.addTextBody("lastname", "Dutta",
					ContentType.TEXT_PLAIN);

			multiPartEntity.addTextBody("email",
					"arnab@informatik.uni-mannheim.de", ContentType.TEXT_PLAIN);

			multiPartEntity.addTextBody("jobtitle", "Senior Java Engineer",
					ContentType.TEXT_PLAIN);

			multiPartEntity.addTextBody("source", "LinkedIn",
					ContentType.TEXT_PLAIN);

			// Set to request body
			postRequest.setEntity(multiPartEntity.build());

			// Send request
			HttpResponse response = client.execute(postRequest);

			// Verify response if any
			if (response != null) {
				System.out.println(response.getStatusLine().getStatusCode());
				System.out.println(response.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

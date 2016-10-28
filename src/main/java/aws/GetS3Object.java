package aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetS3Object {
	private static String bucketName = "text-content";
	private static String key = "text-object.txt";
	public static void main(String[] args) throws IOException{
		AmazonS3 s3Client = new AmazonS3Client();
		try{
			System.out.println("Downloading an object");
			S3Object s3object = s3Client.getObject(
			new GetObjectRequest(bucketName, key));
			displayTextInputStream(s3object.getObjectContent());
		}
		catch(AmazonServiceException ase){
			System.out.println( "AmazonServiceException" );
		}
		catch(AmazonClientException ace){
			System.out.println( "AmazonClientException" );
		}
	}
	private static void displayTextInputStream(InputStream input) throws IOException{
	// Read one text line at a time and display.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while(true){
			String line = reader.readLine();
			if(line == null) break;
			System.out.println( " " + line );
		}
		System.out.println();
	}

}

package com.atithi.ImageSender;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.james.mime4j.field.ContentTypeField;

/**
 * 
 * @author Omkar Rege
 * packaged as jar
 * command to run
 * java -jar ImageSender.jar "file_name" "file_path"
 * java -jar ImageSender.jar "Amdocs_lastdays_1.jpg" "C:/Users/Gaurav/Pictures/Amdocs_LastDays" "RASP1"
 *
 */
public class ImageSender 
{
	private static final String SERVER_IP ="http://54.200.239.59:8080";
	
	//THIS WILL BE THE LOCATION ON RASP-PI WHERE IMAGES ARE STORED 
	//private static String FILE_STORE_PATH ="C:/Users/Gaurav/Pictures/Amdocs_LastDays/";
    public static void main( String[] args ) throws UnsupportedEncodingException
    {
        System.out.println( "Hello World!" );
        
        final String file_Name = args[0]; //"IMAG0013.jpg";
        final String file_path = args[1];//"C:/Users/Gaurav/Pictures/Amdocs_LastDays";
        final String userID = args[2];//"RASP1";
        final String fullPath = file_path+"/"+file_Name;
        System.out.println("Fully qualified path is"+fullPath);
        
        final HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        //This is Hard coded
        //REST CALL TO SERVER
        final HttpPost post = new HttpPost(SERVER_IP+"/ImageManager/rest/imageService/store/"+file_Name+"/"+userID);  
        //new HttpPost("http://localhost:8081/ImageManager/rest/imageService/store/"+file_Name+"/"+userID);      
        //		
        //new HttpPost("http://localhost:8081/ImageManager/rest/imageService/store/"+file_Name+"/"+userID);
               
        final File file = new File(fullPath);
        final FileEntity entity = new FileEntity(file,"binary/octet-stream");
        post.setEntity(entity);
        entity.setContentType("binary/octet-stream");
       
//        post.getParams().setParameter("name", "RASP1");
//        post.getParams().setParameter("pass", "RASP1");
        
        System.out.println("executing request " + post.getRequestLine());
	    HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	    final HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      try {
			System.out.println(EntityUtils.toString(resEntity));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    if (resEntity != null) {
	      try {
			resEntity.consumeContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

	    client.getConnectionManager().shutdown();
    }
}

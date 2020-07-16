package com.example.demo.Config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.example.demo.model.FileUpload;
import com.example.demo.model.User;
import com.example.demo.repo.CollectionRepositoryImpl;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${DownloadedFiles}")
	private String commFiles;

	@Value("${TempFiles}")
	private String tempFiles;

	@Autowired
	private ServletContext context;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		info.put("email", user.getEmail());
		info.put("user_id", user.getUid());
		info.put("userName", user.getUsername());
//		FileUpload FileContent = downloadFile(user.getUser_details().objectId);
//		byte[] filedata = FileContent.getFileContent();
//
//		String filepath = tempFiles + "/" + FileContent.getName();
//		OutputStream os = null;
//		try {
//			os = new FileOutputStream(filepath);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			os.write(filedata);
//			os.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		info.put("dp_path", FileContent.getName());

		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}

	private FileUpload downloadFile(String objectId) {

		return mongoTemplate.findById(objectId.replace("\"", ""), FileUpload.class,
				new CollectionRepositoryImpl().getCollectionName());
	}
}

package com.menzhenchaxun.common.utils;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * 阿里云OSS上传下载
 */
public class OssUtils {

	public static final  String ENDPOINT="oss-cn-beijing.aliyuncs.com";
	public static final String ACCESSKEY="LTAI8vH63loOPvE8";
	public static final String ACCESS_SECRET="Cg5DIuEkqYjIJ1dYoU7FNsMapAPNtz";
	public static final String BUCKET_URL="yihuitongapp.oss-cn-beijing.aliyuncs.com";
	public static final String BUCKET_NAME="yihuitongapp";
//	public static final String FILEDIR="images/";
	private String fileName;
	
	public OssUtils(String fileName){
		this.fileName=fileName;
	}
	
	/**
	 * 创建OSSClient对象
	 */
	public static OSSClient getOSSClient(){
		return new OSSClient(OssUtils.ENDPOINT,OssUtils.ACCESSKEY,OssUtils.ACCESS_SECRET);
	}
	
	/**
	 * 图片上传OSS
	 */
	
	public  String uploadObject2OSS(OSSClient ossClient,MultipartFile file,String bucketName){
		String diskName = "yihuitong/datas/";
		String resultStr=null;
		try {
//			String fileName=file.getOriginalFilename();
			Long fileSize=file.getSize();
			ObjectMetadata metadata= new ObjectMetadata();
			metadata.setContentLength(fileSize);
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma","no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType("");
			metadata.setContentDisposition("filename/filesize=" +fileName+"/"+fileSize+"Byte.");
			//文件上传
			ossClient.putObject(bucketName,diskName +this.fileName,file.getInputStream(),metadata);
			ossClient.shutdown();
			resultStr="http://"+OssUtils.BUCKET_NAME+"."+OssUtils.ENDPOINT+"/"+diskName+fileName;
		} catch (OSSException | ClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
	
	/**
	 * 定义图片上传类型及返回类型
	 */
	
	public static String getContentType(String fileName){
		String fileExtension=fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)){
			return "image/bmp";
		}
		if(".gif".equalsIgnoreCase(fileExtension)){
			return "image/gif";
		}
		if(".jpeg".equalsIgnoreCase(fileExtension)||".jpg".equalsIgnoreCase(fileExtension)||".png".equalsIgnoreCase(fileExtension)){
			return "image/jpeg";
		}
		
		return fileName;
	}
	
	/**
	 * 文件上传OSS
	 */
	
	public  String uploadObject(MultipartFile headPic){
		OSSClient ossClient=getOSSClient();
		return uploadObject2OSS(ossClient,headPic, OssUtils.BUCKET_NAME);
	}
}

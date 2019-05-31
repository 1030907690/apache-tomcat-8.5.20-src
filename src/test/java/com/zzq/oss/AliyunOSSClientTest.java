package com.zzq.oss;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;




import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
 


/**
 * @author zhouzhongqing
 * 2018年7月18日10:03:41
 *  阿里云对象存储OSS客户端
 * */
public class AliyunOSSClientTest {


    private final static String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    private final static String ACCESSKEYID = "LTAI6OqT5RCl61fn";

    private final static String ACCESSKEYSECRET = "xxxxx";

    private final static String BUCKETNAME = "rw-game-version";

    private static String KEY = "*** Provide key ***";

    /***图片路径前缀CDN地址 **/
    public static String OSSIMAGESPath = "";// BeansUtils.getBean(Constants.class).getOssImagesPath();

    //红包图片的相对路径
    public final static String CDN_PATH_PREFIX = "GameVersion/";

    public static OSSClient ossClient = null;

    //上传类型
    //本地文件
    public final static String FILEPYTE = "FILE";
    //字符串类型
    public final static String STIRNGPYTE = "STRING";

    //网络流
    public final static String NETWORKTYTE = "NETWORK";



    /***
     * 创建OSSClient实例
     * **/
    public static void ossInit(){
        if(null == ossClient){
            ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        }
    }


    /***
     * 关闭client
     * **/
    public static void ossShutdown(){
        if(null != ossClient){
            ossClient.shutdown();
            ossClient = null;
        }
    }


    /***
     * 创建Bucket
     * **/
    public void createBucket(OSSClient oss_Client){
        boolean exists = oss_Client.doesBucketExist(BUCKETNAME);
        if(!exists){//如果没有这个Bucket就创建
            CreateBucketRequest createBucketRequest= new CreateBucketRequest(BUCKETNAME);
            // 设置bucket权限为公共读，默认是私有读写
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 设置bucket存储类型为低频访问类型，默认是标准类型
            //createBucketRequest.setStorageClass(StorageClass.IA);
            oss_Client.createBucket(createBucketRequest);
        }else{
            //	System.out.println("已创建");
        }

    }

    // 删除bucket
    public static void deleteBucket(){
        OSSClient oss_Client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        boolean exists = oss_Client.doesBucketExist(BUCKETNAME);
        if(exists){//如果没有这个Bucket就创建
            // 删除bucket
            oss_Client.deleteBucket(BUCKETNAME);
        }

        oss_Client.shutdown();
    }


    /**
     * 上传
     * uploadType 上传类型
     *
     * directoryPath 保存到oss的绝对路径
     *
     * contentOrPath 内容或者是地址
     * */
    public static void upload(String uploadType,String directoryPath,String contentOrPath){

        OSSClient oss_Client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        new AliyunOSSClientTest().createBucket(oss_Client);

        System.out.println("oss---上传的data ："+contentOrPath);
        // Object是否存在
        //boolean found = oss_Client.doesObjectExist(bucketName,directoryPath );
        //if(found)return;
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传内容类型
        meta.setContentType("text/html; charset=utf-8");
        if(AliyunOSSClientTest.STIRNGPYTE.equals(uploadType)){    		// 上传字符串
            try {
                oss_Client.putObject(BUCKETNAME, directoryPath, new ByteArrayInputStream(contentOrPath.getBytes("utf-8")),meta);
            } catch (OSSException | ClientException
                    | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(AliyunOSSClientTest.FILEPYTE.equals(uploadType)){// 上传本地文件
            if(new File(contentOrPath).exists()){
                oss_Client.putObject(BUCKETNAME,directoryPath , new File(contentOrPath));
            }

            boolean found = oss_Client.doesObjectExist(BUCKETNAME,directoryPath ); //2017年6月9日15:31:21  加入网络流的上传
            if(found){
                //System.out.println("上传成功");
            }else{
                try {
                    System.out.println(contentOrPath+ "  : 上传失败，启用网络流方式上传");
                    // 创建上传Object的Metadata
                    ObjectMetadata metaImage = new ObjectMetadata();
                    // 设置上传内容类型
                    metaImage.setContentType("image/jpeg");
                    oss_Client.putObject(BUCKETNAME, directoryPath, new URL(contentOrPath).openStream(),metaImage);
                } catch (Exception e) {
                    System.out.println(AliyunOSSClientTest.FILEPYTE+ " 上传网络流失败");
                    e.printStackTrace();
                }

            }
        }else if (AliyunOSSClientTest.NETWORKTYTE.equals(uploadType)){//上传网络流
            try {

                /*******2017年6月12日10:51:05 zhouzhongqing 将路径中非法字符替换成空白  start********/
                //directoryPath =	HtmlEdit.replaceAllForbiddenCharacter(directoryPath);
                /*******2017年6月12日10:51:05 zhouzhongqing 将路径中非法字符替换成空白  end********/

                // 创建上传Object的Metadata
                ObjectMetadata metaImage = new ObjectMetadata();
                // 设置上传内容类型
                metaImage.setContentType("image/jpeg");
                oss_Client.putObject(BUCKETNAME, directoryPath, new URL(contentOrPath).openStream(),metaImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        oss_Client.shutdown();

    }


    /**
     * 创建模拟文件夹
     * */
    public static void createDirectory(String directory){
        // Object是否存在
    	/*boolean found = ossClient.doesObjectExist(bucketName,directory );
    	if(!found){
    		ossClient.putObject(bucketName, directory, new ByteArrayInputStream(new byte[0]));
    	}*/
    }


    /**
     * 删除文件
     * */

    public static void deleteObject(String filePath){
        OSSClient oss_Client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // Object是否存在
        boolean found = oss_Client.doesObjectExist(BUCKETNAME,filePath );
        //System.out.println(found);
        if(found){
            // 删除Object
            oss_Client.deleteObject(BUCKETNAME, filePath);
        }
        oss_Client.shutdown();
    }


    /**
     * 下载文件到本地
     * @param directoryPath  OSS文件路径
     * @param fleName  下载到本地的路径和名称
     */
    public static void downloadFileToLocal(String directoryPath , String fleName){
        // Object是否存在
        boolean found = ossClient.doesObjectExist(BUCKETNAME,directoryPath );
        if(found){
            // 下载object到文件
            ossClient.getObject(new GetObjectRequest(BUCKETNAME, directoryPath), new File(fleName));
        }else{
            System.out.println("下载的文件不存在");
        }
    }



    /**
     * @author zhouzhongqing
     * 分页列出存储空间中的文件
     */
    public static void findOSSListPage(String nextMarker){
        OSSClient oss_Client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 列举Object
        final int maxKeys = 200;
        //String nextMarker = null;
        ObjectListing objectListing = null;
        //do {
        objectListing = oss_Client.listObjects(new ListObjectsRequest(BUCKETNAME).withMarker(nextMarker).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
            // deleteObject(s.getKey());
        }
        //  nextMarker = objectListing.getNextMarker();
        //} while (objectListing.isTruncated());
        oss_Client.shutdown();
    }


    public static void traverseFolder2(String prefix,String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(prefix,file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        
                        String [] filePathArr = file2.getAbsolutePath().replace(prefix, "").split("\\\\");
                        StringBuffer uploadPath = new StringBuffer();
                        for(int i = 0; i < filePathArr.length ;i++){
                        	uploadPath.append(filePathArr[i]+"/");
                        }
                        
                        if(null != uploadPath && uploadPath.length() > 0){
                        	uploadPath.deleteCharAt(uploadPath.length()-1);
                        }
                       // System.out.println("上传的文件 "+ file2.getAbsolutePath().replace(prefix, "") + " 转为 " + uploadPath.toString());
                        //上传文件
                        AliyunOSSClientTest.upload(AliyunOSSClientTest.FILEPYTE,uploadPath.toString(),file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
    	traverseFolder2("C:"+File.separator+"Users"+File.separator+"Administrator"+File.separator+"Desktop"+File.separator,"C:/Users/Administrator/Desktop/GameVersion");
        //AliyunOSSClient.createDirectory("parent_directory/test/");
        
    	long endTime = System.currentTimeMillis();
       
    	System.out.println(" over "+ ((endTime - startTime)));
        //AliyunOSSClient.deleteObject("parent_directory/test/");

        //AliyunOSSClient.upload(AliyunOSSClient.STIRNGPYTE, "parent_directory/test/4.html","hello world Tom 22222221xxxxxxxxxxx-----");
        //AliyunOSSClient.upload(AliyunOSSClient.FILEPYTE, "parent_directory/test/2.html","C:/Users/zhou/Desktop/index-demo.html");

        //AliyunOSSClient.upload(AliyunOSSClient.NETWORKTYTE, "parent_directory/test/1.html","http://yougougoutest1.oss-cn-shanghai.aliyuncs.com/parent_directory/test/1.html");

        //AliyunOSSClient.downloadFileToLocal("parent_directory/test/12.html", "C:/Users/Public/Pictures/1.html");


        //AliyunOSSClient.ossShutdown();



		/*
		try{
			//创建连接
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://alicdn2.yougogo.cn/phone/phone_region_county/getRegionCounty/41.json");

			 //模拟浏览器
            String charset = "UTF-8";

            //设置参数，仿html表单提交
			//发送HttpPost请求，并返回HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			 if (entity != null) {
                     // 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1
                 String result = EntityUtils.toString(entity,charset);
                 System.out.println( result);
            }
			// 判断请求响应状态码，状态码为200表示服务端成功响应了客户端的请求

			}catch(Exception e){}
         */
    }



}
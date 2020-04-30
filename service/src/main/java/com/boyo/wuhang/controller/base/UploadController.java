package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.ultity.JsonBuilder;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.io.FileUtils.openOutputStream;

@Controller
@Api(tags = "上传接口")
public class UploadController {
	@Value("${upload.image.url}")
	String uploadPath;
	@Value("${load.image.url}")
	String loadUrl;

	@RequestMapping(value = "upload",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject upload(InputStream dataStream){
		String targetDir = "/image";
		String tempFilePath = uploadPath  + targetDir;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String tempFolder = sdf.format(new Date());
		tempFilePath =tempFilePath + '/' + tempFolder;
		Map<String,Object> result = new HashMap<String,Object>();

		try {
			String targetFileName = UUID.randomUUID().toString() + ".jpg";
			copyInputStreamToFile(dataStream, new File(tempFilePath, targetFileName));

			result.put("fileName",targetFileName);
			result.put("httpPath",loadUrl+targetDir+'/'+tempFolder+'/'+targetFileName);
			dataStream.close();
		}catch (Exception e){
			e.printStackTrace();
			return JsonBuilder.build(1,"上传失败",null);
		}
		return JsonBuilder.build(0,"上传成功",result);
	}

	private static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
		try {
			FileOutputStream output = openOutputStream(destination);
			try {
				IOUtils.copy(source, output);
			} finally {
				IOUtils.closeQuietly(output);
			}
		} finally {
			IOUtils.closeQuietly(source);
		}
	}
}

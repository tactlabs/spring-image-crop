package org.tact.base.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tact.base.ImageCropper;

@RestController
@RequestMapping(value = "/base")
public class BaseController {
	
	/**
	 * 
	 * @return
	 * 
	 * Possible urls:
	 * 		http://localhost:1878/base/
	 */
    @GetMapping(value = "")
    public <T> T testBase() {
        
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("one", "two");
        map.put("three", "four");
        map.put("five", "six");
        map.put("seven", "eight");
        
        return (T) map;
    }
    
    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     * 
     * Possible urls:
	 * 		http://localhost:1878/base/crop/image
     */
    @PostMapping(value = "/crop/image")
    public <T> T cropImage(
    	@RequestParam(value = "x1") Integer x1,
    	@RequestParam(value = "y1") Integer y1,
    	@RequestParam(value = "x2") Integer x2,
    	@RequestParam(value = "y2") Integer y2
    	) {
    	
    	String imageBase = getUploadBase();
    	
    	// delete old cropped files
    	deleteOldFiles(imageBase);
    	
    	//System.out.println("x1: "+x1+", y1: "+y1+", x2: "+x2+", y2: "+y2);
    	
    	File fileToWrite = new File(imageBase+"ant.jpg");
		BufferedImage bufferedImage = ImageCropper.cropImageSpecial(fileToWrite, x1, y1, x2, y2);
		
		int randomNumber = getRandom();
		
		String fileName = "result_"+randomNumber+".jpg";

		File outputfile = new File(imageBase+fileName);
		try {
			ImageIO.write(bufferedImage, "jpg", outputfile);
		} catch (IOException e) {		
			e.printStackTrace();
		}
        
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("result", "http://localhost:1878/images/"+fileName);
        map.put("filename", fileName);
        
        return (T) map;
    }
    
    // https://stackoverflow.com/questions/7240519/delete-files-with-same-prefix-string-using-java
    public void deleteOldFiles(String imageBase){
    	final File folder = new File(imageBase);
    	
    	for (File f : folder.listFiles()) {
    	    if (f.getName().startsWith("result_")) {
    	        f.delete();
    	    }
    	}
    }
    
    public int getRandom(){
    	
    	Random r = new Random();
    	int low = 10;
    	int high = 100;
    	int result = r.nextInt(high-low) + low;
    	
    	return result;
    }
    
    @RequestMapping(value = "/charset", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody <T> T testCharSet(			
			@RequestParam(value = "spotcd", defaultValue = "4") String spotCD,
			@RequestParam(value = "item_name", required=false) final String name,
			final HttpServletRequest request, 
			final HttpServletResponse response			
	) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		print("name : ["+name+"]");
		
		map.put("name", name);
		map.put("apiresult", "000");
		map.put("message", "ok");
		
		return (T) map;
	}
    
    public static void print(Object obj){
    	System.out.println(obj);
    }
    
    public static String getUploadBase(){
    	return "p:/test/";
    }
}

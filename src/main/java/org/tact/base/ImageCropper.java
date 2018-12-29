package org.tact.base;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCropper {

	public static void main1(String[] args) {
		ImageCropper imc = new ImageCropper();
		imc.start();
	}

	public void start() {

		File fileToWrite = new File("D:/test/ant.jpg");
		//BufferedImage bufferedImage = cropImage(fileToWrite, 20, 20, 100, 100);
		
		BufferedImage bufferedImage = cropImageSpecial(fileToWrite, 72, 62, 313, 211);

		File outputfile = new File("D:/test/ant_result.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", outputfile);
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}

	@Deprecated
	private BufferedImage cropImage1(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(20, 20, rect.width, rect.height);
		return dest;
	}
	
	public static BufferedImage cropImageSpecial(File filePath, int x1, int y1, int x2, int y2) {
		
		int width = x2 - x1;
		int height = y2 - y1;
		
		return cropImage(filePath, x1, y1, width, height);
	}

	public static BufferedImage cropImage(File filePath, int x, int y, int w, int h) {

		try {
			BufferedImage originalImgage = ImageIO.read(filePath);

			BufferedImage subImgage = originalImgage.getSubimage(x, y, w, h);

			return subImgage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

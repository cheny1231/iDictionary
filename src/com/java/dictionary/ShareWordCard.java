package com.java.dictionary;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;

public class ShareWordCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6175592068468326297L;
	String userFrom;
	BufferedImage image;
	ObservableList<String> usersShared;

	public ShareWordCard(ObservableList<String> usersShared, String userFrom) {
		this.userFrom = userFrom;
		this.usersShared = usersShared;
	}

	public void alphaWords2Image(String word, String translation, String type) throws IOException {
		FileOutputStream fos = null;
		try {
			String path = System.getProperty("user.dir").replace("\\", "/");
			image = ImageIO.read(new File(path.concat("/images/WordCard.png")));
			// create Graphics2D object
			Graphics2D g2d = image.createGraphics();
			// fill the background with source image
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
			// set opacity
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
			g2d.setComposite(ac);
			// set the text
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 40));// set the font of title word
			g2d.setColor(Color.BLACK);
			g2d.drawString(word, 173, 100); // write the word
			g2d.setFont(new Font("ËÎÌו", Font.PLAIN, 26));// set the font of translation
			int x = 30;
			int y = 150;
			for (int i = 0; i < translation.length() - 1; i++) {
				if (translation.substring(i, i + 1).equals("\n")) {
					x = 30;
					y = y + 40;
				} else {
					if (x > 390) {
						x = 30;
						y = y + 40;
					}
					g2d.drawString(translation.substring(i, i + 1), x, y);
					FontMetrics fm = g2d.getFontMetrics();
					Rectangle2D rec = fm.getStringBounds(translation.substring(i, i + 1), g2d);
					x = x + (int) rec.getWidth();
				}
			}
			g2d.drawString(type, 300, 550);
			g2d.dispose();
			fos = new FileOutputStream(path.concat("/images/WordCard1.png"));
			ImageIO.write(image, "png", fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}

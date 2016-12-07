package com.java.dictionary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import javafx.collections.ObservableList;

public class ShareWordCard {
	BufferedImage image;
	ObservableList<String> usersShared;

	public ShareWordCard(ObservableList<String> usersShared) {	
		this.usersShared = usersShared;
	}

	public void alphaWords2Image(String word, String translation, String type) throws IOException {
		FileOutputStream fos = null;
		try {
			String[] trans = translation.split("\n");
			image = ImageIO.read(new File("D:\\WordCard.png"));
			// ����java2D����
			Graphics2D g2d = image.createGraphics();
			// ��Դͼ����䱳��
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
			// ����͸����
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
			g2d.setComposite(ac);
			// ���������������ơ���ʽ����С
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g2d.setColor(Color.BLACK);// ����������ɫ
			g2d.drawString(word, 173, 100); // ����ˮӡ���ּ�����ʼx��y����
			g2d.setFont(new Font("����", Font.PLAIN, 26));
			for(int i = 0; i < trans.length; i++){
				g2d.drawString(trans[i], 30, i*40+150);
			}
			g2d.drawString(type, 300, 500);
			g2d.dispose();
			fos = new FileOutputStream("D:\\WordCard1.png");
			ImageIO.write(image, "png", fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	/*
	 * alphaWords2Image("D:\\WordCard.png",1, "TimesRoman",Font.ITALIC, 12,
	 * Color.BLACK, word, 0, 0, "png", "D:\\WordCard1.png");
	 */
}

package com.java.dictionary;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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
			String path = System.getProperty("user.dir").replace("\\", "/");
			image = ImageIO.read(new File(path.concat("/images/WordCard.png")));
			// 创建java2D对象
			Graphics2D g2d = image.createGraphics();
			// 用源图像填充背景
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
			// 设置透明度
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
			g2d.setComposite(ac);
			// 设置文字字体名称、样式、大小
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g2d.setColor(Color.BLACK);// 设置字体颜色
			g2d.drawString(word, 173, 100); // 输入水印文字及其起始x、y坐标
			g2d.setFont(new Font("宋体", Font.PLAIN, 26));
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

	/*
	 * alphaWords2Image("D:\\WordCard.png",1, "TimesRoman",Font.ITALIC, 12,
	 * Color.BLACK, word, 0, 0, "png", "D:\\WordCard1.png");
	 */
}

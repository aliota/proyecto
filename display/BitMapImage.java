package com.ae.proyecto.display;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class BitMapImage {

    public static void displayBitMapImage(List<List<Integer>> data) {
        int width = data.size();
        int height = data.get(0).size();

        int[] flattenedData = new int[width*height*3];
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int ind = 0;
        int greyShade;

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                greyShade = data.get(i).get(j);
                greyShade = greyShade == 1 ? 0 : 255;
                flattenedData[ind + j*3] = greyShade;
                flattenedData[ind + j*3+1] = greyShade;
                flattenedData[ind + j*3+2] = greyShade;
            }
            ind += height*3;
        }

        img.getRaster().setPixels(0, 0, width, height, flattenedData);

        BufferedImage after = new BufferedImage(img.getWidth()*2, img.getHeight()*2, BufferedImage.TYPE_INT_RGB);
        AffineTransform at = new AffineTransform();
        at.scale(2.0, 2.0);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(img, after);


        JLabel jLabel = new JLabel(new ImageIcon(after));

        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        JFrame r = new JFrame();
        r.setName("Trazados");
        r.setFont(Font.getFont("Arial"));
        r.setSize(after.getWidth() + 20, after.getHeight() + 40);
        r.add(jPanel);
        r.setVisible(true);
    }
}

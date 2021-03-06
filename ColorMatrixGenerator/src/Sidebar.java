import javax.swing.*;

import java.awt.image.BufferedImage;
import java.awt.*; //For graphics
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Sidebar extends JPanel{
    private BufferedImage mImage;
    private ColorDisplay colors;

    private ColorInfo TL;
    private ColorInfo TR;
    private ColorInfo BL;
    private ColorInfo BR;

    private JTextField dimension;

    public Sidebar(ColorDisplay colors){
        this.colors = colors;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(275, getHeight()));
        add(Box.createRigidArea(new Dimension(300, 150)));
        
        JLabel image = new JLabel(new ImageIcon("./lib/ColorMatrix.icns"));
        add(image);

        JLabel text = new JLabel("RGB Matrix Generator");
        text.setFont(new Font("Karla", Font.PLAIN, 20));
        text.setForeground(Color.GRAY);
        add(text);

        TL = new ColorInfo(colors, "tl", " Top Left Color RGB ");
        TR = new ColorInfo(colors, "tr", " Top Right Color RGB ");
        BL = new ColorInfo(colors, "bl", " Bottom Left Color RGB ");
        BR = new ColorInfo(colors, "br", " Bottom Right Color RGB ");
        add(TL);
        add(TR);
        add(BL);
        add(BR);
        
        JButton button = new JButton("Random Matrix");
        button.setBackground(new Color(10, 10, 10));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                colors.setRandom();
                update();
            }
          });
        add(button);

        button = new JButton("Isolate Color");
        button.setBackground(new Color(10, 10, 10));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                colors.isolateColor();
                update();
            }
          });
        add(button);

        text = new JLabel("Matrix Resolution:");
        text.setFont(new Font("Karla", Font.PLAIN, 15));
        text.setForeground(Color.WHITE);
        add(text);

        dimension = new JTextField("" + colors.colors.resolution);
        dimension.setSize(new Dimension(50, 10));
        dimension.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                colors.setResolution(dimension.getText());
            }
          });
        add(dimension);

        JButton save = new JButton("Save Color Matrix");
        save.setBackground(new Color(10, 10, 10));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                colors.save();
            }
          });
        add(save);
    }

    @Override
    public void paintComponent(Graphics g){ 
        mImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics mg = mImage.getGraphics();  

        mg.setColor(new Color(20, 20, 20));
        mg.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
    }

    public void update(){
        dimension.setText("" + colors.colors.resolution);
        TL.update(colors.getColor("tl"));
        TR.update(colors.getColor("tr"));
        BL.update(colors.getColor("bl"));
        BR.update(colors.getColor("br"));
    }
}
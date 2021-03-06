import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*; //For graphics
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ColorInfo extends JPanel{
    private BufferedImage mImage;
    private JTextField red;
    private JTextField green;
    private JTextField blue;
    
    public ColorInfo(ColorDisplay colors, String position, String title){
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 55));

        JPanel subbar = new JPanel();
        subbar.setBackground(new Color(30, 31, 28));
        subbar.setLayout(new FlowLayout());

        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setFont(new Font("Karla", Font.PLAIN, 15));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        add(label, BorderLayout.NORTH);

        red = new JTextField("   ");
        red.setPreferredSize(new Dimension(40, 30));
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                CustomColor newColor = colors.setColor(position, red.getText(), "Red");
                update(newColor);
            }
          });
          subbar.add(red);

        green = new JTextField("   ");
        green.setPreferredSize(new Dimension(40, 30));
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                CustomColor newColor = colors.setColor(position, green.getText(), "Green");
                update(newColor);
            }
          });
        subbar.add(green);

        blue = new JTextField("   ");
        blue.setPreferredSize(new Dimension(40, 30));
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                CustomColor newColor = colors.setColor(position, blue.getText(), "Blue");
                update(newColor);
            }
          });
        subbar.add(blue);

        add(subbar, BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g){ 
        mImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics mg = mImage.getGraphics();  

        mg.setColor(new Color(10, 10, 10));
        mg.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
    }

    public void update(CustomColor c){
        String redText = ""+c.red;
        String greenText = ""+c.green;
        String blueText = ""+c.blue;

        red.setText(redText);
        green.setText(greenText);
        blue.setText(blueText);
    }
}
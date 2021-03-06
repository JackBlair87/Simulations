import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*; //For graphics

public class Header extends JPanel{
    private BufferedImage mImage;
    private Subheader sub;
    private JLabel title;

    public Header(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(getWidth(), 70));

        sub = new Subheader();
        add(sub,BorderLayout.SOUTH);

        add(Box.createRigidArea(new Dimension(300, 10)), BorderLayout.NORTH);
        
        title = new JLabel("Default Color Matrix", JLabel.CENTER);
        title.setFont(new Font("Karla", Font.PLAIN, 30));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);
    }

     @Override
    public void paintComponent(Graphics g){ 
        mImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics mg = mImage.getGraphics();  

        mg.setColor(new Color(38, 39, 33));
        mg.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
    }

    public void update(CustomColor c, String labelText){
        sub.refresh(c);
        if(labelText != null)
            title.setText(labelText);
    }
}
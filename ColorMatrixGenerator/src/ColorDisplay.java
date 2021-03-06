import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*; //For graphics

public class ColorDisplay extends JPanel{
    private BufferedImage mImage;
    public ColorMatrix colors;
    private Header header;

    public int xSelected;
    public int ySelected;

    public ColorDisplay(){
        setLayout(new BorderLayout());
        colors = new ColorMatrix();
        
        header = new Header();
        add(header, BorderLayout.NORTH);
        
        xSelected = -1;
        ySelected = -1;
    }

     @Override
    public void paintComponent(Graphics g){ 
        draw();
        g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
    }

    public void draw(){
        mImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);     
        Graphics g = mImage.getGraphics();  

        int height = getHeight()-header.getHeight();
        int width = getWidth();

        g.setColor(new Color(30, 31, 28));
        g.fillRect(0, 0, getWidth(), getHeight());

        int gapW = (int) (width*0.9)/colors.resolution;
        int gapH = (int) (height*0.9)/colors.resolution;

        int xOffset = (width - (gapW*colors.resolution))/2;
        int yOffset = (height - (gapH*colors.resolution))/2 + header.getHeight();

        for(int x = 0; x < colors.resolution; x++){
            for(int y = 0; y < colors.resolution; y++){
                g.setColor(colors.colorAt(x, y).color);
                g.fillRect(x*gapW + xOffset, y*gapH + yOffset, gapW, gapH);
            }
        }

        if(xSelected > -1 && ySelected > -1 && xSelected < colors.resolution && ySelected < colors.resolution){
            Graphics2D g2 = (Graphics2D) g;
            float max = 4.0f;
            if(colors.resolution > 10)
                max = 3.0f;
            else if(colors.resolution > 50)
                max = 2.0f;
            else if(colors.resolution > 125)
                max = 1.0f;

            g2.setStroke(new BasicStroke(max));
            g.setColor(Color.WHITE);
            g.drawRect(xSelected*gapW + xOffset, ySelected*gapH + yOffset, gapW, gapH);
        }
    }

    public CustomColor shift(int dx, int dy){
        if(xSelected + dx >= 0 && xSelected + dx < colors.resolution)
            xSelected += dx;
        if(ySelected + dy >= 0 && ySelected + dy < colors.resolution)
            ySelected += dy;

        return colors.colorAt(xSelected, ySelected);
    }

    public CustomColor select(int newX, int newY){
        int height = getHeight()-header.getHeight();
        int width = getWidth();

        int gapW = (int) (width*0.9)/colors.resolution;
        int gapH = (int) (height*0.9)/colors.resolution;

        int xOffset = (width - (gapW*colors.resolution))/2;
        int yOffset = (height - (gapH*colors.resolution))/2 + header.getHeight();

        if(newX < xOffset || newX > width-xOffset){
            xSelected = -1;
            ySelected = -1;
        }
        else if(newY < yOffset || newY > getHeight()-(height - (gapH*colors.resolution))/2){
            xSelected = -1;
            ySelected = -1;
        }
        else{
            for(int x = 0; x < colors.resolution; x++){
                for(int y = 0; y < colors.resolution; y++){
                    if(newX > x*gapW + xOffset && newX <= x*gapW + xOffset + gapW){
                        if(newY > y*gapH + yOffset && newY <= y*gapH + yOffset + gapH){
                            xSelected = x;
                            ySelected = y;
                            break;
                        }
                    }
                }
            }
        }

        update(colors.colorAt(xSelected, ySelected), null);
        return colors.colorAt(xSelected, ySelected);
    }

    public void update(CustomColor c, String headerLabel){
        header.update(c, headerLabel);
        repaint();
    }

    public void setRandom(){
        colors.randomInterplolation();
        update(colors.colorAt(xSelected, ySelected), "Random Matrix");
    }

    public void isolateColor(){
        colors.isolateColor();
        update(colors.colorAt(xSelected, ySelected), "Isolated Color Matrix");
    }

    public void setResolution(String newValue){
        colors.setResolution(newValue);
        update(colors.colorAt(xSelected, ySelected), null);
    }

    public void save(){
        xSelected = -1;
        ySelected = -1;
        update(colors.colorAt(xSelected, ySelected), null);

        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        paint(g);
        try {
            File homeDirectory = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory();
            System.out.println("Saved to" + homeDirectory.getAbsolutePath());
            ImageIO.write(image, "png", new File(homeDirectory.getAbsolutePath() + "/" + colors.generateID() + ".png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public CustomColor getColor(String position){
        if(position == "tl")
            return colors.topLeft;
        else if(position == "tr")
            return colors.topRight;
        else if(position == "bl")
            return colors.bottomLeft;
        else if(position == "br")
            return colors.bottomRight;
        return null;
    }

    public CustomColor setColor(String position, String value, String color){
        try{
            int number = Integer.parseInt(value);
            if(number >= 0 && number < 256){
                CustomColor c = getColor(position);

                if(color == "Red"){
                    int green = c.green;
                    int blue = c.blue;
                    c = new CustomColor(number, green, blue);
                }
                else if(color == "Green"){
                    int red = c.red;
                    int blue = c.blue;
                    c = new CustomColor(red, number, blue);
                }
                else if(color == "Blue"){
                    int red = c.red;
                    int green= c.green;
                    c = new CustomColor(red, green, number);
                }

                colors.setColor(c, position);
                colors.generateColors();
                update(c, "Modified Matrix");
                return c;
            }
        }
        finally{ }
        return null;
    }
}
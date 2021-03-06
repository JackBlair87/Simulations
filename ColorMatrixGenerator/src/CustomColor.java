import java.awt.*;

public class CustomColor {
    public int red;
    public int green;
    public int blue;

    public String hex;

    public int hue;
    public int saturation;
    public int lightness;

    private int percentRed;
    private int percentGreen;
    private int percentBlue;

    public Color color;

    public CustomColor(int r, int g, int b){
        update(r, g, b);
    }

    public CustomColor(){
        makeRandom();
    }

    public CustomColor(Color c){
        update(c.getRed(), c.getGreen(), c.getBlue());
    }

    public void makeRandom(){
        int newRed = (int) (Math.random() * 256);
        int newGreen = (int) (Math.random() * 256);
        int newBlue = (int) (Math.random() * 256);

        update(newRed, newGreen, newBlue);
    }

    public void update(int r, int g, int b){
        red = r;
        green = g;
        blue = b;

        color = new Color(red, green, blue);
        refreshHex();
        refreshHSL();
        refreshPercents();
    }

    public void refreshHex(){
        String[] hexCodes = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String hexAnswer = "";

        hexAnswer += hexCodes[(int) red / 16];
        hexAnswer += hexCodes[(int) (red % 16)];

        hexAnswer += hexCodes[(int) green / 16];
        hexAnswer += hexCodes[(int) (green % 16)];

        hexAnswer += hexCodes[(int) blue / 16];
        hexAnswer += hexCodes[(int) (blue % 16)];

        this.hex = hexAnswer;
    }

    public void refreshPercents(){
        int sum = red + green + blue;
        if(sum == 0){
            this.percentRed = 0;
            this.percentGreen = 0;
            this.percentBlue = 0;
        }
        else{
            this.percentRed = (int) ((red / sum) * 100);
            this.percentGreen = (int) (green / sum) * 100;
            this.percentBlue = (int) (blue / sum) * 100;
        }
    }

    public void refreshHSL(){
        //https://www.rapidtables.com/convert/color/rgb-to-hsl.html
        double newRed = (float) red / (float) 255;
        double newGreen = (float) green / (float)255;
        double newBlue = (float) blue /(float) 255;

        double cMax = Math.max(Math.max(newRed, newGreen), newBlue);
        double cMin = Math.min(Math.min(newRed, newGreen), newBlue);
        double delta = cMax-cMin;

        double hue = 0;
        if(delta != 0){
            if(cMax == newRed)
                hue = 0.6 * (((newGreen-newBlue)/delta));
            else if(cMax == newGreen)
                hue = 0.6 * (((newBlue-newRed)/delta) + 2);
            else
                hue = 0.6 * (((newRed-newGreen)/delta) + 4);
        }

        if(hue < 0)
            hue += 3.6;

        //Lightness calculation:
        double lightness = (cMax + cMin) / 2.0;

        //Saturation calculation:
        double saturation = 0;
        if(delta != 0)
            saturation = delta / (1 - Math.abs(2 * lightness -1));

        this.hue = (int) Math.round(hue * 100);
        this.saturation = (int) Math.round(saturation * 100);
        this.lightness = (int) Math.round(lightness * 100);
    }

    public Color toColor(){
        return color;
    }
}
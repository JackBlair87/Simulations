import java.awt.*; //For graphics

public class ColorMatrix {
    
    public int resolution = 10;
    private CustomColor[][] matrix;

    public CustomColor topLeft;
    public CustomColor topRight;
    public CustomColor bottomLeft;
    public CustomColor bottomRight;

    public ColorMatrix(){
        matrix = new CustomColor[resolution][resolution];
        topLeft = new CustomColor(2, 42, 106);
        topRight = new CustomColor(201, 33, 183);
        bottomLeft = new CustomColor(228, 110, 33);
        bottomRight = new CustomColor(200, 20, 20);
        generateColors();
    }
    
    public void generateColors(){
        Color tl = topLeft.toColor();
        Color tr = topRight.toColor();
        Color bl = bottomLeft.toColor();
        Color br = bottomRight.toColor();

        for(int x = 0; x < resolution; x++){
            for(int y = 0; y < resolution; y++){
                matrix[x][y] = new CustomColor(
                    bilinearInterpolateColor(tl, tr, bl, br, (float) x / (float) resolution, (float) y / (float) resolution));
            }
        }
    }

    public void randomInterplolation(){
        topLeft = new CustomColor();
        topRight = new CustomColor();
        bottomLeft = new CustomColor();
        bottomRight = new CustomColor();
        generateColors();
    }

    public void isolateColor(){
        topRight = new CustomColor(255, 255, 255);
        bottomLeft = new CustomColor(0, 0, 0);
        bottomRight = new CustomColor(0, 0, 0);
        generateColors();
    }

   //https://harmoniccode.blogspot.com/2011/04/bilinear-color-interpolation.html
    private Color bilinearInterpolateColor(final Color COLOR_00, Color COLOR_10, final Color COLOR_01, final Color COLOR_11, final float FRACTION_X, final float FRACTION_Y)
    {
        final Color COLOR_X1 = interpolateColor(COLOR_00, COLOR_10, FRACTION_X);
        final Color COLOR_X2 = interpolateColor(COLOR_01, COLOR_11, FRACTION_X);
        return interpolateColor(COLOR_X1, COLOR_X2, FRACTION_Y);
    }

    private java.awt.Color interpolateColor(final Color COLOR1, final Color COLOR2, float fraction)
    {            
        final float INT_TO_FLOAT_CONST = 1f / 255f;
        fraction = Math.min(fraction, 1f);
        fraction = Math.max(fraction, 0f);
        
        final float RED1 = COLOR1.getRed() * INT_TO_FLOAT_CONST;
        final float GREEN1 = COLOR1.getGreen() * INT_TO_FLOAT_CONST;
        final float BLUE1 = COLOR1.getBlue() * INT_TO_FLOAT_CONST;
        final float ALPHA1 = COLOR1.getAlpha() * INT_TO_FLOAT_CONST;

        final float RED2 = COLOR2.getRed() * INT_TO_FLOAT_CONST;
        final float GREEN2 = COLOR2.getGreen() * INT_TO_FLOAT_CONST;
        final float BLUE2 = COLOR2.getBlue() * INT_TO_FLOAT_CONST;
        final float ALPHA2 = COLOR2.getAlpha() * INT_TO_FLOAT_CONST;

        final float DELTA_RED = RED2 - RED1;
        final float DELTA_GREEN = GREEN2 - GREEN1;
        final float DELTA_BLUE = BLUE2 - BLUE1;
        final float DELTA_ALPHA = ALPHA2 - ALPHA1;

        float red = RED1 + (DELTA_RED * fraction);
        float green = GREEN1 + (DELTA_GREEN * fraction);
        float blue = BLUE1 + (DELTA_BLUE * fraction);
        float alpha = ALPHA1 + (DELTA_ALPHA * fraction);

        red = Math.min(red, 1f);
        red = Math.max(red, 0f);
        green = Math.min(green, 1f);
        green = Math.max(green, 0f);
        blue = Math.min(blue, 1f);
        blue = Math.max(blue, 0f);
        alpha = Math.min(alpha, 1f);
        alpha = Math.max(alpha, 0f);

        return new Color(red, green, blue, alpha);        
    }

	public CustomColor colorAt(int x, int y) {
        if(x < 0 || y < 0 || x >= resolution || y >= resolution)
            return null;
		return matrix[x][y];
	}

    public void setResolution(String newValue){
        try{
            int num = Integer.parseInt(newValue);
            if(num > 0 && num < 256)
                resolution = num;
        }
        finally{ }
        matrix = new CustomColor[resolution][resolution];
        generateColors();
    }

    public void setColor(CustomColor c, String position){
        if(position == "tl")
            topLeft = c;
        else if(position == "tr")
            topRight = c;
        else if(position == "bl")
            bottomLeft = c;
        else if(position == "br")
            bottomRight = c;
    }

    public String generateID(){
        return topLeft.hex + topRight.hex + bottomLeft.hex + bottomRight.hex;
    }
}
package vector.saver;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import javax.imageio.ImageIO;

/**
 *
 * @author Bolotin Sergey
 */
public class ItemProperty {
    public String propertyName = new String();
    public String property = new String();
    
    public ItemProperty(String propertyName, Object object, Class type) {
        Gson gson = new Gson();
        this.propertyName = propertyName;
        this.property = gson.toJson(object, type);
    }
    
    public ItemProperty(String propertyName, BufferedImage bImage) throws IOException{
        Gson gson = new Gson();
        this.propertyName = propertyName;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", stream);
        this.property = gson.toJson(stream);
    }

    public <T> T getProperty(Type type) {
        Gson gson = new Gson();
        return (T)gson.fromJson(this.property, type);
    }
   
    public BufferedImage getImageProperty() {
        ByteArrayInputStream stream = new Gson().fromJson(this.property, ByteArrayInputStream.class);
        try {
            return ImageIO.read(stream);
        } catch (IOException ex) {
            return null;
        }
    }
    
}
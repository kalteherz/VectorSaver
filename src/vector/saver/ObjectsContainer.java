package vector.saver;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bolotin Sergey
 */
public class ObjectsContainer {
    private String  format, version;
    public String authorName;
    public ArrayList<Item> items;
    
    private void setParam(String format, String version, String authorName, ArrayList<Item> items){
        this.format = format;
        this.version = version;
        this.items = items;
        this.authorName = authorName;
    }
    
    public final void assign(ObjectsContainer other){
        setParam(other.format, other.version, other.authorName, other.items);
    }
    
    public ObjectsContainer(){
        setParam("VectorB8219a", "1.1", "", new ArrayList<Item>());
    }    

    public ObjectsContainer(ObjectsContainer other){
        assign(other);
    }

    public ObjectsContainer(File file){
        loadFromFile(file);
    }
    
    public ObjectsContainer(String json){
        this(new Gson().fromJson(json, ObjectsContainer.class));
    }
    
    public String toJson(){
        return new Gson().toJson(this, ObjectsContainer.class);
    }
    
    public String getFormat(){
        return format;
    }
    
    public String getVersion(){
        return version;
    }
    
    private void writeFile(String str, DataOutputStream out) throws IOException {
        out.writeChar((char)(str.length() >>> 16));
        out.writeChar((char)(str.length() >>> 0));
        out.writeChars(str);
    }

    private String readFile(DataInputStream in) throws IOException {
        int length = (int)Math.pow(2, 16) * (int)in.readChar() + (int)in.readChar();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            sb.append(in.readChar());
        }
        return sb.toString();
    }
    
    public boolean saveToFile(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);
            writeFile(toJson(), dos);
            dos.close();
            bos.close();
            fos.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    public boolean saveToFile(String fileName){
        return saveToFile(new File(fileName));
    }

    public final boolean loadFromFile(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            assign(new ObjectsContainer(readFile(dis)));
            dis.close();
            bis.close();
            fis.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    public boolean loadFromFile(String fileName){
        return loadFromFile(new File(fileName));
    }    
    
}
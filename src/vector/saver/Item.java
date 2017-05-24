package vector.saver;

import java.util.ArrayList;

/**
 *
 * @author Bolotin Sergey
 */
public class Item {
    public String itemClass = new String();
    public ArrayList<ItemProperty> properties = new ArrayList();
    
    public Item(String itemClass){
        this.itemClass = itemClass;
    }
}
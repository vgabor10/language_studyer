package language_studyer;

public class Category {
    
    public int index;
    public String name;
    
    public Category() {
        index = -1;
        name = "";
    }
    
    @Override
    public String toString() {
        return Integer.toString(index) + " " + name;
    }
    
}

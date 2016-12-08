package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryContainer {

    private List<CardCategory> data = new ArrayList<>();
    private Map<Integer, Integer> categoryIndexToOrder = new HashMap<>();
    
    public void add(CardCategory category) {
        data.add(category);
        categoryIndexToOrder.put(category.index, data.size()-1);
    }
    
    public int numberOfItems() {
        return data.size();
    }

    public CardCategory getCategoryByOrder(int order) {
        return data.get(order);
    }
    
    public String getCategoryNameByOrder(int order) {
        return data.get(order).name;
    }
    
    public CardCategory getCategoryByIndex(int categoryIndex) {
        return data.get(categoryIndexToOrder.get(categoryIndex));
    }    
    
    public String getCategoryNameByIndex(int categoryIndex) {
        return data.get(categoryIndexToOrder.get(categoryIndex)).name;
    }
    
    public boolean containsCategoryWithIndex(int categoryIndex) {
        boolean out = true;
        
        int i=0;
        while (i<data.size() && data.get(i).index != categoryIndex) {
            i++;
        }
        
        if (i==data.size()) {
            out = false;
        }
        
        return out;
    }
    
    public void removeCategoryByOrder(int order) {
        categoryIndexToOrder.remove(data.get(order).index);
        data.remove(order);
    }
    
    public Set<Integer> getCategoryIndexes() {
        Set<Integer> out = new HashSet<>();
        
        for (int i=0; i<data.size(); i++) {
            out.add(data.get(i).index);
        }
        
        return out;
    }
    
    public void clear() {
        data.clear();
        categoryIndexToOrder.clear();
    }
    
}

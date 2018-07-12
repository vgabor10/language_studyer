package grammar_book;

import java.util.Arrays;
import java.util.Vector;

public class GrammarItemTitle {

    private Vector<String> categoris = new Vector<>();

    public GrammarItemTitle() {
    }

    public GrammarItemTitle(GrammarItemTitle git) {
        categoris = new Vector<String>(git.getCategorisInVector());
    }

    public int getDebth() {
        return categoris.size();
    }

    public Vector<String> getCategorisInVector() {
        return categoris;
    }

    public void setTitleFromString(String titleString) {
        clear();
        String[] splitedTitleString = titleString.split("/");
        categoris.addAll(Arrays.asList(splitedTitleString));
    }
    
    @Override
    public String toString() {
        String out = "";
        if (!categoris.isEmpty()) {
            out = categoris.get(0);
            for (int i = 1; i < categoris.size(); i++) {
                out = out + "/" + categoris.get(i);
            }
        }
        return out;
    }
    
   public String toStringReverse() {
        String out = "";
        if (!categoris.isEmpty()) {
            out = categoris.get(categoris.size()-1);
            for (int i = categoris.size()-2; 0 <= i; i--) {
                out = out + " \\ " + categoris.get(i);
            }
        }
        return out;
    }

    public void clear() {
        categoris.clear();
    }

}

package grammar_book;

import study_item_objects.StudyItem;

import java.util.*;

public class GrammarItem extends StudyItem {

    public GrammarItemTitle title = new GrammarItemTitle();
    public String description;
    public String comments;
    private Map<Integer, Example> examples = new TreeMap<>();

    public GrammarItem() {
        title.clear();
        comments = "";
        description = "";
    }

    public int numberOfExamples() {
        return examples.size();
    }

    public boolean isEmpty() {
        return (index == -1) && (description.equals("")) && (examples.isEmpty()) && (comments.equals(""));
    }

    public Example getExampleByIndex(int index) {
        return examples.get(index);
    }

    public void addExample(Example e) {
        examples.put(e.index, e);
    }

    public Set<Integer> getExampleIndexes() {
        return examples.keySet();
    }

    public void clearExamples() {
        examples.clear();
    }

    @Override
    public String toString() {
        String outString = "";

        outString = "\\title{"+ title.toString() + "}\n\n";
        
        outString = outString + "GrammarItemIndex = " + Integer.toString(index) + "\n\n";

        if (description.equals("")) {
            outString = outString + "\\begin{desc}\n\\end{desc}\n\n";
        } else {
            outString = outString + "\\begin{desc}\n" + description + "\n\\end{desc}\n\n";
        }

        if (comments.length() != 0) {
            outString = outString + comments + "\n";
        }
        outString = outString + "\\begin{exmp}\n";
        for (int i : getExampleIndexes()) {
            outString = outString + getExampleByIndex(i).toStringInLatexFormat() + "\n";
        }
        outString = outString + "\\end{exmp}\n";

        return outString;
    }
    
}

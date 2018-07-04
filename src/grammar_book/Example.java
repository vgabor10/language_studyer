package grammar_book;

public class Example {

    public int grammarItemIndex = -1;
    public int index = -1;
    public String hun;
    public String foreign;

    public Example() {
        index = -1;
    }

    @Override
    public String toString() {
        return Integer.toString(index) + "\t" + foreign + " - " + hun;
    }

    public String toStringInLatexFormat() {
        return Integer.toString(index) + " | " + foreign + " | " + hun;
    }

}

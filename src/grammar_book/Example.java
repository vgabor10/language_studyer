package grammar_book;

public class Example {

	public int index;
	public String hun;
	public String foreign;

	Example() {
		index = -1;
	}

        @Override
	public String toString() {
		return Integer.toString(index) + "\t"  + foreign + " - " + hun;
	}

	public String toStringInLatexFormat() {
		return Integer.toString(index) + " | "  + foreign + " | " + hun + "\\\\";
	}

}

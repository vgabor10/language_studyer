package grammar_book;

import java.util.*;

public class GrammarItemTitle {

    private Vector<String> categoris = new Vector<String>();

    public GrammarItemTitle() {
    }

    public GrammarItemTitle(GrammarItemTitle git) {
        categoris = new Vector<String>(git.getCategorisInVector());
    }

    public void setSection(String section) {
        while (categoris.size() < 1) {
            categoris.add("-");
        }
        categoris.setElementAt(section, 0);
    }

    public void setSubsection(String subsection) {
        while (categoris.size() < 2) {
            categoris.add("-");
        }
        categoris.setElementAt(subsection, 1);
    }

    public void setSubsubsection(String subsubsection) {
        while (categoris.size() < 3) {
            categoris.add("-");
        }
        categoris.setElementAt(subsubsection, 2);
    }

    public void setParagraph(String paragraph) {
        while (categoris.size() < 4) {
            categoris.add("-");
        }
        categoris.setElementAt(paragraph, 3);
    }

    public String getSection() {
        return categoris.get(0);
    }

    public String getSubsection() {
        return categoris.get(1);
    }

    public String getSubSubsection() {
        return categoris.get(2);
    }

    public String getParagraph() {
        return categoris.get(3);
    }

    public int getDebth() {
        return categoris.size();
    }

    public void deleteCategoriesFromDebth(int debth) {
        while (debth < categoris.size()) {
            categoris.removeElementAt(debth);
        }
    }

    public Vector<String> getCategorisInVector() {
        return categoris;
    }

    public String getInLatexFormatAfterGivenTitle(GrammarItemTitle grammarItemTitle) {

        Vector<String> thisGit = this.getCategorisInVector();
        Vector<String> otherGit = grammarItemTitle.getCategorisInVector();

        int diffIndex = 0;
        while (diffIndex < otherGit.size() &&
                diffIndex < thisGit.size() && 
                thisGit.get(diffIndex).equals(otherGit.get(diffIndex))) {
            diffIndex++;
        }
        
        String out = "";
        
        for (int i = diffIndex; i < thisGit.size(); i++) {
            if (i == 0) {
                out = out + "\\section{" + thisGit.get(i) + "}\n\n";
            }

            if (i == 1) {
                out = out + "\\subsection{" + thisGit.get(i) + "}\n\n";
            }

            if (i == 2) {
                out = out + "\\subsubsection{" + thisGit.get(i) + "}\n\n";
            }

            if (i == 3) {
                out = out + "\\paragraph{" + thisGit.get(i) + "}\n\n";
            }
        }

        return out;
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

    public void clear() {
        categoris.clear();
    }

}

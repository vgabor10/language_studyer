package grammar_book;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GrammarItemModificatorFrame extends JPanel implements ActionListener {

	protected JLabel indexLabel;

	protected JTextField indexTextField;
	private JTextField titleTextField = new JTextField();

   	protected JTextArea descriptionTextArea;
	protected JTextArea examplesTextArea;

	protected JLabel titleLabel;
	protected JLabel grammarItemTitleLabel;
	protected JLabel descriptionLabel;
	protected JLabel examplesLabel;
	protected JButton saveButton;
	protected JButton cancelButton;

	public Component createComponents() {

		indexLabel = new JLabel("Index:");
		titleLabel = new JLabel("Title:");
		descriptionLabel = new JLabel("Description:");
		examplesLabel = new JLabel("Examples:");

		indexTextField = new JTextField();

		descriptionTextArea = new JTextArea(3,3);
		descriptionTextArea.setEditable(true);

		JScrollPane scrollPane = new JScrollPane(descriptionTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		examplesTextArea = new JTextArea(3,3);
		examplesTextArea.setEditable(true);

		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");

		JPanel pane = new JPanel(new GridLayout(0, 1));
		pane.add(indexLabel);
		pane.add(indexTextField);
		pane.add(titleLabel);
		pane.add(titleTextField);
		pane.add(descriptionLabel);
		pane.add(scrollPane);
		pane.add(examplesLabel);
		pane.add(examplesTextArea);
		pane.add(saveButton);
		pane.add(cancelButton);

		return pane;
	}

	public void setGrammarItemTitle(String title) {
		titleTextField.setText(title);
	}

	public void actionPerformed(ActionEvent evt) {
        /*String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());*/
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

	public /*static*/ void createAndShowGUI() {
		JFrame frame = new JFrame("modificate grammar item");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//JComponent newContentPane = new GrammarItemModificatorFrame();
		//setOpaque(true);
		//frame.setContentPane(this);

		Component contents = createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

    /*public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }*/
}

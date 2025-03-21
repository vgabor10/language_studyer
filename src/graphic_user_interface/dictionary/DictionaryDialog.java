package graphic_user_interface.dictionary;

import dictionary.Card;
import dictionary.CardFinder;
import dictionary.Dictionary;
import graphic_user_interface.common.DialogAnswer;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class DictionaryDialog extends javax.swing.JDialog {

    private Dictionary dictionary;
    private CardFinder cardFinder;
    
    private final DefaultTableModel tableModel;
   
    public DictionaryDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tableModel = (DefaultTableModel)jTable1.getModel();
        
        searchModeComboBox.setSelectedIndex(1);
        
        closeButton.setMnemonic(KeyEvent.VK_C);
        addNewCardButton.setMnemonic(KeyEvent.VK_A);
        inspectCardButton.setMnemonic(KeyEvent.VK_I);
        listAllCardsButton.setMnemonic(KeyEvent.VK_L);
        categoryFilterButton.setMnemonic(KeyEvent.VK_F);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        searchDirectionComboBox = new javax.swing.JComboBox<>();
        inspectCardButton = new javax.swing.JButton();
        addNewCardButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        categoryFilterButton = new javax.swing.JButton();
        listAllCardsButton = new javax.swing.JButton();
        searchModeComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "term", "definition"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        searchDirectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "foreign -> hungarian", "hungarian -> foreign" }));
        searchDirectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchDirectionComboBoxActionPerformed(evt);
            }
        });

        inspectCardButton.setText("Inspect selected card");
        inspectCardButton.setEnabled(false);
        inspectCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inspectCardButtonActionPerformed(evt);
            }
        });

        addNewCardButton.setText("Add new card");
        addNewCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCardButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setText("number of results found:");

        categoryFilterButton.setText("Category filter");
        categoryFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryFilterButtonActionPerformed(evt);
            }
        });

        listAllCardsButton.setText("List all");
        listAllCardsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAllCardsButtonActionPerformed(evt);
            }
        });

        searchModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "enter key press refresh", "any key press refresh" }));
        searchModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchModeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchDirectionComboBox, 0, 1, Short.MAX_VALUE)
                            .addComponent(addNewCardButton, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inspectCardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchModeComboBox, 0, 1, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(categoryFilterButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(listAllCardsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchDirectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryFilterButton)
                    .addComponent(searchModeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewCardButton)
                    .addComponent(inspectCardButton)
                    .addComponent(listAllCardsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addNewCardButton, inspectCardButton, listAllCardsButton});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {categoryFilterButton, searchDirectionComboBox, searchModeComboBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.cardFinder = dictionary.cardFinder;
        
        cardFinder.clearExceptCardContainer();
        
        jTextField1.setText("");
        jTextField1.requestFocus();
        jLabel1.setText("-");
        
        searchDirectionComboBox.removeAllItems();
        searchDirectionComboBox.addItem(dictionary.discFilesMetaDataHandler.getStudiedLanguageName().toLowerCase()
                + " -> hungarian");
        searchDirectionComboBox.addItem("hungarian -> " +
        dictionary.discFilesMetaDataHandler.getStudiedLanguageName().toLowerCase());
        
        setTitle("Dictionary - " + dictionary.discFilesMetaDataHandler.getStudiedLanguageName());
    }
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void clearTable() {
        for (int i=tableModel.getRowCount()-1; 0<=i; i--) {
            tableModel.removeRow(i);
        }
        jLabel1.setText("-");
    }

    private void toScreenCards(List<Card> cardList) {
        clearTable();
        
        if (searchDirectionComboBox.getSelectedIndex() == 0) {
            for (int i = 0; i < cardList.size(); i++) {
                tableModel.addRow(new Object[]{
                    cardList.get(i).term,
                    cardList.get(i).definition});
            }
        }

        if (searchDirectionComboBox.getSelectedIndex() == 1) {
            for (int i = 0; i < cardList.size(); i++) {
                tableModel.addRow(new Object[]{
                    cardList.get(i).definition,
                    cardList.get(i).term});
            }
        }
        
        jLabel1.setText(Integer.toString(cardList.size()));
    }
    
    private void inspectCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inspectCardButtonActionPerformed
        int selectedTableRowIndex = jTable1.getSelectedRow();
        showCardInspectorDialog(selectedTableRowIndex);
    }//GEN-LAST:event_inspectCardButtonActionPerformed

    private void addNewCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewCardButtonActionPerformed
        DialogAnswer dialogAnswer = new DialogAnswer();

        CardAdderDialog dialog 
                = new CardAdderDialog(new javax.swing.JFrame(), true);
        dialog.setDictionary(dictionary);
        dialog.dialogAnswer = dialogAnswer;
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialogAnswer.stringAnswer.equals("save_card")) {           
            clearTable();
            cardFinder.makeSearch();
            toScreenCards(cardFinder.foundCards);
        }

        jTextField1.requestFocus();
    }//GEN-LAST:event_addNewCardButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        inspectCardButton.setEnabled(true);
        
        if (evt.getClickCount() == 2) {
                int selectedTableRowIndex = jTable1.getSelectedRow();
                showCardInspectorDialog(selectedTableRowIndex);
        }
    }//GEN-LAST:event_jTable1MouseClicked
    
    private void showCardInspectorDialog(int selectedTableRowIndex) {
        Card cardToInspect = cardFinder.foundCards.get(selectedTableRowIndex);
        
        CardInspectorDialog dialog 
                = new CardInspectorDialog(new javax.swing.JFrame(), true);
        dialog.setDictionary(dictionary);
        dialog.setCardToInspect(cardToInspect);
        DialogAnswer dialogAnswer = new DialogAnswer();
        dialog.dialogAnswer = dialogAnswer;
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialogAnswer.stringAnswer.equals("delete_card") 
                || dialogAnswer.stringAnswer.equals("save_card")) {
            
            clearTable();
            cardFinder.makeSearch();
            toScreenCards(cardFinder.foundCards);
            inspectCardButton.setEnabled(false);
        }

        jTextField1.requestFocus();
    }
    
    private void searchDirectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchDirectionComboBoxActionPerformed
        
        JTableHeader th = jTable1.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        TableColumn tc0 = tcm.getColumn(0);
        TableColumn tc1 = tcm.getColumn(1);
        
        if( searchDirectionComboBox.getSelectedIndex() == 0) {
            tc0.setHeaderValue("term");
            tc1.setHeaderValue("definition");
            
            cardFinder.setSearchAccordingToTerm();
        }
        
        if( searchDirectionComboBox.getSelectedIndex() == 1) {
            tc0.setHeaderValue("definition");
            tc1.setHeaderValue("term");
            
            cardFinder.setSearchAccordingToDefinition();
        }
        
        th.repaint();
        clearTable();
        inspectCardButton.setEnabled(false);
        
        jTextField1.setText("");
        jTextField1.requestFocus();
    }//GEN-LAST:event_searchDirectionComboBoxActionPerformed

    private void listAllCardsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAllCardsButtonActionPerformed
        jTextField1.setText("");
        cardFinder.setAnyStringAccepted();
        cardFinder.makeSearch();
        toScreenCards(cardFinder.foundCards);
        
        inspectCardButton.setEnabled(false);
        
        jTextField1.setText("");
        jTextField1.requestFocus();   
    }//GEN-LAST:event_listAllCardsButtonActionPerformed

    private void categoryFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryFilterButtonActionPerformed
        DialogAnswer dialogAnswer = new DialogAnswer();
        
        CardCategoryFilterDialog dialog 
                = new CardCategoryFilterDialog(new javax.swing.JFrame(), true);
        
        dialog.dialogAnswer = dialogAnswer;
        dialog.setDictionary(dictionary);

        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        if (dialogAnswer.stringAnswer.equals("ok_button_pressed")) {
            clearTable();
        }
        
        jTextField1.requestFocus();
    }//GEN-LAST:event_categoryFilterButtonActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (searchModeComboBox.getSelectedIndex() == 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER 
                    && !jTextField1.getText().isEmpty()) {

                clearTable();

                cardFinder.setStringToSearch(jTextField1.getText());
                cardFinder.makeSearch();
                toScreenCards(cardFinder.foundCards);

                jTextField1.setText("");
                inspectCardButton.setEnabled(false);
            }
            else {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER &&
                    jTextField1.getText().isEmpty()) {
                    clearTable();
                    inspectCardButton.setEnabled(false);
                }
            }
        }
        else {
            if (!jTextField1.getText().isEmpty()) {
                clearTable();

                cardFinder.setStringToSearch(jTextField1.getText());
                cardFinder.makeSearch();
                toScreenCards(cardFinder.foundCards);

                inspectCardButton.setEnabled(false);
            }
            else {
                clearTable();
            }
            
            if (evt.getKeyCode() == KeyEvent.VK_ENTER ) {               
                inspectCardButton.setEnabled(false);
                jTextField1.setText("");
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void searchModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchModeComboBoxActionPerformed
        jTextField1.setText("");
        jTextField1.requestFocus();
    }//GEN-LAST:event_searchModeComboBoxActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DictionaryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DictionaryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DictionaryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DictionaryDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DictionaryDialog dialog = new DictionaryDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewCardButton;
    private javax.swing.JButton categoryFilterButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton inspectCardButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton listAllCardsButton;
    private javax.swing.JComboBox<String> searchDirectionComboBox;
    private javax.swing.JComboBox<String> searchModeComboBox;
    // End of variables declaration//GEN-END:variables
}

package graphic_user_interface.dictionary;

import study_item_objects.AnswerDataContainer;
import common.Logger;
import disc_operation_handlers.DictionaryDataModificator;
import dictionary.CardChooser;
import dictionary.CardTester;
import dictionary.DictionaryDataContainer;
import graphic_user_interface.common.DialogAnswer;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

public class CardTesterDialog extends javax.swing.JDialog {

    private CardTester cardTester;
    private CardChooser cardChooser;
    public DictionaryDataContainer dictionaryDataContainer;
    
    private final DefaultTableModel acceptableAnswersTableModel;
    private final DefaultTableModel exampleSentencesTableModel;
    private final Logger logger = new Logger();
    private long startTime;
    private long finishTime;

    public CardTesterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        acceptableAnswersTableModel = (DefaultTableModel) jTable1.getModel();
        exampleSentencesTableModel = (DefaultTableModel) jTable2.getModel();

        closeButton.setMnemonic(KeyEvent.VK_C);
        inspectCardButton.setMnemonic(KeyEvent.VK_I);
    }

    public void initialise() {  
        cardChooser = new CardChooser();
        cardChooser.setCardContainer(dictionaryDataContainer.cardContainer);
        cardChooser.setAnswerDataContainer(dictionaryDataContainer.answerDataContainer);
        
        AnswerDataContainer answerDataContainer = dictionaryDataContainer.answerDataContainer;
        
        Set<Integer> cardIndexesToTest;
        if (answerDataContainer.numberOfAnswers() > 100) {
            cardIndexesToTest = cardChooser.getCardIndexes();
        } else {
            cardIndexesToTest = cardChooser.getRandomCardIndexes(20, new HashSet<Integer>());
        }
        
        cardTester = new CardTester();
        cardTester.setAllCard(dictionaryDataContainer.cardContainer);
        cardTester.setCardsToTestFromCardIndexesSet(cardIndexesToTest);

        cardTester.moveToNextCardToQuestion();

        jTextField1.requestFocus();

        clearTabulars();
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText(cardTester.getActualQuestionedCard().definition);
        jLabel2.setText(cardTester.numberOfCardsQuestioned() + "\\" + cardTester.getNumberOfQuestions());

        startTime = new Date().getTime();
    }

    public void setData(DictionaryDataContainer ddc) {
        dictionaryDataContainer = ddc;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        inspectCardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel2.setText("jLabel2");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jTextField3.setText("jTextField3");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 0));
        jTextField2.setText("jTextField2");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Term", "Definition"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Example sentences"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setEnabled(false);
        jScrollPane2.setViewportView(jTable2);

        inspectCardButton.setText("Inspect card");
        inspectCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inspectCardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inspectCardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(inspectCardButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!cardTester.isGetAnswerToActualQuestion()) {

                cardTester.setUserAnswer(jTextField1.getText());
                
                if (cardTester.isUserAnswerRight()) {
                    if (cardTester.getActualQuestionedCard().term.equals(cardTester.getUserActualAnswer())) {
                        jTextField2.setForeground(new Color(45, 107, 53));   //green   
                    }
                    else {
                        jTextField2.setForeground(new Color(51,194,242));   //light blue
                        jTextField1.setText("");
                    }
                } else {
                    jTextField2.setForeground(new Color(255,71,71));  //light red
                    jTextField1.setText("");
                }

                jTextField2.setText(cardTester.getActualQuestionedCard().term);
                showAcceptableCards();
                showExampleSentences();
                
            } else {
                if (cardTester.getActualQuestionedCard().term.equals(
                    cardTester.getUserActualAnswer())) {
                    
                    if (cardTester.isMoreCardToTest()) {
                        cardTester.moveToNextCardToQuestion();
                        jLabel2.setText(cardTester.numberOfCardsQuestioned() + "\\" + cardTester.getNumberOfQuestions());
                        jTextField3.setText(cardTester.getActualQuestionedCard().definition);
                        jTextField1.setText("");
                        jTextField2.setText("");

                        clearTabulars();
                    } else {
                        goToStatisticsFrameAndSaveData();
                    }
                }
                else if (cardTester.getActualQuestionedCard().term.equals(jTextField1.getText())) {
                    if (cardTester.isMoreCardToTest()) {
                        cardTester.moveToNextCardToQuestion();
                        jLabel2.setText(cardTester.numberOfCardsQuestioned() + "\\" + cardTester.getNumberOfQuestions());
                        jTextField3.setText(cardTester.getActualQuestionedCard().definition);
                        jTextField1.setText("");
                        jTextField2.setText("");

                        clearTabulars();
                    } else {
                        goToStatisticsFrameAndSaveData();
                    }
                }
            }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void showAcceptableCards() {
        Set<Integer> acceptableCardIndexes
                = cardTester.getAcceptableCardIndexes(cardTester.getActualQuestionedCard().definition);

        for (int index : acceptableCardIndexes) {
            acceptableAnswersTableModel.addRow(new Object[]{
                dictionaryDataContainer.cardContainer.getCardByIndex(index).term,
                dictionaryDataContainer.cardContainer.getCardByIndex(index).definition
            });
        }
    }
    
    private void showExampleSentences() {
        List<Integer> exampleSentenceIndexes = new ArrayList<>();
        for (int i=0; i<cardTester.getActualQuestionedCard().exampleSentences.size(); i++) {
            exampleSentenceIndexes.add(i);
        }
        Collections.shuffle(exampleSentenceIndexes);
        
        for (int i : exampleSentenceIndexes) {
            exampleSentencesTableModel.addRow(new Object[]{
                cardTester.getActualQuestionedCard().exampleSentences.get(i)
            });
        }
    }

    private void clearTabulars() {
        for (int i = acceptableAnswersTableModel.getRowCount() - 1; 0 <= i; i--) {
            acceptableAnswersTableModel.removeRow(i);
        }
        
        for (int i = exampleSentencesTableModel.getRowCount() - 1; 0 <= i; i--) {
            exampleSentencesTableModel.removeRow(i);
        }
    }

    private void goToStatisticsFrameAndSaveData() {
        finishTime = new Date().getTime();
        
        CardTesterStatisticsDialog dialog = new CardTesterStatisticsDialog(new javax.swing.JFrame(), true);
        dialog.allCard = dictionaryDataContainer.cardContainer;
        dialog.testAnswers = cardTester.getUserAnswers();
        dialog.oldAnswers = dictionaryDataContainer.answerDataContainer;
        dialog.finishTime = finishTime;
        dialog.startTime = startTime;
        dialog.dialogAnswer = new DialogAnswer();

        dialog.setCardTestStatisticsDataToFrame();
        
        DictionaryDataModificator dictionaryDataModificator = new DictionaryDataModificator();
        dictionaryDataModificator.setData(dictionaryDataContainer);

        dictionaryDataModificator.appendToStudiedLanguageCardData(cardTester.getUserAnswers());
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        if (dialog.dialogAnswer.boolAnswer) {
            initialise();
        } else {
            dispose();
        }
    }

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyPressed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void inspectCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inspectCardButtonActionPerformed
        CardInspectorDialog dialog 
                = new CardInspectorDialog(new javax.swing.JFrame(), true);
        dialog.cardToInspect = cardTester.getActualQuestionedCard();
        dialog.dictionaryDataContainer = dictionaryDataContainer;
        DialogAnswer dialogAnswer = new DialogAnswer();
        dialog.dialogAnswer = dialogAnswer;
        dialog.unableDeleteCardButton();
        dialog.initialise();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        if (dialogAnswer.stringAnswer.equals("save_card")) {
            DictionaryDataModificator dictionaryDataModificator 
                    = new DictionaryDataModificator();
            dictionaryDataModificator.setData(dictionaryDataContainer);
            dictionaryDataModificator.saveAllData();
        }
        
        jTextField1.requestFocus();
    }//GEN-LAST:event_inspectCardButtonActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(CardTesterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardTesterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardTesterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardTesterDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CardTesterDialog dialog = new CardTesterDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton closeButton;
    private javax.swing.JButton inspectCardButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

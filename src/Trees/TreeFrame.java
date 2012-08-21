/*
 * Copyright 2011 Adam Nelson
 * 
 * This file is part of LindenTrees.
 * 
 * LindenTrees is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

/*
 * TreeFrame.java
 *
 * Created on Nov 27, 2011, 9:22:23 PM
 */
package Trees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.lwjgl.LWJGLException;

/**
 * A frame to load and set the iteration of a tree to be 
 * displayed with the TreeDisplay class
 * @author Adam
 */
public class TreeFrame extends javax.swing.JFrame {
    
    //File chooser to choose tree, bark and leaf files
    private final JFileChooser fc = new JFileChooser();
    //All of the files to create a tree
    private File treeFile;
    private File barkFile;
    private File leafFile;
    //Thread to run the display in
    private DisplayThread display;
    //Holds whether the display has been started already or not
    private boolean started = false;
    
    /** Creates new form TreeFrame */
    public TreeFrame() throws LWJGLException, FileNotFoundException {
        initComponents();
        display = new DisplayThread();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        treeFileButton = new javax.swing.JButton();
        treeLabel = new javax.swing.JLabel();
        CreateTreeButton = new javax.swing.JButton();
        barkFileButton = new java.awt.Button();
        barkLabel = new java.awt.Label();
        leafFileButton = new java.awt.Button();
        leafLabel = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        treeFileButton.setText("Choose Tree File");
        treeFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treeFileButtonActionPerformed(evt);
            }
        });

        treeLabel.setText(" ");

        CreateTreeButton.setText("Create Tree");
        CreateTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateTreeButtonActionPerformed(evt);
            }
        });

        barkFileButton.setLabel("Choose Bark Image (.png, .gif, .jpg)");
        barkFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barkFileButtonActionPerformed(evt);
            }
        });

        leafFileButton.setLabel("Choose Leaf Image (.png)");
        leafFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leafFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CreateTreeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(treeFileButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(leafFileButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(barkFileButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(treeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(leafLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(barkLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(treeFileButton)
                    .addComponent(treeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(barkFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leafFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leafLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CreateTreeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Select a file to render a tree from. File must be of type .tree,
    //which is just an extension I made (unless it already exists) to determine the
    //tree from, and prevents loading just any file.
    private void treeFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treeFileButtonActionPerformed
        // TODO add your handling code here:
        fc.showOpenDialog(this);
        treeFile = fc.getSelectedFile();
        if(treeFile != null){
            if(!treeFile.getName().endsWith(".tree")){
                JOptionPane.showMessageDialog(this, "Please select a .tree file.");
                treeFile = null;
            }
            else{
                treeLabel.setText(treeFile.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_treeFileButtonActionPerformed

    private void CreateTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateTreeButtonActionPerformed
        
        //If any one of the files have not been instantiated, 
        //make user instantiate all the files before starting the display
        if(treeFile == null || barkFile == null || leafFile == null){
            JOptionPane.showMessageDialog(null, "Please select all neccessary files.");
        }
        else{
            //Set the aspects of the tree
            display.treeDisplay.setIteration(0);
            display.treeDisplay.setBarkTexture(barkFile);
            display.treeDisplay.setLeafTexture(leafFile);
            display.treeDisplay.setTreeFile(treeFile);
            //If the display hasn't already been started, start the display
            try{
                if(!started){
                    display.treeDisplay.setTreeFile(treeFile);
                    display.start();
                    started = true;
                }
            } catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_CreateTreeButtonActionPerformed

    //Selects an image to texture the tree branches with. File can be a 
    //PNG, GIF or JPG
    private void barkFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barkFileButtonActionPerformed
        // TODO add your handling code here:
        fc.showOpenDialog(this);
        barkFile = fc.getSelectedFile();
        if(barkFile != null){
            if(!(barkFile.getName().toLowerCase().endsWith(".png") ||
                 barkFile.getName().toLowerCase().endsWith(".gif") ||
                 barkFile.getName().toLowerCase().endsWith(".jpg"))){

                JOptionPane.showMessageDialog(this, "Please select a .png, .gif or .jpg file.");
                barkFile = null;
            }
            else{
                barkLabel.setText(barkFile.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_barkFileButtonActionPerformed

    //Selects a image file to texture a leaf. The texture must be a PNG
    private void leafFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leafFileButtonActionPerformed
        // TODO add your handling code here:
        fc.showOpenDialog(this);
        leafFile = fc.getSelectedFile();
        if(leafFile != null){
            if(!(leafFile.getName().toLowerCase().endsWith(".png"))){
                JOptionPane.showMessageDialog(this, "Please select a .png file.");
                leafFile = null;
            }
            else{
                leafLabel.setText(leafFile.getAbsolutePath());
            }
        }
    }//GEN-LAST:event_leafFileButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TreeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TreeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TreeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TreeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new TreeFrame().setVisible(true);
                } catch (LWJGLException ex) {
                    Logger.getLogger(TreeFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TreeFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateTreeButton;
    private java.awt.Button barkFileButton;
    private java.awt.Label barkLabel;
    private java.awt.Button leafFileButton;
    private java.awt.Label leafLabel;
    private javax.swing.JButton treeFileButton;
    private javax.swing.JLabel treeLabel;
    // End of variables declaration//GEN-END:variables
}

//A thread to run the TreeDisplay in, so that the TreeFrame may be interactive
//while the display is running
class DisplayThread extends Thread {
    
    TreeDisplay treeDisplay;
    
    public DisplayThread(){
        treeDisplay = new TreeDisplay();
    }
    
    @Override
    public void run(){
        try {
            try {
                treeDisplay.start();
            } catch (IOException ex) {
                Logger.getLogger(DisplayThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (LWJGLException ex) {
            Logger.getLogger(DisplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

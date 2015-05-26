/*
 * ImageModifierView.java
 */

package imagemodifier;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 * The application's main frame.
 */
public class ImageModifierView extends FrameView {
    
    private ImageUtility imgUtility;
    private BufferedImage resizeImage;
    private BufferedImage borderImage;
    private int x1=0,y1=0,x2=0,y2=0;
    private Stack<Line> lines;
    private boolean isBorderClicked = false;
    private boolean isLineClicked = false;

    public ImageModifierView(SingleFrameApplication app) {
        super(app);

        initComponents();
        editorPanel.setLayout(new BorderLayout());
        imgUtility = new ImageUtility();
        lines = new Stack<Line>();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ImageModifierApp.getApplication().getMainFrame();
            aboutBox = new ImageModifierAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ImageModifierApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        editorPanel = new javax.swing.JPanel();
        browseButton = new javax.swing.JButton();
        menuPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        sizeComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        openFileDialogue = new javax.swing.JFileChooser();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(imagemodifier.ImageModifierApp.class).getContext().getResourceMap(ImageModifierView.class);
        editorPanel.setBackground(resourceMap.getColor("editorPanel.background")); // NOI18N
        editorPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(resourceMap.getColor("editorPanel.border.highlightColor"), resourceMap.getColor("editorPanel.border.shadowColor"))); // NOI18N
        editorPanel.setName("editorPanel"); // NOI18N

        javax.swing.GroupLayout editorPanelLayout = new javax.swing.GroupLayout(editorPanel);
        editorPanel.setLayout(editorPanelLayout);
        editorPanelLayout.setHorizontalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        editorPanelLayout.setVerticalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );

        browseButton.setFont(resourceMap.getFont("browseButton.font")); // NOI18N
        browseButton.setText(resourceMap.getString("browseButton.text")); // NOI18N
        browseButton.setName("browseButton"); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        menuPanel.setBackground(resourceMap.getColor("menuPanel.background")); // NOI18N
        menuPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(resourceMap.getColor("menuPanel.border.highlightColor"), resourceMap.getColor("menuPanel.border.shadowColor"))); // NOI18N
        menuPanel.setName("menuPanel"); // NOI18N

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100/72", "100/80", "100/100", "150/100", "150/150", "200/200" }));
        sizeComboBox.setName("sizeComboBox"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        saveButton.setFont(resourceMap.getFont("saveButton.font")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(editorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(browseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(imagemodifier.ImageModifierApp.class).getContext().getActionMap(ImageModifierView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 507, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        openFileDialogue.setName("openFileDialogue"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        // TODO add your handling code here:
        int retVal = openFileDialogue.showOpenDialog(this.mainPanel);
        
        if(retVal == JFileChooser.APPROVE_OPTION)
        {
            SetImageSize();
            File file = openFileDialogue.getSelectedFile();
            String filePath = file.toString();
            try{
                    editorPanel.removeAll();

                    BufferedImage originalImage = ImageIO.read(new File(filePath));
                    resizeImage = imgUtility.GetResizeImage(originalImage);  

                    JLabel label = new JLabel(new ImageIcon(resizeImage));
                    label.addMouseListener(new MouseListener() {

                        public void mousePressed(MouseEvent e) {
                            if(isLineClicked)
                            {
                                Component com = (Component)e.getSource();
                                //System.out.println(com.getHeight() + " "+ com.getWidth() + " "+ imgUtility.GetWidth() + " "+ imgUtility.GetHeight() + " " + e.getX() + " " + e.getY());
                                if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                                {
                                    x1 = e.getX();
                                    y1 = e.getY();
                                }
                            }
                        }

                        public void mouseReleased(MouseEvent e) {
                            if(isLineClicked)
                            {
                                Component com = (Component)e.getSource();
                                if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                                {
                                    x2 = e.getX();
                                    y2 = e.getY();
                                    Graphics2D g = (Graphics2D)com.getGraphics();
                                    g.setColor(Color.RED);
                                    g.setStroke(new BasicStroke(4));
                                    g.drawLine(x1, y1, x2, y2);
                                    imgUtility.DrawArrow(g, x1, y1, x2, y2);
                                    lines.push(new Line(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), x1, x2, y1, y2));
                                }
                                x1 = x2 = y1 = y2 = 0;
                            }
                            
                        }

                        public void mouseEntered(MouseEvent e) {
                        }

                        public void mouseExited(MouseEvent e) {
                        }

                        public void mouseMoved(MouseEvent e) {
                        }

                    public void mouseClicked(MouseEvent e) {
                    }
                    });
                    
                    editorPanel.add(label);
                    editorPanel.repaint();
                    editorPanel.revalidate();

            }catch(IOException e){
                    System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(isBorderClicked) isBorderClicked = false;
        else isBorderClicked = true;
        
        if(isBorderClicked)
        {
            borderImage = imgUtility.GetBorderImage(resizeImage);
            editorPanel.removeAll();
            JLabel label = new JLabel(new ImageIcon(borderImage));
            label.addMouseListener(new MouseListener() {

                public void mousePressed(MouseEvent e) {
                    if(isLineClicked)
                    {
                        Component com = (Component)e.getSource();
                        //System.out.println(com.getHeight() + " "+ com.getWidth() + " "+ imgUtility.GetWidth() + " "+ imgUtility.GetHeight() + " " + e.getX() + " " + e.getY());
                        if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                        {
                            x1 = e.getX();
                            y1 = e.getY();
                        }
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if(isLineClicked)
                    {
                        Component com = (Component)e.getSource();
                        if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                        {
                            x2 = e.getX();
                            y2 = e.getY();
                            Graphics2D g = (Graphics2D)com.getGraphics();
                            g.setColor(Color.RED);
                            g.setStroke(new BasicStroke(4));
                            g.drawLine(x1, y1, x2, y2);
                            imgUtility.DrawArrow(g, x1, y1, x2, y2);
                            lines.push(new Line(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), x1, x2, y1, y2));
                        }
                        x1 = x2 = y1 = y2 = 0;
                    }
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mouseMoved(MouseEvent e) {
                }

            public void mouseClicked(MouseEvent e) {
            }
            });
            editorPanel.add(label);
            editorPanel.repaint();
            editorPanel.revalidate();
        }
        else
        {
            editorPanel.removeAll();
            JLabel label = new JLabel(new ImageIcon(resizeImage));
            label.addMouseListener(new MouseListener() {

                public void mousePressed(MouseEvent e) {
                    if(isLineClicked)
                    {
                        Component com = (Component)e.getSource();
                        //System.out.println(com.getHeight() + " "+ com.getWidth() + " "+ imgUtility.GetWidth() + " "+ imgUtility.GetHeight() + " " + e.getX() + " " + e.getY());
                        if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                        {
                            x1 = e.getX();
                            y1 = e.getY();
                        }
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if(isLineClicked)
                    {
                        Component com = (Component)e.getSource();
                        if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                        {
                            x2 = e.getX();
                            y2 = e.getY();
                            Graphics2D g = (Graphics2D)com.getGraphics();
                            g.setColor(Color.RED);
                            g.setStroke(new BasicStroke(4));
                            g.drawLine(x1, y1, x2, y2);
                            imgUtility.DrawArrow(g, x1, y1, x2, y2);
                            lines.push(new Line(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), x1, x2, y1, y2));
                        }
                        x1 = x2 = y1 = y2 = 0;
                    }

                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }

                public void mouseMoved(MouseEvent e) {
                }

            public void mouseClicked(MouseEvent e) {
            }
            });

            editorPanel.add(label);
            editorPanel.repaint();
            editorPanel.revalidate();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        int retVal = openFileDialogue.showSaveDialog(this.mainPanel);
        
        if(retVal == JFileChooser.APPROVE_OPTION)
        {
            File file = openFileDialogue.getSelectedFile();
            String filePath = file.toString();
            BufferedImage linedImage;
            if(isBorderClicked)
                linedImage = imgUtility.DrawLineOnImage(borderImage, lines);
            else
                linedImage = imgUtility.DrawLineOnImage(resizeImage, lines);
            try {
                ImageIO.write(linedImage, "png", new File(filePath));
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        lines.clear();
        editorPanel.removeAll();
        JLabel label = new JLabel(new ImageIcon(resizeImage));
        label.addMouseListener(new MouseListener() {

            public void mousePressed(MouseEvent e) {
                if(isLineClicked)
                {
                    Component com = (Component)e.getSource();
                    //System.out.println(com.getHeight() + " "+ com.getWidth() + " "+ imgUtility.GetWidth() + " "+ imgUtility.GetHeight() + " " + e.getX() + " " + e.getY());
                    if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                    {
                        x1 = e.getX();
                        y1 = e.getY();
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(isLineClicked)
                {
                    Component com = (Component)e.getSource();
                    if(imgUtility.ValidRegion(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), e.getX(), e.getY()))
                    {
                        x2 = e.getX();
                        y2 = e.getY();
                        Graphics2D g = (Graphics2D)com.getGraphics();
                        g.setColor(Color.RED);
                        g.setStroke(new BasicStroke(4));
                        g.drawLine(x1, y1, x2, y2);
                        imgUtility.DrawArrow(g, x1, y1, x2, y2);
                        lines.push(new Line(com.getWidth(), com.getHeight(), imgUtility.GetWidth(), imgUtility.GetHeight(), x1, x2, y1, y2));
                    }
                    x1 = x2 = y1 = y2 = 0;
                }

            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
            }

        public void mouseClicked(MouseEvent e) {
        }
        });

        editorPanel.add(label);
        editorPanel.repaint();
        editorPanel.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(isLineClicked) isLineClicked = false;
        else isLineClicked = true;
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel editorPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JFileChooser openFileDialogue;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox sizeComboBox;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    
    private void SetImageSize()
    {
        if(sizeComboBox.getSelectedIndex() == 0)
        {
            imgUtility.SetHeight(72);
            imgUtility.SetWidth(100);
        }
        else if(sizeComboBox.getSelectedIndex() == 1)
        {
            imgUtility.SetHeight(80);
            imgUtility.SetWidth(100);
        }
        else if(sizeComboBox.getSelectedIndex() == 2)
        {
            imgUtility.SetHeight(100);
            imgUtility.SetWidth(100);
        }
        else if(sizeComboBox.getSelectedIndex() == 3)
        {
            imgUtility.SetHeight(100);
            imgUtility.SetWidth(150);
        }
        else if(sizeComboBox.getSelectedIndex() == 4)
        {
            imgUtility.SetHeight(150);
            imgUtility.SetWidth(150);
        }
        else if(sizeComboBox.getSelectedIndex() == 5)
        {
            imgUtility.SetHeight(200);
            imgUtility.SetWidth(200);
        }
    }
    
    private void DrawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2)
    {
        if(x2 > x1 && y1 == y2)
        {
            g.setStroke(new BasicStroke(3));
            g.drawLine(x1, y1, x2 - 6, y2);
            
            g.setStroke(new BasicStroke(6));
            g.drawLine(x2 - 5, y2, x2 - 5, y2);
            g.setStroke(new BasicStroke(5));
            g.drawLine(x2 - 4, y2, x2 - 4, y2);
            g.setStroke(new BasicStroke(4));
            g.drawLine(x2 - 3, y2, x2 - 3, y2);
            g.setStroke(new BasicStroke(3));
            g.drawLine(x2 - 2, y2, x2 - 2, y2);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x2 - 1, y2, x2 - 1, y2);
            g.setStroke(new BasicStroke(1));
            g.drawLine(x2, y2, x2, y2);
        }
        else if(x2 < x1 && y1 == y2)
        {
            g.setStroke(new BasicStroke(3));
            g.drawLine(x1, y1, x2 + 6, y2);
            
            g.setStroke(new BasicStroke(6));
            g.drawLine(x2 + 5, y2, x2 + 5, y2);
            g.setStroke(new BasicStroke(5));
            g.drawLine(x2 + 4, y2, x2 + 4, y2);
            g.setStroke(new BasicStroke(4));
            g.drawLine(x2 + 3, y2, x2 + 3, y2);
            g.setStroke(new BasicStroke(3));
            g.drawLine(x2 + 2, y2, x2 + 2, y2);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x2 + 1, y2, x2 + 1, y2);
            g.setStroke(new BasicStroke(1));
            g.drawLine(x2, y2, x2, y2);
        }
        else if(x2 < x1 && y1 < y2)
        {
            g.setStroke(new BasicStroke(3));
            g.drawLine(x1, y1, x2 - 6, y2 + 6);
            
            g.setStroke(new BasicStroke(6));
            g.drawLine(x2 - 5, y2, x2 + 5, y2);
            g.setStroke(new BasicStroke(5));
            g.drawLine(x2 -+ 4, y2, x2 + 4, y2);
            g.setStroke(new BasicStroke(4));
            g.drawLine(x2 - 3, y2, x2 + 3, y2);
            g.setStroke(new BasicStroke(3));
            g.drawLine(x2 - 2, y2, x2 + 2, y2);
            g.setStroke(new BasicStroke(2));
            g.drawLine(x2 - 1, y2, x2 + 1, y2);
            g.setStroke(new BasicStroke(1));
            g.drawLine(x2, y2, x2, y2);
        }
    }
}

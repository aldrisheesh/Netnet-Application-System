package com.group_9.project;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Template extends JFrame {

    public Template() {
        BaseFrameSetup.applyAppIcon(this);
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Template().setVisible(true));
    }
}

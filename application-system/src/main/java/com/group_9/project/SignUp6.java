package com.group_9.project;
import com.group_9.project.database.ApplicationService;
import com.group_9.project.database.DatabaseConnection;
import com.group_9.project.session.UserApplicationData;
import com.group_9.project.utils.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SignUp6 extends JFrame {
    private static final int RADIUS = 15;

    // Sets up the main frame
    public SignUp6() {
        BackgroundPanel background = BaseFrameSetup.setupCompleteFrame(this, 1);

        // Main content container
        JPanel container = createContentPanel();
        background.add(container);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setOpaque(false);
        innerContent.setBounds(40, 40, 890, 615);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        Color titleColor = Color.decode("#2B0243");
        Color txtColor = Color.decode("#302E2E");

        JLabel title = new JLabel("SERVICE APPLICATION", SwingConstants.CENTER);
        title.setFont(FontUtil.getOutfitBoldFont(26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(titleColor);
        innerContent.add(title);

        // adds spacing
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // step tracker panel using the new separate class
        JPanel stepWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepWrapper.setOpaque(false);
        stepWrapper.add(CreateStepTracker.createStepTracker(3)); // Active index is 3 for fourth step
        innerContent.add(stepWrapper);
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add confirmation icon right after step tracker
        ImageIcon confirmIconRaw = new ImageIcon(getClass().getClassLoader().getResource("images/confirmation-icn.png"));
        Image confirmImg = confirmIconRaw.getImage().getScaledInstance(61, 61, Image.SCALE_SMOOTH);
        JLabel confirmLabel = new JLabel(new ImageIcon(confirmImg));
        confirmLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        innerContent.add(confirmLabel);

        // Add some spacing after the icon
        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel msgSuccess = new JLabel("APPLICATION SUBMITTED SUCCESSFULLY!", SwingConstants.CENTER);
        msgSuccess.setFont(FontUtil.getOutfitFont(16f));
        msgSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        msgSuccess.setForeground(txtColor);
        innerContent.add(msgSuccess);

        innerContent.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel tyMsg = new JLabel("Thank you for choosing NETNET!", SwingConstants.CENTER);
        tyMsg.setFont(FontUtil.getInterFont(15f));
        tyMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        tyMsg.setForeground(txtColor);
        innerContent.add(tyMsg);

        JLabel msg = new JLabel("Your application has been submitted and is now being processed.", SwingConstants.CENTER);
        msg.setFont(FontUtil.getInterFont(15f));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setForeground(txtColor);
        innerContent.add(msg);

        innerContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel trackTxt = new JLabel("Track Your Application Status", SwingConstants.CENTER);
        trackTxt.setFont(FontUtil.getOutfitFont(15f));
        trackTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackTxt.setForeground(txtColor);
        innerContent.add(trackTxt);

        // Add spacing after trackTxt
        innerContent.add(Box.createRigidArea(new Dimension(0, 5)));

        // Create rounded panel container with RoundedBorder
        JPanel roundedPanel = new JPanel();
        roundedPanel.setOpaque(true);
        roundedPanel.setBackground(Color.WHITE);
        roundedPanel.setBorder(new RoundedComponents.RoundedBorder(RADIUS)); 
        roundedPanel.setPreferredSize(new Dimension(375, 118));
        roundedPanel.setMaximumSize(new Dimension(375, 118));
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));

        // Add some padding at the top
        roundedPanel.add(Box.createVerticalGlue());

        // Get application data - try from database first, then fallback
        String appNumber = null;
        String appDate = null;
        String username = UserApplicationData.get("Username");
        
        System.out.println("SignUp6: Attempting to retrieve application data for username: " + username);
        
        if (username != null && !username.trim().isEmpty()) {
            // Try to get from database with retry mechanism
            appNumber = getApplicationNumberWithRetry(username, 3);
            if (appNumber != null) {
                appDate = getLatestApplicationDate(username);
                System.out.println("SignUp6: Retrieved from database - Application Number: " + appNumber + ", Date: " + appDate);
            }
        }

        // If we couldn't get from database, use fallback
        if (appNumber == null || appNumber.trim().isEmpty()) {
            appNumber = generateFallbackAppNumber();
            appDate = getCurrentFormattedDate();
            System.out.println("SignUp6: Using fallback - Application Number: " + appNumber + ", Date: " + appDate);
        }
        
        // Display the application number
        JLabel appNum = new JLabel("Application No.  " + appNumber, SwingConstants.CENTER);
        appNum.setFont(FontUtil.getOutfitFont(15f));
        appNum.setForeground(txtColor);
        appNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(appNum);

        roundedPanel.add(Box.createRigidArea(new Dimension(0, 9)));

        // Display the submission date
        JLabel date = new JLabel("Date Submitted:  " + appDate, SwingConstants.CENTER);
        date.setFont(FontUtil.getOutfitFont(15f));
        date.setForeground(txtColor);
        date.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundedPanel.add(date);

        // Add padding at the bottom
        roundedPanel.add(Box.createVerticalGlue());

        // Center the rounded panel
        JPanel roundedPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        roundedPanelWrapper.setOpaque(false);
        roundedPanelWrapper.add(roundedPanel);
        innerContent.add(roundedPanelWrapper);

        // Add flexible space to push buttons to bottom
        innerContent.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setMaximumSize(new Dimension(826, 50));

        RoundedComponents.RoundedButton doneButton = new RoundedComponents.RoundedButton("DONE", 25);
        doneButton.setPreferredSize(new Dimension(148, 41));
        doneButton.setBackground(Color.decode("#2A0243"));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(FontUtil.getOutfitBoldFont(16f));
        doneButton.setBorderColor(Color.decode("#2A0243"));

        buttonPanel.add(doneButton);
        innerContent.add(buttonPanel);

        container.add(innerContent);
        setVisible(true);

        // DONE button action
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrackingPage().setVisible(true);
                dispose();
            }
        });
    }

    // Enhanced method to get application number with retry mechanism
    private String getApplicationNumberWithRetry(String username, int maxRetries) {
        String appNumber = null;
        int attempts = 0;
        
        while (appNumber == null && attempts < maxRetries) {
            appNumber = getLatestApplicationNumber(username);
            
            if (appNumber == null && attempts < maxRetries - 1) {
                try {
                    Thread.sleep(500); // Wait 500ms before retry
                    attempts++;
                    System.out.println("SignUp6: Retry attempt " + (attempts + 1) + " for getting application number");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                break;
            }
        }
        
        return appNumber;
    }

    // Enhanced method to get the latest application number for a customer
    private String getLatestApplicationNumber(String username) {
        Connection conn = null;
        System.out.println("SignUp6: Searching for application number for username: '" + username + "'");
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                System.err.println("SignUp6: Cannot get database connection");
                return null;
            }
            System.out.println("SignUp6: Database connection established");

            // First, let's check if the customer exists and get customer_ID
            String checkCustomerSql = "SELECT customer_ID, username FROM tbl_customer WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkCustomerSql)) {
                checkStmt.setString(1, username);
                ResultSet checkRs = checkStmt.executeQuery();
                
                if (checkRs.next()) {
                    String customerId = checkRs.getString("customer_ID");
                    System.out.println("SignUp6: Found customer ID: " + customerId + " for username: " + checkRs.getString("username"));
                    
                    // Now get the latest application using customer_ID (not numeric customer_ID)
                    String sql = "SELECT a.application_no, a.application_date FROM tbl_application a " +
                                "WHERE a.customer_ID = ? " +
                                "ORDER BY a.application_date DESC LIMIT 1";
                    
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, customerId); // Use string customer_ID
                        ResultSet rs = stmt.executeQuery();
                        
                        if (rs.next()) {
                            String appNo = rs.getString("application_no");
                            System.out.println("SignUp6: Found application number: " + appNo);
                            return appNo;
                        } else {
                            System.err.println("SignUp6: No applications found for customer ID: " + customerId);
                        }
                    }
                } else {
                    System.err.println("SignUp6: No customer found with username: '" + username + "'");
                    
                    // Let's see what usernames actually exist
                    String listUsersSql = "SELECT username FROM tbl_customer ORDER BY customer_ID DESC LIMIT 5";
                    try (PreparedStatement listStmt = conn.prepareStatement(listUsersSql)) {
                        ResultSet listRs = listStmt.executeQuery();
                        System.out.println("SignUp6: Recent usernames in database:");
                        while (listRs.next()) {
                            System.out.println("SignUp6: - '" + listRs.getString("username") + "'");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SignUp6: SQL Error getting latest application number: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("SignUp6: Error closing connection: " + e.getMessage());
                }
            }
        }
        return null;
    }

    // New method to get the actual application date from database
    private String getLatestApplicationDate(String username) {
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return getCurrentFormattedDate(); // Return current date as fallback
            }

            // First get customer_ID, then get application date
            String getCustomerSql = "SELECT customer_ID FROM tbl_customer WHERE username = ?";
            try (PreparedStatement customerStmt = conn.prepareStatement(getCustomerSql)) {
                customerStmt.setString(1, username);
                ResultSet customerRs = customerStmt.executeQuery();
                
                if (customerRs.next()) {
                    String customerId = customerRs.getString("customer_ID");
                    
                    String sql = "SELECT a.application_date FROM tbl_application a " +
                                "WHERE a.customer_ID = ? " +
                                "ORDER BY a.application_date DESC LIMIT 1";
                    
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, customerId);
                        ResultSet rs = stmt.executeQuery();
                        
                        if (rs.next()) {
                            Timestamp appDate = rs.getTimestamp("application_date");
                            if (appDate != null) {
                                LocalDateTime dateTime = appDate.toLocalDateTime();
                                return dateTime.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SignUp6: Error getting latest application date: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("SignUp6: Error closing connection: " + e.getMessage());
                }
            }
        }
        return getCurrentFormattedDate(); // Return current date as fallback
    }

    // Generate a fallback application number
    private String generateFallbackAppNumber() {
        // Generate a simple application number based on timestamp
        long timestamp = System.currentTimeMillis();
        return "APP" + String.valueOf(timestamp).substring(7); // Last 6 digits
    }

    // Helper method to get current formatted date
    private String getCurrentFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    // Method to display error message in the panel
    private void displayErrorMessage(JPanel panel, Color textColor, String message) {
        JLabel errorLabel = new JLabel(message, SwingConstants.CENTER);
        errorLabel.setFont(FontUtil.getOutfitFont(15f));
        errorLabel.setForeground(textColor);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(errorLabel);
        
        panel.add(Box.createRigidArea(new Dimension(0, 9)));
        
        JLabel dateLabel = new JLabel("Date Submitted:  N/A", SwingConstants.CENTER);
        dateLabel.setFont(FontUtil.getOutfitFont(15f));
        dateLabel.setForeground(textColor);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dateLabel);
    }

    // Custom content panel with rounded corners and shadow - now also using RoundedBorder
    private JPanel createContentPanel() {
        JPanel content = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow first (offset to bottom-right)
                int shadowOffset = 4;
                g2.setColor(new Color(0, 0, 0, 20)); 
                g2.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                
                // Draw main panel
                g2.setColor(new Color(255, 241, 255));
                g2.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 25, 25);
                g2.setColor(new Color(220, 200, 230));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - shadowOffset - 1, getHeight() - shadowOffset - 1, 25, 25);
                g2.dispose();
            }
        };
        content.setBounds(235, 165, 970, 695);
        content.setOpaque(false);
        return content;
    }

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUp6::new);
    }
}
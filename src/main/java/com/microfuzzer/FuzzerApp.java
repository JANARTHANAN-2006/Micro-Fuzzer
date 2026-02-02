package com.microfuzzer;

import com.microfuzzer.service.FuzzingService;
import com.microfuzzer.model.FuzzingResult;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FuzzerApp extends JFrame {
    private JTextArea inputArea = new JTextArea();
    private JTextArea outputArea = new JTextArea();
    private JLabel statusLabel = new JLabel(" SYSTEM READY // STANDBY");
    private FuzzingService service = new FuzzingService();

    private final Color BG_DARK = new Color(30, 30, 30);      
    private final Color BG_SIDEBAR = new Color(37, 37, 38);   
    private final Color ACCENT_GREEN = new Color(80, 250, 123); 
    private final Color TEXT_WHITE = new Color(220, 220, 220);
    private final Color BORDER_COLOR = new Color(50, 50, 50);

    public FuzzerApp() {
        setTitle("MICRO-FUZZER v1.0 // JANARTHANAN_EDITION");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));
        sidebar.setLayout(new BorderLayout());
        sidebar.add(createSidebarContent(), BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridLayout(1, 2, 15, 0));
        mainContent.setBackground(BG_DARK);
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        mainContent.add(createEditorPanel("SOURCE_INPUT", inputArea));
        mainContent.add(createEditorPanel("FIXED_OUTPUT", outputArea));

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(BG_SIDEBAR);
        bottomBar.setPreferredSize(new Dimension(0, 65));
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        buttonPanel.setOpaque(false);

        JButton openBtn = createProButton("📂 OPEN FILE", new Color(60, 60, 60));
        JButton fixBtn = createProButton("⚡ RUN FUZZER", new Color(33, 115, 70));
        JButton saveBtn = createProButton("💾 EXPORT", new Color(0, 122, 204));

        buttonPanel.add(openBtn);
        buttonPanel.add(fixBtn);
        buttonPanel.add(saveBtn);

        statusLabel.setForeground(new Color(150, 150, 150));
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
        statusLabel.setBorder(new EmptyBorder(0, 15, 0, 0));
        
        bottomBar.add(statusLabel, BorderLayout.WEST);
        bottomBar.add(buttonPanel, BorderLayout.EAST);

        openBtn.addActionListener(e -> openFile());
        fixBtn.addActionListener(e -> runFuzzer());
        saveBtn.addActionListener(e -> saveFile());

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        setSize(1300, 850);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSidebarContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel greeting = new JLabel("ARCHITECT:");
        greeting.setForeground(new Color(150, 150, 150));
        greeting.setFont(new Font("Monospaced", Font.BOLD, 12));
        
        JLabel nameLabel = new JLabel("JANARTHANAN");
        nameLabel.setForeground(ACCENT_GREEN);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel bioLabel = new JLabel("<html>SRM KTR // B.Tech CSE<br>Specialization: Cybersecurity</html>");
        bioLabel.setForeground(TEXT_WHITE);
        bioLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JTextArea pitch = new JTextArea(
            "\n🚀 THE MISSION\n" +
            "A precision-engineered syntax guardian built to sanitize " +
            "broken source code with sub-millisecond precision.\n\n" +
            "🛡️ SECURITY ARCHITECTURE\n" +
            "Utilizes H2 Embedded SQL for local, secure logging. " +
            "Zero data leaks. Zero cloud reliance.\n\n" +
            "⚡ ADVANTAGE: RULES vs ML\n" +
            "• Deterministic Logic\n" +
            "• Zero Hallucinations\n" +
            "• Absolute Precision\n" +
            "• Full Offline Stealth"
        );
        pitch.setFont(new Font("Consolas", Font.PLAIN, 13));
        pitch.setForeground(new Color(180, 180, 180));
        pitch.setOpaque(false);
        pitch.setEditable(false);
        pitch.setLineWrap(true);
        pitch.setWrapStyleWord(true);
        pitch.setMaximumSize(new Dimension(260, 400));

        content.add(greeting);
        content.add(nameLabel);
        content.add(bioLabel);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(pitch);

        return content;
    }

    private JPanel createEditorPanel(String title, JTextArea area) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        
        JLabel l = new JLabel(" " + title);
        l.setForeground(ACCENT_GREEN);
        l.setFont(new Font("Monospaced", Font.BOLD, 12));
        l.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        area.setBackground(new Color(20, 20, 20));
        area.setForeground(TEXT_WHITE);
        area.setCaretColor(ACCENT_GREEN);
        area.setFont(new Font("Consolas", Font.PLAIN, 15));
        area.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane sp = new JScrollPane(area);
        sp.setBorder(new LineBorder(BORDER_COLOR, 1));
        
        p.add(l, BorderLayout.NORTH);
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    private JButton createProButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 40));
        
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(bg); }
        });
        return b;
    }

    private void openFile() {
        JFileChooser jfc = new JFileChooser();
        if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                inputArea.setText(Files.readString(jfc.getSelectedFile().toPath()));
                statusLabel.setText(" FILE LOADED: " + jfc.getSelectedFile().getName().toUpperCase());
            } catch (Exception ex) { statusLabel.setText(" STATUS: LOAD_FAILED"); }
        }
    }

    private void runFuzzer() {
        String input = inputArea.getText();
        if(input.length() < 10) {
            statusLabel.setText(" STATUS: INPUT_TOO_SHORT");
            return;
        }
        FuzzingResult res = service.fixSyntaxErrors(input);
        outputArea.setText(res.getFixedContent());
        statusLabel.setText(" STATUS: " + res.getErrorCount() + " ANOMALIES REPAIRED");
    }

    private void saveFile() {
        if(outputArea.getText().isEmpty()) return;
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File("REPAIRED_SOURCE.java"));
        if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(jfc.getSelectedFile())) {
                fw.write(outputArea.getText());
                statusLabel.setText(" STATUS: EXPORT_SUCCESSFUL");
            } catch (Exception ex) { statusLabel.setText(" STATUS: EXPORT_FAILED"); }
        }
    }
}
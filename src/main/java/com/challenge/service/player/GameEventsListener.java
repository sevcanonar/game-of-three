package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class GameEventsListener {

    PlayerType playerType;
    PrintWriter out;
    Scanner in;
    Socket clientSocket;
    PlayerEventQueue playerEventQueue;

    public GameEventsListener(PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
        this.playerEventQueue = playerEventQueue;
    }
}

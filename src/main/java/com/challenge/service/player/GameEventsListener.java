package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GameEventsListener {

    PlayerType playerType;
    PrintWriter out;
    BufferedReader in;
    Socket clientSocket;
    PlayerEventQueue playerEventQueue;

    public GameEventsListener(PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
        this.playerEventQueue = playerEventQueue;
    }
}

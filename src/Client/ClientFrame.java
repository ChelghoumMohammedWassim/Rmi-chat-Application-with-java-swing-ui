package Client;

import Repondeur.Repondeur;
import Server.Server_Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientFrame extends JFrame implements ActionListener {
    JButton button = new JButton();

    JButton clearButton = new JButton();
    JTextField input = new JTextField();
    ClientChatTable chatTable = new ClientChatTable();
    JTable table = new JTable(chatTable);

    JFrame connectionFrame;

    Repondeur repondeur;
    public ClientFrame(String reponIp,JFrame connectionFrame) throws MalformedURLException, NotBoundException, RemoteException, InterruptedException, AlreadyBoundException {

        Server_Session service= (Server_Session) Naming.lookup("rmi://"+reponIp+":5099/Server");
        repondeur= service.createSession();

        this.connectionFrame=connectionFrame;
        JPanel frame = new JPanel();
        frame.setSize(new Dimension(600, 500));
        setSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(61, 61, 61));
        setTitle("Questionner");

        JPanel buttomPanel = new JPanel();
        buttomPanel.setSize(new Dimension(600, 60));
        buttomPanel.setLayout(new BorderLayout());
        input.setPreferredSize(new Dimension(330, 35));
        Action enterbutton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question=input.getText().trim();
                if (!question.isEmpty()){
                    try {
                        sendQuestion(question);
                        input.setText("");
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 5));
        buttons.setMinimumSize(new Dimension(250, 30));
        input.addActionListener(enterbutton);
        clearButton.setText("Fin");
        clearButton.setSize(new Dimension(125, 30));
        clearButton.addActionListener(this);
        button.setText("Envoi");
        button.setSize(new Dimension(125, 30));
        button.addActionListener(this);
        buttons.add(button);
        buttons.add(clearButton);
        buttomPanel.add(input, BorderLayout.CENTER);
        buttomPanel.add(buttons, BorderLayout.EAST);
        setLayout(new BorderLayout());
        frame.setLayout(new BorderLayout());
        frame.add(buttomPanel, BorderLayout.SOUTH);
        frame.setBackground(new Color(73, 73, 74));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setShowGrid(false);
        table.setFont(new Font("Monospace", 0, 14));
        table.setSelectionBackground(new Color(0f, 0f, 0f, 0f));
        table.setTableHeader(null);
        table.setBackground(new Color(238, 238, 238, 255));
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        add(frame);
        pack();
        button.setBackground(new Color(150, 146, 146));
        clearButton.setBackground(new Color(150, 146, 146));
        input.requestFocusInWindow();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==button){
            String question=input.getText().trim();
            if (!question.isEmpty()){
                try {
                    sendQuestion(question);
                    input.setText("");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (e.getSource()==clearButton){
            input.setEnabled(false);
            clearButton.setEnabled(false);
            try {
                repondeur.exit();
                System.out.println(repondeur);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }

    }

    public void sendQuestion(String question) throws RemoteException {
        repondeur.poseQuestion(question);
        chatTable.addCommand("  Client:  "+question);
        chatTable.addCommand("  Repondeur:  "+repondeur.getReponse());
    }

}

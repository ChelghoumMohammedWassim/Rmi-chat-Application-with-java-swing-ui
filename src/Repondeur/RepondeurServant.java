package Repondeur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RepondeurServant extends UnicastRemoteObject implements Repondeur  {
    JButton button = new JButton();

    JTextField input = new JTextField();
    RepondeurChatTable chatTable = new RepondeurChatTable();
    JTable table = new JTable(chatTable);
    boolean answered=false;
    JFrame frame;
    public RepondeurServant() throws RemoteException {
        frame= new JFrame();
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(600, 500));
        frame.setSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setBackground(new Color(61, 61, 61));
        frame.setTitle("Service");

        JPanel buttomPanel = new JPanel();
        buttomPanel.setSize(new Dimension(600, 60));
        buttomPanel.setLayout(new BorderLayout());
        input.setPreferredSize(new Dimension(330, 35));
        Action enterbutton = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!input.getText().trim().isEmpty()){
                    answered=true;
                }
            }
        };
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 5, 5));
        buttons.setMinimumSize(new Dimension(250, 30));
        input.addActionListener(enterbutton);
        button.setText("Repond");
        button.setSize(new Dimension(125, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!input.getText().trim().isEmpty()){
                    answered=true;
                }
            }
        });
        buttons.add(button);
        buttomPanel.add(input, BorderLayout.CENTER);
        buttomPanel.add(buttons, BorderLayout.EAST);
        frame.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        panel.add(buttomPanel, BorderLayout.SOUTH);
        panel.setBackground(new Color(73, 73, 74));
        table.setRowHeight(table.getRowHeight() + 10);
        table.setShowGrid(false);
        table.setFont(new Font("Monospace", 0, 14));
        table.setSelectionBackground(new Color(0f, 0f, 0f, 0f));
        table.setTableHeader(null);
        table.setBackground(new Color(238, 238, 238, 255));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.pack();
        input.requestFocusInWindow();
        input.setEnabled(false);
        button.setEnabled(false);
        frame.setVisible(true);

    }

    @Override
    public void poseQuestion(String quetion) throws RemoteException {
        System.out.println(quetion);
        input.requestFocusInWindow();
        input.setEnabled(true);
        button.setEnabled(true);
        chatTable.addCommand("  Client: "+quetion);
    }

    @Override
    public String getReponse() throws RemoteException {
        while (!answered){
            System.out.print("waiting");
        }
        String reponse=input.getText().trim();
        chatTable.addCommand("  Repondeur:" +reponse);
        input.setEnabled(false);
        button.setEnabled(false);
        input.setText("");
        answered=false;
        return reponse;
    }

    @Override
    public void exit() throws RemoteException, MalformedURLException, NotBoundException {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Naming.unbind("rmi://localhost:5090/Repondeur");
    }
}

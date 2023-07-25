package Client;

import Repondeur.Repondeur;
import Server.Server_Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ConnectionFrame implements ActionListener {

    JFrame frame;
    JButton ipButton = new JButton();
    JButton localButton = new JButton();
    JTextField input = new JTextField();

    JLabel er = new JLabel();

    public ConnectionFrame() {
        frame = new JFrame();
        frame.setSize(new Dimension(350, 300));
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        frame.setTitle("Connection");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0, 10, 10));
        JLabel titre = new JLabel();
        titre.setText("IP :");
        titre.setSize(new Dimension(20, 20));
        titre.setFont(new Font("", 0, 16));

        input.setPreferredSize(new Dimension(250, 30));


        panel.add(titre);
        panel.add(input);

        ipButton.setBackground(new Color(150, 146, 146));
        localButton.setBackground(new Color(150, 146, 146));
        ipButton.setText("Connecter");
        localButton.setText("Local");
        ipButton.addActionListener(this);
        localButton.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 40, 0));
        buttons.setSize(new Dimension(500, 70));
        buttons.add(ipButton);
        buttons.add(localButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.add(er);
        frame.add(buttons);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==localButton){
            try {
                new ClientFrame("localhost",frame);
                new ClientFrame("localhost",frame);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (AlreadyBoundException ex) {
                throw new RuntimeException(ex);
            }
            frame.setVisible(false);
        }
        if(e.getSource()==ipButton){
            if (!input.getText().trim().isEmpty()){
                try {
                    new ClientFrame(input.getText().trim(),frame);
                    frame.setVisible(false);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (AlreadyBoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }

    }
}

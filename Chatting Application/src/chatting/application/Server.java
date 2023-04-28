package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    
    JButton send;
    JTextField text1;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server(){
        
        f.setLayout(null);
        
//        panel no 1:
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0, 450,70);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image i2 = i1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(10,20,25,25);
        p1.setLayout(null);
        p1.add(back);
        
        //for mouse click on back button
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        //for adding profile image
        ImageIcon profileImg = new ImageIcon(ClassLoader.getSystemResource("icons/person.png"));
        Image profileI1 = profileImg.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
        ImageIcon profileI2 = new ImageIcon(profileI1);
        JLabel profPic = new JLabel(profileI2);
        profPic.setBounds(45,15,35,35);
        p1.setLayout(null);
        p1.add(profPic);
        
        //for adding video option image
//        ImageIcon videoImg = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
//        Image videoI1 = videoImg.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
//        ImageIcon videoI2 = new ImageIcon(videoI1);
//        JLabel videoI3 = new JLabel(videoI2);
//        videoI3.setBounds(300,15,35,35);
//        p1.setLayout(null);
//        p1.add(videoI3);
        
        //for adding telephone option image
//        ImageIcon phoneImg = new ImageIcon(ClassLoader.getSystemResource("icons/phone.jpg"));
//        Image phoneI1 = phoneImg.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);
//        ImageIcon phoneI2 = new ImageIcon(phoneI1);
//        JLabel phoneI3 = new JLabel(phoneI2);
//        phoneI3.setBounds(345,15,35,35);
//        p1.setLayout(null);
//        p1.add(phoneI3);
        
        //for adding more option image
//        ImageIcon moreImg = new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
//        Image moreI1 = moreImg.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
//        ImageIcon moreI2 = new ImageIcon(moreI1);
//        JLabel moreI3 = new JLabel(moreI2);
//        moreI3.setBounds(380,15,35,35);
//        p1.setLayout(null);
//        p1.add(moreI3);
        
        //text using JLabel
        JLabel name = new JLabel("Server");
        name.setBounds(105,14,100,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("ARIAL", Font.BOLD, 18));
        p1.add(name);
        
        //status using JLabel
        JLabel statusImg = new JLabel("Active now");
        statusImg.setBounds(105,30,100,30);
        statusImg.setForeground(Color.WHITE);
        statusImg.setFont(new Font("SAN SARIF", Font.BOLD, 10));
        p1.add(statusImg);
        
        //PANEL NO 2
        a1 = new JPanel();
        a1.setBounds(5,75, 425,570);
        f.add(a1);
        
        //TEXT FIELD 
        text1 = new JTextField();
        text1.setBounds(5, 655, 310,40);
        text1.setFont(new Font("SAN SARIF", Font.PLAIN, 16));
        f.add(text1);
        
        //FOR SEND BUTTON
        send = new JButton("SEND");
        send.setBounds(320,655,105,40);
        send.setBackground(new Color(7,94,84));
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        f.add(send);
        
        f.setSize(435,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.setVisible(true);
        f.getContentPane().setBackground(Color.white);
    }
    
    public void actionPerformed(ActionEvent ae){
        try {
        String out = text1.getText();
        
        JPanel p2 = formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);   //it cant take string element as a first parameter
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical, BorderLayout.PAGE_START);
        
        text1.setText("");
        
        dout.writeUTF(out);
        
        f.repaint();
        f.invalidate();
        f.validate();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);        
        return panel;
    } 
    
    public static void main(String args[]){
        new Server();  //creating object of class Server
        
        try {
            ServerSocket skt = new ServerSocket(6001);
            System.out.println("PORT NO: 6001");
            while(true) {
                System.out.println("Waiting for Client...");
                Socket s = skt.accept();
                System.out.println("Connection Established");
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true){
                    String msg = din.readUTF(); //readUTF is a protocol
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.Date;

class MyServer extends JFrame implements ActionListener, Runnable {
    FileWriter fileWriter;
    JFrame frame;
    JButton SendButton, ExitButton;
    JTextArea ChatArea;
    JTextField Message;
    JScrollPane sp;
    JPanel panel;
    DataInputStream din;
    DataOutputStream dout;

    MyServer() {
        ServerGUI();
    }

    private void ServerConnection(){
        ServerSocket ss;
        try {
            ss = new ServerSocket(3333);
            Socket s = ss.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            String msgin = "";
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();
                ChatArea.setText(ChatArea.getText().trim() + "\n Client:" + msgin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ServerGUI() {
        frame = new JFrame("Server");
        frame.setLayout(new BorderLayout());
        SendButton = new JButton("Send");
        ExitButton = new JButton("Exit");
        ChatArea = new JTextArea();
        ChatArea.setRows(10);
        ChatArea.setColumns(50);
        ChatArea.setEditable(false);
        Message = new JTextField(50);
        sp = new JScrollPane(ChatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(sp, BorderLayout.CENTER);
        panel = new JPanel(new FlowLayout());
        panel.add(Message);
        panel.add(SendButton);
        panel.add(ExitButton);
        frame.add(panel, BorderLayout.SOUTH);
        SendButton.addActionListener(this);
        ExitButton.addActionListener(this);
        frame.setLocation(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == SendButton) {
            try {
                File file = new File("C:\\Users\\ttanu\\IdeaProjects\\ChatApplication\\src\\ChatHistory");
                fileWriter = new FileWriter("C:\\Users\\ttanu\\IdeaProjects\\ChatApplication\\src\\ChatHistory",true);
                String msgout;
                msgout = Message.getText().trim();
                dout.writeUTF(msgout);
                fileWriter.write("Server : " + msgout + " " + new Date(file.lastModified()).toString() + "\n" );
                Message.setText("");
                fileWriter.close();
            } catch (Exception ignored) { }
        } else if(actionEvent.getSource() == ExitButton){
            frame.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MyServer();
    }

    @Override
    public void run() {
        ServerConnection();
    }
}
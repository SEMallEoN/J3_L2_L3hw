package J3_L2_L3_hw_semenov.service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientService extends JFrame {
    JTextArea jta = new JTextArea();
    JTextField jtf = new JTextField();
    JButton jb = new JButton("Send");
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String myNick;

    public ClientService() {
        setTitle("SuperChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(50, 50, 400, 600);

        setLayout(new BorderLayout());

        JPanel chat = new JPanel();
        JPanel input = new JPanel();

        jta.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService.this.jb(jtf, jta);
            }
        });

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService.this.jb(jtf, jta);
            }
        });

        chat.setLayout(new BoxLayout(chat, BoxLayout.X_AXIS));
        chat.add(jtf);
        chat.add(jb);

        input.setLayout(new BoxLayout(input, BoxLayout.X_AXIS));
        input.add(jsp);

        getContentPane().add(chat, BorderLayout.SOUTH);
        getContentPane().add(input, BorderLayout.CENTER);

        setVisible(true);
    }

    private void jb(JTextField jtf, JTextArea jta) {
        String strField = jtf.getText();
        if (strField.startsWith("/auth")) {
            startChat();
            try {
                dos.writeUTF(jtf.getText());
                jtf.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                dos.writeUTF(jtf.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jta.append("\n");
        jtf.setText("");
    }

    public void startChat() {
        myNick = "";

        try {
            socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);


            Thread t1 = new Thread(() -> {
                try {
                    while (true) {
                        String strMsg = dis.readUTF();
                        jta.append(strMsg + "\n");
                        if (strMsg.startsWith("/authOk")) {
                            setAuthorized(true);
//                            loadHistory();
                        } else {
                            jta.append(strMsg + "\n");
                            break;
                        }
                    }

                    while (true) {
                        String strMsg = dis.readUTF();
                        if (strMsg.equals("/exit")) {
                            break;
                        }
                        jta.append(strMsg);
                        saveHistory();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    setAuthorized(false);
                    try {
                        socket.close();
                        myNick = "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveHistory() throws IOException {
        try {
            File history = new File("C:/Java/GeekBrains/Java3/J3_L2_L3hw/src/main/java/J3_L2_L3_hw_semenov/history.txt");
            PrintWriter fileWriter = new PrintWriter(new FileWriter(history, false));

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jta.getText());
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() throws IOException {
        int posHistory = 100;
        File history = new File("C:/Java/GeekBrains/Java3/J3_L2_L3hw/src/main/java/J3_L2_L3_hw_semenov/history.txt");
        List<String> historyList = new ArrayList<>();
        FileInputStream in = new FileInputStream(history);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            historyList.add(temp);
        }

        if (historyList.size() > posHistory) {
            for (int i = historyList.size() - posHistory; i <= (historyList.size() - 1); i++) {
                jta.append("(history) " + historyList.get(i) + "\n");
            }
        } else {
            for (int i = 0; i < posHistory; i++) {
                jta.append("(history) " + historyList.get(i) + "\n");
            }
        }
    }

    public void setAuthorized(boolean b) {


    }
}

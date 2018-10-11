
// Created by Edgar Daniel Rojas Vazquez
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
public class DESInterface extends JFrame implements  ActionListener{
	private JTextArea  tfMessage, tfResult;
    private JButton bEncrypt, bDecrypt;
    private JPanel panel1, panel2, panel3;
    private DESObject des = new DESObject();


	public DESInterface(){
		 super("DES Encryption");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Create objects used in GUI
        tfMessage = new JTextArea();
        bEncrypt  = new JButton("Cipher");
        bDecrypt = new JButton("Decipher");
        tfResult   = new JTextArea();

        // Add actionlisteners to buttons
        bEncrypt.addActionListener(this);
        bDecrypt.addActionListener(this);

        // Create panels to format layout
        panel1     = new JPanel();
        panel2     = new JPanel();
        panel3		= new JPanel();

        // Give panels layout
        panel1.setLayout(new GridLayout(2,2,20,20));
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new GridLayout(2,1));

        // 2. Add objects to panel 1
        panel1.add(new JLabel("Message to cipher: "));
        panel1.add(tfMessage);
        panel1.add(new JLabel("Result: "));
        panel1.add(tfResult);
        panel2.add(bEncrypt);

        // 3. Add panels to parent panel 3
        panel3.add(panel1);
        panel3.add(panel2);
        add(panel3);
        setSize(250,250);
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
    {
        // Process the button press
        if(e.getSource()== bEncrypt)
        {
            // Get text and encrypt. In case textbox is empty don't do anything
            String message = tfMessage.getText();
            if(!message.equals(""))
                tfResult.setText(des.encrypt(message));
        }

        if(e.getSource()== bDecrypt)
        {
        	//To be created
        }

    }

	public static void main(String args[]){;
		new DESInterface();
	}


}

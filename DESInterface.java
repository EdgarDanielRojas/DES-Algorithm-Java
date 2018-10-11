

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
public class DESInterface extends JFrame implements  ActionListener{
	private JTextArea tfEncriptar, tfDesencriptar;
	private JTextField tfNumber;
    private JButton bEncriptar, bDesencriptar;
    private JPanel panel1, panel2, panel3;
    private DESObject des = new DESObject();


	public DESInterface(){
		 super("DES Encryption");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 1. Crear objetos
        tfEncriptar = new JTextArea();
        bEncriptar  = new JButton("Cipher");
        bDesencriptar = new JButton("Decipher");

        tfDesencriptar   = new JTextArea();

        tfNumber = new JTextField();
        // Adicionar deteccion de eventos a los botones
        bEncriptar.addActionListener(this);
        bDesencriptar.addActionListener(this);


        //taDatos    = new JTextArea(10,30);
        panel1     = new JPanel();
        panel2     = new JPanel();
        panel3		= new JPanel();
        // 2. Adicionar los objetos al panel1
        panel1.setLayout(new GridLayout(2,2,20,20));
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new GridLayout(2,1));

        panel1.add(new JLabel("Message to cipher/decipher: "));
        panel1.add(tfEncriptar);

        //panel1.add(new JLabel("      "));
        panel1.add(new JLabel("Result: "));
        //panel1.add(new JLabel("      "));
        panel1.add(tfDesencriptar);

        panel2.add(bEncriptar);
        //panel2.add(bDesencriptar);

        panel3.add(panel1);
        //panel2.add(new JScrollPane(taDatos));

        // 3. Adicionar panel2 al JFrame
        panel3.add(panel2);
        add(panel3);
        setSize(250,250);
        setVisible(true);



	}
	public void actionPerformed(ActionEvent e)
    {

        if(e.getSource()== bEncriptar)
        {
            String message = tfEncriptar.getText();
            if(!message.equals(""))
                tfDesencriptar.setText(des.encrypt(message));
        }

        if(e.getSource()== bDesencriptar)
        {
        	
        }

    }

	public static void main(String args[]){;
		new DESInterface();
	}


}

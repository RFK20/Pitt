import javax.swing.*;
import java.awt.event.*;

/*rye
 * 11/13/19
 * calculator jam
 */

public class Calculator implements ActionListener{
	
	private JFrame calc;//frame
	private JTextField s;//screen
	private JButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,
	btn9,btn0,btndiv,btnmul,btnsub,btnadd,btndec,btneq,btnperc,btnclr,btnlftparen,
	btnrgtparen;//buttons

	//calculator note: only number buttons work
	Calculator()
	{
		calc=new JFrame("Calculator");
		
		s=new JTextField();
		
		//sets layout to null and and frame size
		calc.setLayout(null);
		calc.setVisible(true);
		calc.setSize(350,500);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calc.setResizable(false);
		
        //assigns buttons
		btn1=new JButton("1");
		btn2=new JButton("2");
		btn3=new JButton("3");
		btn4=new JButton("4");
		btn5=new JButton("5");
		btn6=new JButton("6");
		btn7=new JButton("7");
		btn8=new JButton("8");
		btn9=new JButton("9");
		btn0=new JButton("0");
		btndiv=new JButton("/");
		btnmul=new JButton("*");
		btnsub=new JButton("-");
		btnadd=new JButton("+");
		btndec=new JButton(".");
		btneq=new JButton("=");
		btnperc=new JButton("%");
		btnclr=new JButton("AC");
		btnlftparen=new JButton("(");
		btnrgtparen=new JButton(")");

		s.setBounds(30,40,280,50);//screen boundaries
		/* Button boundaries 
		 * below
		 */
		btn7.setBounds(40,100,50,50);
		btn8.setBounds(110,100,50,50);
		btn9.setBounds(180,100,50,50);
		btndiv.setBounds(250,100,50,50);

		btn4.setBounds(40,170,50,50);
		btn5.setBounds(110,170,50,50);
		btn6.setBounds(180,170,50,50);
		btnmul.setBounds(250,170,50,50);

		btn1.setBounds(40,240,50,50);
		btn2.setBounds(110,240,50,50);
		btn3.setBounds(180,240,50,50);
		btnsub.setBounds(250,240,50,50);

		btndec.setBounds(40,310,50,50);
		btn0.setBounds(110,310,50,50);
		btneq.setBounds(180,310,50,50);
		btnadd.setBounds(250,310,50,50);

		btnperc.setBounds(40,380,50,50);
		btnclr.setBounds(110,380,50,50);
		btnlftparen.setBounds(180,380,50,50);
		btnrgtparen.setBounds(250,380,50,50);

		calc.add(s);
		calc.add(btn7);
		calc.add(btn8);
		calc.add(btn9);
		calc.add(btndiv);
		calc.add(btn4);
		calc.add(btn5);
		calc.add(btn6);
		calc.add(btnmul);
		calc.add(btn1);
		calc.add(btn2);
		calc.add(btn3);
		calc.add(btnsub);
		calc.add(btndec);
		calc.add(btn0);
		calc.add(btneq);
		calc.add(btnadd);
		calc.add(btnperc);
		calc.add(btnclr);
		calc.add(btnlftparen);
		calc.add(btnrgtparen);

		
         //sets text field for buttons
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		btn0.addActionListener(this);
		btnadd.addActionListener(this);
		btndiv.addActionListener(this);
		btnmul.addActionListener(this);
		btnsub.addActionListener(this);
		btndec.addActionListener(this);
		btneq.addActionListener(this);
		btnperc.addActionListener(this);
		btnclr.addActionListener(this);
		btnlftparen.addActionListener(this);
		btnrgtparen.addActionListener(this);
	}
    // sets action of buttons
	public void actionPerformed(ActionEvent e)

	{
		if(e.getSource()==btn1)
			s.setText(s.getText().concat("1"));

		if(e.getSource()==btn2)
			s.setText(s.getText().concat("2"));

		if(e.getSource()==btn3)
			s.setText(s.getText().concat("3"));

		if(e.getSource()==btn4)
			s.setText(s.getText().concat("4"));

		if(e.getSource()==btn5)
			s.setText(s.getText().concat("5"));

		if(e.getSource()==btn6)
			s.setText(s.getText().concat("6"));

		if(e.getSource()==btn7)
			s.setText(s.getText().concat("7"));

		if(e.getSource()==btn8)
			s.setText(s.getText().concat("8"));

		if(e.getSource()==btn9)
			s.setText(s.getText().concat("9"));

		if(e.getSource()==btn0)
			s.setText(s.getText().concat("0"));

		if(e.getSource()==btndec)
			s.setText(s.getText().concat("."));

	}
	public static void main(String...str)
	{
		
		//method!
		new Calculator();
	}
}
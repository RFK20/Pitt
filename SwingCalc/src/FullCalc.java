import javax.swing.*;
import java.awt.event.*;

public class FullCalc implements ActionListener{
	JFrame calc;
	JTextField s;
	JButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,
	btn9,btn0,btndiv,btnmul,btnsub,btnadd,btndec,btneq,btnperc,btnclr;

	static double a=0,b=0,result=0;
	static int operator=0;

	FullCalc()
	{
		calc=new JFrame("Calculator");
		
		s=new JTextField();
		
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

		s.setBounds(30,40,280,30);
		btn7.setBounds(40,100,50,40);
		btn8.setBounds(110,100,50,40);
		btn9.setBounds(180,100,50,40);
		btndiv.setBounds(250,100,50,40);

		btn4.setBounds(40,170,50,40);
		btn5.setBounds(110,170,50,40);
		btn6.setBounds(180,170,50,40);
		btnmul.setBounds(250,170,50,40);

		btn1.setBounds(40,240,50,40);
		btn2.setBounds(110,240,50,40);
		btn3.setBounds(180,240,50,40);
		btnsub.setBounds(250,240,50,40);

		btndec.setBounds(40,310,50,40);
		btn0.setBounds(110,310,50,40);
		btneq.setBounds(180,310,50,40);
		btnadd.setBounds(250,310,50,40);

		btnperc.setBounds(60,380,100,40);
		btnclr.setBounds(180,380,100,40);

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

		calc.setLayout(null);
		calc.setVisible(true);
		calc.setSize(350,500);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calc.setResizable(false);

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
	}

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

		if(e.getSource()==btnadd)
		{
			a=Double.parseDouble(s.getText());
			operator=1;
			s.setText("");
		} 

		if(e.getSource()==btnsub)
		{
			a=Double.parseDouble(s.getText());
			operator=2;
			s.setText("");
		}

		if(e.getSource()==btnmul)
		{
			a=Double.parseDouble(s.getText());
			operator=3;
			s.setText("");
		}

		if(e.getSource()==btndiv)
		{
			a=Double.parseDouble(s.getText());
			operator=4;
			s.setText("");
		}

		if(e.getSource()==btnperc)
		{
			a=Double.parseDouble(s.getText());
			operator=5;
			s.setText("");
		} 

		if(e.getSource()==btneq)
		{
			b=Double.parseDouble(s.getText());

			switch(operator)
			{
			case 1: result=a+b;
			break;

			case 2: result=a-b;
			break;

			case 3: result=a*b;
			break;

			case 4: result=a/b;
			break;
			
			case 5: result = a%b;
			break;

			default: result=0;
			}

			s.setText(""+result);
		}

		if(e.getSource()==btnclr)
			s.setText("");

		if(e.getSource()==btnperc)
		{
			String str=s.getText();
			s.setText("");
			for(int i=0;i<str.length()-1;i++)
				s.setText(s.getText()+str.charAt(i));
		}		
	}
	public static void main(String...str)
	{
		new Calculator();
	}
}

//can
	
	
	

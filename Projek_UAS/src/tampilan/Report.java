package tampilan;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sistem.*;

public class Report extends JFrame 
{
	private Corel core;
	final public static int HEADER = 0, BODY = 1, FOOTER = 2;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextArea jl;

	public Report(Corel core, String[] data) 
	{
		this.core = core;

		setResizable(false);

		setSize(450, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.green);
		jl = new JTextArea(data[HEADER] + data[BODY] + data[FOOTER]);
		jl.setFont(new Font("Courier", Font.PLAIN, 11));
		jl.setBounds(0, 0, getWidth(), getHeight());
		jl.setEditable(false);

		container.add(jl);
	}

}

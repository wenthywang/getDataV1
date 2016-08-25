package cn.gb40;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test01 {
	
	JFrame		jf;
	JPanel		jp;
	JTextField	jtf1, jtf2, jtf3, jtf4;
	
	public Test01() {
		
		jf = new JFrame("Xmiles_GetData");
		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		jp = new JPanel();
		jtf1 = new JTextField();
		jtf2 = new JTextField(10);
		jtf3 = new JTextField("指定文本内容");
		jtf4 = new JTextField("指定内容+指定长度(只读状态)", 30);
		
		jtf3.setEnabled(false);
		jtf4.setFont(new Font("谐体", Font.BOLD | Font.ITALIC, 16));
		// 设置文本的水平对齐方式
		jtf4.setHorizontalAlignment(JTextField.CENTER);
		JButton jb=new JButton("获取数据");
		jb.setBounds(10, 10, 100, 20);
		jb.addActionListener(new ActionListener()
				{
		         public void actionPerformed(ActionEvent e)
		         {
		        	 String date=jtf2.getText();
		        	HttpClientTest.main(date);
		         }});
//		jp.add(jtf1);
		jp.add(jtf2);
//		jp.add(jtf3);
//		jp.add(jtf4);
		jp.add(jb);
		jp.setPreferredSize(new Dimension(300, 150));//关键代码,设置JPanel的大小  
		contentPane.add(jp);
		
		jf.pack();
		jf.setLocation(600, 400);
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
	   new Test01();

		
	}
}
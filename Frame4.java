package main_code;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import static main_code.Frame1.*;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Frame4 {

	private static JButton b1 = new JButton("Upravit data");
	private static JButton b2 = new JButton("Uložit data");
	private static JButton b3 = new JButton("Zavřít");
	private static JList<String> list = new JList<String>(model);
	public static JTextField text = new JTextField();
	public static JFrame f = new JFrame("Tvůrce kontaktů");
	public static JFrame ff = new JFrame("Editor dat");
	
	public static int wh = 400;
	private static int bw = 150;
	private static int bh = 80;
	private static int bg = 10;
	private static int bx = 500;
	private static int by = (wh - 4 * bh - 3 * bg) / 2 + 25;
	public static int x = Frame.NORMAL;
	public static String a = "";
	public static int list_index = list.getSelectedIndex()+1;

	
	
	


	public static void main_frame() {
		WindowListener listener = (WindowListener) new WindowAdapter() {
			public void windowActivated(WindowEvent evt) {
				f.setIconImage(icon_white);
			}
			public void windowDeactivated(WindowEvent evt) {
				f.setIconImage(icon);
			}
		};
		f.addWindowListener(listener);
		
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(list);
		f.setSize(ww, wh);
		f.getContentPane().setBackground(Color.BLACK);
		f.setBounds(wx, wy, ww, wh);
		f.setLayout(null);
		f.setState(x);
		list.setBounds(50, 50, ww - 300, 260);
		b1.setBounds(bx, by, bw, bh);

		b2.setBounds(bx, by + bh + bg, bw, bh);
		b3.setBounds(bx, by + 2 * bh + 2 * bg, bw, bh);
		
		//Button pro otevření vedlejšího okna a upravení dat
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list_index = list.getSelectedIndex();
				f.setState(Frame.ICONIFIED);
				if (list.getSelectedValue()!=null) {
				text.setText("");
				a = (String) list.getSelectedValue();
				System.out.println("Editoru dat bylo zasláno: " + "--" + a + "--"); //DEBUG zpráva
				sec_frame();
				}
				else {
					JOptionPane.showMessageDialog(ff, "Prosím vyberte řádek, který chcete změnit.");
					ff.dispose();
					f.setState(Frame.NORMAL);
				}
			}
		});
		
		//Button pro uložení dat do nového kontaktu
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String b = "";
					int i = 0;
					System.out.println(model.capacity()-1);
					while (i<model.capacity()-1) {
						if (b=="") {
							b = b + model.getElementAt(i);
						}
						else {
							b = b + "\n" + model.getElementAt(i);
						}
						i++;
					}
					System.out.println(b);

					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./src/kontakty"));
					fc.setFileHidingEnabled(true);
					disableNav(fc);
					int r = fc.showSaveDialog(f); //Zapnutí Save dialogu

					PolohaTXT = fc.getSelectedFile().toString();
					System.out.println("Byl vybrán soubor: " + PolohaTXT);

					if (r == APPROVE_OPTION) {
						System.out.println(b);

						try (FileWriter fwriter = new FileWriter(PolohaTXT)) {
							fwriter.write(b);
							fwriter.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(f, "Nebyl vybrán žádný soubor.");
					}

				

			}
		});
		
		//Button pro ukončení Framu
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				Frame1.f.setState(Frame.NORMAL);
			}
		});
		f.setVisible(true);
		return;
	}

	//Vedlejší okno pro úpravu vybraného řádku
	public static void sec_frame() {
		int ww = 465;
		int wh = 200;
		int bw = 100;
		int bh = 50;
		int bg = 10;
		int bx = (ww - 4 * bw - 3 * bg) / 2 + 100;
		int by = 100;

		JButton b2 = new JButton("Zaslat data");
		JButton b3 = new JButton("Zavřít");
		ff.add(b2);
		ff.add(b3);
		ff.add(text);
		text.setBounds(25, 25, 400, 50);
		text.setVisible(true);
		text.setText(a);
		
		WindowListener listener = (WindowListener) new WindowAdapter() {
			public void windowActivated(WindowEvent evt) {
				ff.setIconImage(icon_white);
			}
			public void windowDeactivated(WindowEvent evt) {
				ff.setIconImage(icon);
			}
			//public void windowOpened(WindowEvent evt) {
			//	Frame3.ff.setIconImage(icon_white);
			//}
		};
		ff.addWindowListener(listener);
		
		ff.setBounds(wx + ww / 4, wy + wh / 4, ww, wh);
		ff.setLayout(null);
		ff.getContentPane().setBackground(Color.BLACK);

		//Button pro odeslání dat z TextFieldu do modelu
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String x = text.getText();
				model.remove(list_index);
				model.add(list_index, x);
				Frame4.main(null);
				ff.dispose();
			}
		});
		
		//Button pro zavření vedlejšího okna
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ff.dispose();
				f.setState(Frame.NORMAL);
			}
		});

		b2.setBounds(bx, by, bw, bh);
		b3.setBounds(bx + bw + bg, by, bw, bh);

		ff.setVisible(true);
		//System.out.print(bx);
	}

	public static void main(String[] args) {
		StredOkna();
		main_frame();
	}
}

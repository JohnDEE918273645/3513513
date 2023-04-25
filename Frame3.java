package main_code;

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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Frame3 {

	private static JButton b1 = new JButton("Upravit data");
	private static JButton b2 = new JButton("Uložit data");
	private static JButton b3 = new JButton("Zavřít");
	public static JTextField text = new JTextField();
	public static JFrame f = new JFrame("Správce kontaktů");
	public static JFrame ff = new JFrame("Editor dat");
	
	public static int wh = 400;
	private static int bw = 150;
	private static int bh = 80;
	private static int bg = 10;
	private static int bx = 500;
	private static int by = (wh - 4 * bh - 3 * bg) / 2 + 25; //Výpočet pozice 1. buttonu
	public static String a = "";
	public static int list_index = list.getSelectedIndex()+1; //Pozice ředku v listu se kterým se počítá

	//Hlavní okno Správce kontaktů
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
		f.setState(Frame.NORMAL);
		
		list.setBounds(50, 50, ww - 300, 260);
		
		b1.setBounds(bx, by, bw, bh);
		b2.setBounds(bx, by + bh + bg, bw, bh);
		b3.setBounds(bx, by + 2 * bh + 2 * bg, bw, bh);
		
		//Button pro úpravu dat
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list_index = list.getSelectedIndex(); //Získání pozice vybraného řádku
				f.setState(Frame.ICONIFIED);  //Minimalizování hlavního Framu
				
				//Kontrola vybrání řádku
				if (list.getSelectedValue()!=null) {
				text.setText("");
				a = (String) list.getSelectedValue(); //Zaslaná data
				System.out.println("Editoru dat bylo zasláno: " + "--" + a + "--"); //DEBUG zpráva
				sec_frame(); //Inicializace vedlejšího okna
				}
				else {
					JOptionPane.showMessageDialog(ff, "Prosím vyberte řádek, který chcete změnit.");
					ff.dispose();
					f.setState(Frame.NORMAL);
				}
			}
		});
		
		//Button pro ukládání dat
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String b = ""; //Deklarace stringu pro výpočet
					int i = 0;
					System.out.println(model.capacity()-1); //DEBUG zpráva
					
					//Postupné vypsání řádků v modelu do stringu
					while (i<model.capacity()-1) {
						if (b=="") {
							b = b + model.getElementAt(i);
						}
						else {
							b = b + "\n" + model.getElementAt(i);
						}
						i++;
					}
					System.out.println(b); //DEBUG zpráva

					//Uložení stringu do předem vybraného kontaktu
					try (FileWriter fwriter = new FileWriter(PolohaTXT)) {
						fwriter.write(b);
						fwriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
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

	//Vedlejší okno pro přepis dat
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
			//	Frame3.f.setIconImage(icon_white);
			//}
		};
		ff.addWindowListener(listener);
		
		ff.setBounds(wx + ww / 4, wy + wh / 4, ww, wh);
		ff.setLayout(null);
		ff.getContentPane().setBackground(Color.BLACK);

		//Button pro zaslání dat z TextFieldu do modelu
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				String x = text.getText(); //Získání dat
				model.remove(list_index); //Odstranění vybraného řádku
				model.add(list_index, x); //Přidání Stringu X do pozice vybraného řádku
				Frame3.main(null); //Inicializace Hlavního okna
				ff.dispose(); //Vypnutí vedlejšího okna
				
			}
		});
		
		//Button pro ukončení Framu
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
		main_frame(); //Inicializace hlavního okna
	}
}

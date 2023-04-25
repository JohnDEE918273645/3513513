package main_code;

import static javax.swing.JFileChooser.APPROVE_OPTION;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Frame1{

	public static JFrame f = new JFrame("Hlavní menu");

	//Image import
	public static Image icon = Toolkit.getDefaultToolkit().getImage("./src/photos/icon.png");
	public static Image icon_white = Toolkit.getDefaultToolkit().getImage("./src/photos/icon_white.png");
	public static ImageIcon logo = new ImageIcon("./src/photos/logo.png");
	public static Image img = logo.getImage();

	public static int ww = 700; //Window width
	private static int wh = 500; //Window height
	public static int wx = 0; //X position of a window
	public static int wy = 0; //Y position of a window

	public static int bw = 150; //Button width
	public static int bh = 80; //Button height
	public static int bg = 10; //Button spacing
	public static int bx = (ww - 4 * bw - 3 * bg) / 2 - 10; //Button X position
	public static int by = 350; //Button Y position

	//Extraction of the JFrame dimensions
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int width = (int) screenSize.getWidth();
	public static int height = (int) screenSize.getHeight();
	
	public static DefaultListModel<String> model = new DefaultListModel<>();
	public static JList<String> list = new JList<>(model);

	public static String PolohaTXT = ""; //Pevná poloha vybraného souboru pro výpočty

	//Uzamknuťí FileChooseru
	public static void disableNav(Container c) {
		for (Component x : c.getComponents())
			if (x instanceof JComboBox)
				((JComboBox<?>) x).setEnabled(false);
			else if (x instanceof JButton) {
				String text = ((JButton) x).getText();
				if (text == null || text.isEmpty())
					((JButton) x).setEnabled(false);
			} else if (x instanceof Container)
				disableNav((Container) x);
	}
	
	//Centering of the JFrames
	public static void StredOkna() {
		wx = width / 2 - ww / 2;
		wy = height / 2 - wh / 2;
		return;
	}

	//Process termination
	public static void CloseAll() {
		System.exit(0);
	}


	public static void main(String[] args) {
		f.setState(Frame.NORMAL);
		StredOkna();
		
		//Responzivní změna icony na liště
		WindowListener listener = (WindowListener) new WindowAdapter() {
			public void windowActivated(WindowEvent evt) {
				f.setIconImage(icon_white);
			}

			public void windowDeactivated(WindowEvent evt) {
				f.setIconImage(icon);
			}
		};

		f.addWindowListener(listener);
		f.setBounds(wx, wy, ww, wh);
		f.getContentPane().setBackground(Color.BLACK);
		f.setLayout(null);

		//Nastavení label
		JLabel label = new JLabel();
		f.add(label);
		label.setBounds(ww / 2 - 305, 50, 600, 144);
		Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH); //Scaling of the logo
		ImageIcon scaledIcon = new ImageIcon(imgScale); //Image-to-ImageIcon conversion
		label.setIcon((Icon) scaledIcon);
		label.setVisible(true);
		
		//První button, který jen vypíše vybraný kontakt do zamčeného JListu
		JButton b1 = new JButton("Vypsat kontakt");
		f.add(b1);
		b1.setBounds(bx, by, bw, bh);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("--Vypsat kontakt-- bylo požito.");
				JFileChooser filechooser = new JFileChooser(); //Nový FileChooser
				filechooser.setCurrentDirectory(new File("./src/kontakty"));
				filechooser.setFileHidingEnabled(true); //Skrytí předlohy
				disableNav(filechooser); //Uzamčení FileChooseru
				int r = filechooser.showOpenDialog(f); //Získání číselné odpověni
				
				//Přečtení vybraného kontaktu, když je vybraný
				if (r == APPROVE_OPTION) {
					File file = new File(filechooser.getSelectedFile().toString()); //Získání pevné cesty ke kontaktu
					System.out.println("Byl vybrán soubor: " + file); //DEBUG zpráva
					Scanner sc; //Deklarace scanneru
					model.removeAllElements(); //Příprava modelu
					Frame1.f.setState(Frame.ICONIFIED); //Skrytí Framu
					Frame2.main(null); //Spuštení mainu Prohlížeče kontaktů
					try {
						sc = new Scanner(file); //Scanování předem určení cesty
						
						//Ukládání řádků do elementů modelu
						while (sc.hasNextLine()) {
							model.addElement(sc.nextLine());
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(f, "Nebyl vybrán žádný soubor."); //Zpráva v případě nevybrání souboru
				}
			}
		});

		//Druhý button, který otevře Frame pro úpravu existujících kontaktů
		JButton b2 = new JButton("Správa kontaktů");
		f.add(b2);
		b2.setBounds(bx + bw + bg, by, bw, bh);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("--Správa kontaktů-- bylo požito."); //DEBUG zpráva
				JFileChooser filechooser = new JFileChooser();
				filechooser.setCurrentDirectory(new File("./src/kontakty"));
				filechooser.setFileHidingEnabled(true);
				disableNav(filechooser);
				int r = filechooser.showOpenDialog(f);

				PolohaTXT = filechooser.getSelectedFile().toString(); //Deklarace pevné cesty výběru
				System.out.println("Byl vybrán soubor: " + PolohaTXT); //DEBUG zpráva

				if (r == APPROVE_OPTION) {
					File file = new File(PolohaTXT); 
					Scanner sc;
					model.removeAllElements();
					Frame1.f.setState(Frame.ICONIFIED); //Skrytí Framu

					try {
						sc = new Scanner(file); //Nový scanner spojený k vybranému kontaktu
						
						//Vypsání dat z kontaktu do modelu
						while (sc.hasNextLine()) {
							String p = sc.nextLine();
							model.addElement(p);
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					Frame3.main(null); //Inicializace správce kontaktů
				} else {
					JOptionPane.showMessageDialog(f, "Nebyl vybrán žádný soubor.");
				}
			}
		});

		//Třetí button, který vypíše do JListu data z předlohy
		JButton b3 = new JButton("Vytvoření kontaktů");
		f.add(b3);
		b3.setBounds(bx + 2 * bw + 2 * bg, by, bw, bh);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("--Vytvoření kontaktů-- bylo požito."); //DEBUG zpráva

				File file = new File("./src/kontakty/predloha.txt"); //Deklarace předlohy
				System.out.println("Byla načtena předloha: " + file); //DEBUG zpráva

				Scanner sc;
				model.removeAllElements();
				Frame1.f.setState(Frame.ICONIFIED);

				try {
					sc = new Scanner(file); //Scanner pracující s předlohou
					
					//Vypsání předlohy
					while (sc.hasNextLine()) {
						String p = sc.nextLine();
						model.addElement(p);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Frame4.main(null); //Inicializace tvůrce kontaktů

			}
		});

		//Button pro zavření
		JButton b4 = new JButton("Zavřít");
		f.add(b4);
		b4.setBounds(bx + 3 * bw + 3 * bg, by, bw, bh);
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CloseAll();
			}
		});

		f.setVisible(true);
	}

}

package main_code;

import static main_code.Frame1.*;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame2 {

	public static JFrame f = new JFrame("Prohlížeč kontaktů");
	private static JButton b1 = new JButton("Zavřít");

	public static int wh = 400;

	private static void Add() {
		f.add(b1);
		f.add(list);
		return;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		WindowListener listener = (WindowListener) new WindowAdapter() {
			public void windowActivated(WindowEvent evt) {
				f.setIconImage(icon_white);
			}

			public void windowDeactivated(WindowEvent evt) {
				f.setIconImage(icon);
			}
		};
		f.addWindowListener(listener);

		StredOkna();

		list.setBounds(50, 50, ww - 300, 260);
		list.disable(); //Odepření úpravy listu
		b1.setBounds(500, 50, 140, 80);
		
		//Button pro zavření
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.dispose();
				Frame1.f.setState(Frame.NORMAL);
			}
		});
		f.setSize(ww, wh);
		f.getContentPane().setBackground(Color.BLACK);
		f.setVisible(true);
		f.setBounds(wx, wy, ww, wh);
		f.setLayout(null);
		Add();

	}

}

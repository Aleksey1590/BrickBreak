package client;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A class extending JButton for use in the user interface.
 */
@SuppressWarnings("serial")
public class Button extends JButton {
	public static int radius = 20;
	private boolean hover;
	private Color COLOR1, COLOR2;

	/**
	 * Creates a new button.
	 */
	public Button() {
		COLOR1 = new Color(250, 250, 250);
		COLOR2 = new Color(197, 197, 197);
		paintcolor(COLOR1, COLOR2);
	}

	/**
	 * Creates a new button given some text.
	 * 
	 * @param name
	 *            The text for the button.
	 */
	public Button(String name) {
		super.setText(name);
		COLOR1 = new Color(250, 250, 250);
		COLOR2 = new Color(197, 197, 197);
		paintcolor(COLOR1, COLOR2);
	}

	/**
	 * Creatses a new button given some ImageIcon.
	 * 
	 * @param icon
	 *            The icon for the button.
	 */
	public Button(ImageIcon icon) {
		super(icon);
		COLOR1 = new Color(107, 85, 163);
		COLOR2 = new Color(30, 13, 72);
		paintcolor(COLOR1, COLOR2);
	}

	private void paintcolor(Color COLOR1, Color COLOR2) {
		setMargin(new Insets(0, 0, 0, 0));
		setBorderPainted(false);
		setForeground(Color.black);
		setFocusPainted(false);
		setContentAreaFilled(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
		});
	}

	/**
	 * Paints the button on screen.
	 */
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int height = getHeight();
		int with = getWidth();
		float tran = 0.6F;
		if (!hover) {
			tran = 0.3F;
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, height, new Color(100, 100, 100), true);
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, height, new Color(255, 255, 255, 100), true);
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, height, new Color(0, 0, 0), true);
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, height, new Color(0, 0, 0, 50), true);
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, with - 1, height - 1, radius, radius);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, COLOR1, 0.0F, height / 2, COLOR2, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, with, height);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, with - 3, height - 3, radius, radius);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, with - 3, height - 3, radius, radius);
		g2d.dispose();
		super.paintComponent(g);
	}

}
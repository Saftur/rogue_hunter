package rogue;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.FileReader;
import asciiPanel.AsciiPanel;
import rogue.screens.Screen;
import rogue.screens.StartScreen;

public class RoguePlus extends JFrame implements KeyListener {
	private static final long serialVersionUID = -2007269423561740435L;
	
	public static RoguePlus app;
	
	public static void exit() {
		RoguePlus.app.dispose();
		System.exit(0);
	}
	
	public static int[] version = new int[3];
	public static String version_text;
	
	public static String pname;
	public static long round_num;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public static boolean shift;
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT)
			shift = true;
		else {
			screen = screen.respondToUserInput(e);
			repaint();
		}
	}

    public void keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_SHIFT)
    		shift = false;
    }

    public void keyTyped(KeyEvent e) { }
    
    public RoguePlus() {
    	super();
    	char[] lastVersion = FileReader.readFileChar("changelog.txt");
    	String[] vt = new String[]{"","",""};
        version_text = "";
        shift = false;
        int pos = 0;
        for (char c : lastVersion) {
        	if (c == '.' && pos < 2) {
        		pos++;
        	} else if (c == ':') {
        		break;
        	} else {
        		vt[pos] += c;
        	}
        	version_text += c;
        }
        for (int i=0;i<3;i++) {
        	version[i] = Integer.parseInt(vt[i]);
        }
        pname = "";
    	terminal = new AsciiPanel();
    	add(terminal);
    	pack();
    	screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
    
    public static void main(String[] args) {
        app = new RoguePlus();
        app.setTitle("Rogue+");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}

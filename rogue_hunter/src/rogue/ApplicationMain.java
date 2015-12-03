package rogue;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;

import fr.FileReader;
import asciiPanel.AsciiPanel;
import rogue.screens.Screen;
import rogue.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {
	private static final long serialVersionUID = -2007269423561740435L;
	
	public static int[] version = new int[3];
	public static String version_text;
	
	public static String pname;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public void keyPressed(KeyEvent e) {
		System.out.println("key");
        screen = screen.respondToUserInput(e);
        repaint();
	}

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }
    
    public ApplicationMain() {
    	super();
    	char[] lastVersion = FileReader.readFileChar("changelog.txt");
    	String[] vt = new String[]{"","",""};
        version_text = "";
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
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}

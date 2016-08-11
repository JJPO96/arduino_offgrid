package arduino_gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;



public class ArdOffgrid {

	private JFrame frmArduinoOffgrid;

	SerialPort serialPort;

	String stringPort = "COM5";
	
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM5", // Windows
	};

	/**
	 * A BufferedReader which will be fed by a InputStreamReader 
	 * converting the bytes into characters 
	 * making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

	public void initializeSerial() throws NoSuchPortException {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		portId = CommPortIdentifier.getPortIdentifier(stringPort);
		
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	private boolean l1 = false;
	private boolean l2 = false;
	private boolean l3 = false;
	private boolean l4 = false;

	void updateArduino(){
		int b0 = 0b0001;
		int b1 = 0b0010;
		int b2 = 0b0100;
		int b3 = 0b1000;
		int ret = 0;

		if(l1)
			ret |= b0;
		if(l2)
			ret |= b1;
		if(l3)
			ret |= b2;
		if(l4)
			ret |= b3;


		try {
			//sends the integer for the arduino through the serial port
			output.write(ret);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error writing to serial Port");
		}
		
		System.out.println(Integer.toBinaryString(ret));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArdOffgrid window = new ArdOffgrid();
					window.frmArduinoOffgrid.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchPortException 
	 */
	public ArdOffgrid() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, NoSuchPortException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initializeSerial();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmArduinoOffgrid = new JFrame();
		frmArduinoOffgrid.setBackground(Color.DARK_GRAY);
		frmArduinoOffgrid.setTitle("Arduino Offgrid");
		frmArduinoOffgrid.setBounds(100, 100, 500, 240);
		frmArduinoOffgrid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmArduinoOffgrid.getContentPane().setLayout(null);

		JButton btn1 = new JButton("Luz 1");
		btn1.setBounds(25, 95, 90, 25);
		frmArduinoOffgrid.getContentPane().add(btn1);

		JButton btn2 = new JButton("Luz 2");
		btn2.setBounds(140, 95, 90, 25);
		frmArduinoOffgrid.getContentPane().add(btn2);

		JButton btn3 = new JButton("Luz 3");
		btn3.setBounds(255, 95, 90, 25);
		frmArduinoOffgrid.getContentPane().add(btn3);

		JButton btn4 = new JButton("Luz 4");
		btn4.setBounds(370, 95, 90, 25);
		frmArduinoOffgrid.getContentPane().add(btn4);

		JButton btnAll = new JButton("Ligar todas");
		btnAll.setBounds(25, 130, 435, 25);
		frmArduinoOffgrid.getContentPane().add(btnAll);

		JButton btnUAll = new JButton("Desligar todas");
		btnUAll.setBounds(25, 165, 435, 25);
		frmArduinoOffgrid.getContentPane().add(btnUAll);

		JTextPane pnl1 = new JTextPane();
		if(l1)
			pnl1.setBackground(new Color(50, 205, 50));
		else
			pnl1.setBackground(new Color(255, 30, 30));
		pnl1.setEditable(false);
		pnl1.setBounds(25, 10, 90, 75);
		frmArduinoOffgrid.getContentPane().add(pnl1);

		JTextPane pnl2 = new JTextPane();
		if(l2)
			pnl2.setBackground(new Color(50, 205, 50));
		else
			pnl2.setBackground(new Color(255, 30, 30));
		pnl2.setEditable(false);
		pnl2.setBounds(140, 10, 90, 75);
		frmArduinoOffgrid.getContentPane().add(pnl2);

		JTextPane pnl3 = new JTextPane();
		if(l3)
			pnl3.setBackground(new Color(50, 205, 50));
		else
			pnl3.setBackground(new Color(255, 30, 30));
		pnl3.setEditable(false);
		pnl3.setBounds(255, 10, 90, 75);
		frmArduinoOffgrid.getContentPane().add(pnl3);

		JTextPane pnl4 = new JTextPane();
		if(l4)
			pnl4.setBackground(new Color(50, 205, 50));
		else
			pnl4.setBackground(new Color(255, 30, 30));
		pnl4.setEditable(false);
		pnl4.setBounds(370, 10, 90, 75);
		frmArduinoOffgrid.getContentPane().add(pnl4);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l1 = !l1;
				if(l1)
					pnl1.setBackground(new Color(50, 205, 50));
				else
					pnl1.setBackground(new Color(255, 30, 30));
				updateArduino();
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l2 = !l2;
				if(l2)
					pnl2.setBackground(new Color(50, 205, 50));
				else
					pnl2.setBackground(new Color(255, 30, 30));
				updateArduino();
			}
		});

		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l3 = !l3;
				if(l3)
					pnl3.setBackground(new Color(50, 205, 50));
				else
					pnl3.setBackground(new Color(255, 30, 30));
				updateArduino();
			}
		});

		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l4 = !l4;
				if(l4)
					pnl4.setBackground(new Color(50, 205, 50));
				else
					pnl4.setBackground(new Color(255, 30, 30));
				updateArduino();
			}
		});

		btnAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l1 = true;
				l2 = true;
				l3 = true;
				l4 = true;
				pnl1.setBackground(new Color(50, 205, 50));
				pnl2.setBackground(new Color(50, 205, 50));
				pnl3.setBackground(new Color(50, 205, 50));
				pnl4.setBackground(new Color(50, 205, 50));
				updateArduino();
			}
		});

		btnUAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l1 = false;
				l2 = false;
				l3 = false;
				l4 = false;
				pnl1.setBackground(new Color(255, 30, 30));
				pnl2.setBackground(new Color(255, 30, 30));
				pnl3.setBackground(new Color(255, 30, 30));
				pnl4.setBackground(new Color(255, 30, 30));
				updateArduino();
			}
		});
	}
}

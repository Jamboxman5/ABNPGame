package me.jamboxman5.abnpgame.main;

import javax.swing.JFrame;


public class Launcher {
	
	public static JFrame window;
	
	public static void main(String[] args) {
		
		windowSetup();
		
	}

	private static void windowSetup() {
						
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Game");
				
		GamePanel gamePanel = new GamePanel(window);
		
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.start();
		
//		Timer t1 = new Timer(17, new Resizer());
//		t1.start();
//		Timer t2 = new Timer(17, new FieldChecker());
//		t2.start();
//		Timer t3 = new Timer(1000, new ConnectionChecker());
//		t3.start();
//		Timer t4 = new Timer(17, new ButtonChecker());
//		t4.start();
		
//		Timer t5 = new Timer(17, new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				if (currentPhase == Phase.EDIT) {
//					treePanel.draw();
//				}
//				
//			}
//			
//		});
		
//		t5.start();
		
		window.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//		        thread = null;
//		        t1.stop();
//		        t2.stop();
//		        t3.stop();
//		        t4.stop();
//		        t5.stop();
		    }
		});
		
		
				
	}

}

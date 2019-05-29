import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
public class BattleShipsController{
	/** BattleShipsController manipulon me objektet e tjera duke dhene referenca */
	public void launch(){
		
		JFrame jf = new JFrame();
		JFrame jf2 = new JFrame();
		jf.setTitle("Player 1");
		jf2.setTitle("Player 2");
		// krijojme dy frames per dy lojtaret
		KeyListen kl  = new KeyListen();
		KeyListen kl2 = new KeyListen();
		
		MouseListen ml = new MouseListen();
		MouseListen ml2 = new MouseListen();
		//na duhet nga dy objekte keylisten dhe mouselisten per dy dritaret ku do te luhet
		ml.setFrames(jf,jf2);
		ml2.setFrames(jf2,jf);
		// dy instancave te mouselisten ju duhen referencat e dritareve te veta si dhe te dritares tjeter ne menyre qe te manipulojne me to
		// gjate fazave te ndryshme te lojes
		
		BattleShipsPainter bsp = new BattleShipsPainter(kl,ml);
		BattleShipsPainter bsp2 = new BattleShipsPainter(kl2,ml2);
		 //krijimi i dy bsp-ve per  dy dritaret 
		
		jf.addKeyListener(kl);
		jf2.addKeyListener(kl2);
		// dhenia e referencave te dyja objekteve KeyListen instancave te frame-it
		
		
		
		kl.setBsp(bsp);
		kl2.setBsp(bsp2);
		// dhenia e referencave te bsp-ve instancave KeyListen per manipulim me butona
		ml.setOther(bsp2,ml2);
		ml2.setOther(bsp,ml);
		// dhenia e referencave te bsp-ve si dhe te njera tjetres te MouseListen per manipulim me ndryshore si dhe me dritare
		ml.setBsc(this);
		ml2.setBsc(this);
		// dhenia e references te kesaj klase te klasa mouselisten, ne menyre qe te mund te thirret po kjo metode ne rast se 
		// perdoruesi deshiron te luaj prap
		jf.setSize(350,350);
		jf2.setSize(350,350);
		
		jf.getContentPane().add(bsp);
		
		jf2.getContentPane().add(bsp2);
		
		
		jf.setVisible(true);
		jf2.setLocation(jf.getX()+jf.getWidth(), jf.getY());
		// se pari e bejme te dukshme vetem Frame-in e lojtarit te pare ndersa ne klasen MouseListener ndodhe edhe e anasjellta
		
		JOptionPane.showMessageDialog(null, "Player 1 deployment");
	
		
	}
	
	
}
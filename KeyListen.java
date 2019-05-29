import java.awt.*;
import java.awt.event.*;

public class KeyListen implements KeyListener{
	/** Klassa KeyListen ka per detyre qe te ndermarre veprime varesisht prej butonave te shtypur ne tastiere*/
	private BattleShipsPainter bsp;
	private int currentId;
	private boolean deploymentFinished;
	// merr si reference BattleShipsPainter sepse i duhet te kete efekt ne kornize si dhe i duhet indeksi i panelit ku gjendet kursori
	// gjitheashtu i duhe te dije nese faza e vendosjes se anijeve ka perfunduar
	
	@Override
	public void keyPressed(KeyEvent e){
		// kjo metode ndermerr veprime varesisht prej butonave qe shtypen, por e humbe efektin nese vendosja e anijeve ka mbaruar
		if(!deploymentFinished){
			if(e.getKeyCode()==40){
				// nese eshte prekur butoni shigjeta e poshtme, atehere kontrollon a mund te vizatohet dhe i kthen panelet 
				// deri te gjatesia e vet ne ngjyren fillestare ndersa ata nen te i kthen ne ngjyren e anijes
				if(bsp.isPaintable(currentId)){
					bsp.paint(currentId,Color.blue); 
				}
				bsp.setDirection(false); 
				if(bsp.isPaintable(currentId)){
				
					bsp.paint(currentId,Color.white);
				}
			}
			if(e.getKeyCode()==39){
				// e njejta logjike eshte perdorur per kthimin e anijes ne pozite horizontale
				if(bsp.isPaintable(currentId)){
					bsp.paint(currentId,Color.blue);
				}
					bsp.setDirection(true); 
				if(bsp.isPaintable(currentId)){	
					bsp.paint(currentId,Color.white);
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e){
			}
	
	@Override
	public void keyTyped(KeyEvent e){
		
	}

	public void setBsp(BattleShipsPainter bsp){
		this.bsp=bsp;
	}
	public void setCurrentId(int id){
		currentId=id;
	}
	public void setDeploymentFinished(boolean deploymentFinished){
		this.deploymentFinished=deploymentFinished;
	}
	
	
}

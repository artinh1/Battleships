import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class MouseListen implements MouseListener{
	private BattleShipsPainter ownBsp; //referenca e bsp qe korespondon me kete objekt
	private BattleShipsPainter otherBsp; // referenca e bsp qe korsepondon me objektin e dritares tjeter
	private BattleShipsController bsc;
	private MouseListen other; // referenca e MouseListen te dritares tjeter
	private KeyListen kl; 
	private int[] pattern; //referenca e patternit per manipulim gjate klikimeve
	private int timesClicked=2; // kjo ndryshore na duhet ne menyre qe te kemi gjatesine 3 te anijeve dy here
	
	private JFrame ownFrame; // referenca e frame-it te dritares ku gjendet ky objekt
	private JFrame otherFrame; // referenca e frame-it tjeter
	private ArrayList<JPanel> panels; // lista e paneleve duhet per manipulim
	private int hits; // kjo ndryshore ruan numrin e goditjes se anijeve ne loje
	private static int count; // kjo ndryshore duhet per kontrollimin e ekzekutimit te nje pjese specifike ne kod
	private static boolean deploymentFinished; // ndryshorja qe ruan true ose false varesisht se a eshte perfunduar vendosja e anijeve apo jo
	private int currentId; // indeksi ne liste i panelit ku gjendet kursori
	
	@Override
	public void mousePressed(MouseEvent e){
		
	}
    @Override
	public void mouseReleased(MouseEvent e){
    }

    @Override
	public void mouseEntered(MouseEvent e){
		
		JPanel jp = (JPanel)e.getSource();

		currentId = panels.indexOf(jp);
		
		ownBsp.setCurrentId(currentId);
		if(!deploymentFinished){
			// kjo pjese kodi qe varet nga deploymentFinished, vizaton nje lloj preview te anijeve para vendosjes
			kl.setCurrentId(currentId);
			if(ownBsp.isPaintable(currentId)){
				ownBsp.paint(currentId,Color.white);
			
			}
		}
	}

    @Override
	public void mouseExited(MouseEvent e){
		// kjo metode varet nga deploymentFinished dhe pas largimit te kursorit nga paneli ku gjendej, e kthen ngjyren fillestare
		if(!deploymentFinished){
			JPanel jp = (JPanel)e.getSource();
		    int id = panels.indexOf(jp);
			if(ownBsp.isPaintable(id)){
				ownBsp.paint(id,Color.blue);
			}
		}
	}

    @Override
	public void mouseClicked(MouseEvent e){
		// kjo eshte metoda kryesore dhe kontrollon nje pjese mjaft te madhe te lojes
		JPanel jp = (JPanel)e.getSource();
		int id = panels.indexOf(jp);	
		if(ownBsp.getLength()!=6){
			//kjo pjese kodi ekzekutohet vetem nese gjatesia e anijes nuk e ka arritur 6
		
			if(ownBsp.isPaintable(id)){	
				ownBsp.paint(id,Color.green);
			
			
				timesClicked++;
				// vizatojme pjesen e klikuar me ngjyre te gjelber si dhe rrisim numrin e klikimeve
				if(timesClicked!=4){ownBsp.raiseLength(); //rritja e gjatesise dhe mbajtja e gjatesise 3 dy here
				}
				
				if(ownBsp.getDirection()){
					//vizaton ne varesi te drejtimit, si dhe i kthen vlerat koresponduese ne pattern ne 1
					for(int i = id; i<id+ownBsp.getLength(); i++){
						if(panels.get(i).getBackground().equals(Color.green)){
							pattern[i]=1;
							}
						}
				}else{
					for(int i = id; i<id+ownBsp.getLength()*10; i+=10){
						if(panels.get(i).getBackground().equals(Color.green)){
							pattern[i]=1;
						}
					}
				}
				
			}
			
			
		}else{
			//eshte perdorur counti qe te ekzekutohet kjo pjese vetem nje here
			count++;
			if(count<2){
				ownBsp.resetAll();
				ownFrame.setVisible(false);
				otherFrame.setVisible(true);
				JOptionPane.showMessageDialog(null,"Player 2 deployment!");
			}else if(!deploymentFinished){ // ketu eshte perdorur deploymentFinished per kontrollimin e ekzekutimit te kesaj pjese poashtu
			
				JOptionPane.showMessageDialog(null,"Get ready, the game begins now!");
				deploymentFinished=true;
				kl.setDeploymentFinished(deploymentFinished);
				ownBsp.resetAll();
				
				
			}else{
				//tani behet kontrollimi nese vendi i klikuar ka anije apo jo me ane te patternit 
				if(pattern[currentId]==1){
					ownBsp.paintSingle(currentId,Color.red);
					JOptionPane.showMessageDialog(null, "Hit!");
					hits++;
					pattern[currentId]=2;
					//nese jane 17 goditje ne total, atehere ai lojtar qe i ka bere fiton
					if(hits==17){
						int ans =JOptionPane.showConfirmDialog(null, "You win! Want to play again?");
						if(ans==0){
							deploymentFinished=false;
							ownBsp.resetPattern();
							otherBsp.resetPattern();
							ownBsp.resetAll();
							otherBsp.resetAll();
							ownBsp.setLength(2);
							otherBsp.setLength(2);
							this.resetClicksHits();
							other.resetClicksHits();
							ownFrame.setVisible(false);
							otherFrame.setVisible(false);
							ownFrame.dispose();
							otherFrame.dispose();
							bsc.launch();
							//resetimi i ndryshoreve per fillimin e lojes nga fillimi
						}else{
							ownFrame.setVisible(false);
							otherFrame.setVisible(false);
							ownFrame.dispose();
							otherFrame.dispose();
							// mbyllja e lojes
						}
					}
				}else if(pattern[currentId]==0){
					// nese nuk eshte goditur ndonje anije, i jepet radha lojtarit tjeter
					ownBsp.paintSingle(currentId,Color.cyan);
					JOptionPane.showMessageDialog(null,"Miss!");
					ownFrame.setVisible(false);
					otherFrame.setVisible(true);
					pattern[currentId]=3;
					
				}else{}
			}
			
			
			
		}
	}
	public void setBsp(BattleShipsPainter bsp){
		this.ownBsp=bsp;
	}
	public void setOther(BattleShipsPainter otherBsp, MouseListen other){
		this.otherBsp = otherBsp;
		this.other=other;
		
	}
	public void setKeyListen(KeyListen kl){
		this.kl=kl;
	}
	public void setPattern(int[]pattern){
		this.pattern=pattern;
	}
	public void setPanels(ArrayList<JPanel> panels){
		this.panels=panels;
	}
	public void setFrames(JFrame ownFrame, JFrame otherFrame){
		this.ownFrame=ownFrame;
		this.otherFrame=otherFrame;
	}
	public void resetClicksHits(){
		hits=0;
		timesClicked=2;
		count=0;
	}
	public void setBsc(BattleShipsController bsc){
		this.bsc=bsc;
	}

	
	
	
}
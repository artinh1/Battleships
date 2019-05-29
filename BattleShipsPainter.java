import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BattleShipsPainter extends JPanel{
	/** BattleShipsPainter e ka si detyre kryesore vizatimin e paneleve ne kornize */
	private JPanel panel; // Paneli i cili futet ne kornizen kryesore dhe ne te cilin do te futen te gjithe panelet tjere ne forme grid
	private ArrayList<JPanel>  panels; // lista e te gjitha paneleve ne kornize
	private int [] pattern; // patterni i cili na tregon permes numrave se cilet panele jane te ngjyrosur
	private boolean direction = true; // ndryshorja e cila na tregon drejtimin e anijes (true per pozite horizontale dhe false per vertikale)
	private int length=2; // gjatesia fillestare eshte 2
	private int currentId; // treguesi se ne cilin indeks te listes gjendet paneli ne te cilin ka hyre kursori
	private KeyListen kl; // referenca e klases KeyListen e cila na duhet vetem qe ti ipet klases MouseListen
	private MouseListen ml; // referenca e klases MouseListen te cilen do ta marrin te gjithe panelet qe futen ne kornize
	


	
	public BattleShipsPainter(KeyListen kl, MouseListen m){
		panels = new ArrayList<JPanel>();
		pattern = new int[100];
		ml= m;
		ml.setBsp(this);
		ml.setPanels(panels);
		ml.setPattern(pattern);
		ml.setKeyListen(kl);
		
		
		this.kl=kl;
		
		panel = new JPanel(new GridLayout(10,10));
	    // krijimi i objekteve dhe dhenia e referencave te lartpermendura
		
		
		for(int i =0; i<100; i++){
			JPanel otherPanels = new JPanel();
			otherPanels.setBackground(Color.blue);
			
			otherPanels.setPreferredSize(new Dimension(30,30));
			otherPanels.setBorder(BorderFactory.createLineBorder(Color.red));
			otherPanels.addMouseListener(ml);
			panels.add(otherPanels);
			panel.add(panels.get(i));
			
			// Krijimi i 100 paneleve dhe futja e tyre ne panelin kryesor se bashku me referencen e MouseListen
		}
		
		
		
		this.add(panel);
		// futja e panelit kryesor

	}
	
	
	public boolean isPaintable(int id){
		//kjo metode shqyrton nese mund te vizatohet aty ku deshiron perdoruesi dhe merr si argument indeksin e panelit ne te cilen gjendet kursori
		boolean rez = true;
		
		if(direction){
			// nese drejtimi eshte horizontal, kontrollojme patternin per numra tjere perveq zeros prej indeksit te panelit deri te indeksi indeksi+gjatesia
			try{
				for(int i = id; i<id+length; i++ ){
					if(pattern[i]>=1 || (i+1)%10==0){
					rez=false;
					break;						}
				}
			}catch(Exception ex){ rez= false;}
		}else if(!direction && length>1){
			try{
				//nese drejtimi eshte vertikal atehere na duhet qe te kontrollojme patternin por kete here diferenca mes indekseve te dy paneleve qe perbejne nje anije eshte 10
				for(int i = id; i<id+length*10; i+=10){
					if(pattern[i]>=1){							
					rez=false;
						break;
					}
					
				}
			}catch(Exception ex){ rez=false; }
		}
		
		return rez;
	}
	
	public void paint(int id, Color color){
		if(direction){
			//vizaton sipas te njejtes logjike
			try{
				for(int i =  id; i<id+length; i++){
					panels.get(i).setBackground(color);
				}
			}catch(Exception ex){}
		}else{
			try{
				for(int i = id; i<id+length*10; i+=10){
					panels.get(i).setBackground(color);
				}
			}catch(Exception ex){}
		}
	}
	public void paintSingle(int id,Color color){
		//vizaton ne vetem nje panel
		panels.get(id).setBackground(color);
	}
	public void resetAll(){
		//i kthen te gjitha panelet ne ngjyren fillestare(te kalter)
		for(int i = 0; i<100; i++){
			panels.get(i).setBackground(Color.blue);
		}
	}
	public void resetPattern(){
		//e kthen patternin ne gjendjen fillestare me te gjitha elementet 0
		for(int i = 0; i<pattern.length; i++){
			pattern[i]=0;
		}
	}
	public void setDirection(boolean direction){
		this.direction=direction;
	}
	public boolean getDirection(){
		return direction;
	}
	public int getCurrentId(){
		return currentId;
	}
	
	public void setCurrentId(int currentId){
		this.currentId=currentId;
	}
	public void raiseLength(){length++;}
	public void setLength(int length){
		this.length=length;
	}
	public int getLength(){return length;}
	
	
	
	
}
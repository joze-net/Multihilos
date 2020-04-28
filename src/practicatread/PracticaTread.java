
package practicatread;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class PracticaTread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame marco=new MarcoRebote();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.setVisible(true);

	}

}




//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota{
	
	// Mueve la pelota invirtiendo posici�n si choca con l�mites
	
	public void mueve_pelota(Rectangle2D limites){
		
		x+=dx;
		
		y+=dy;
		
		if(x<limites.getMinX()){
			
			x=limites.getMinX();
			
			dx=-dx;
		}
		
		if(x + TAMX>=limites.getMaxX()){
			
			x=limites.getMaxX() - TAMX;
			
			dx=-dx;
		}
		
		if(y<limites.getMinY()){
			
			y=limites.getMinY();
			
			dy=-dy;
		}
		
		if(y + TAMY>=limites.getMaxY()){
			
			y=limites.getMaxY()-TAMY;
			
			dy=-dy;
			
		}
		
	}
	
	//Forma de la pelota en su posici�n inicial
	
	public Ellipse2D getShape(){
		
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
		
	}	
	
	private static final int TAMX=15;
	
	private static final int TAMY=15;
	
	private double x=0;
	
	private double y=0;
	
	private double dx=1;
	
	private double dy=1;
	
	
}

// L�mina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{
	
	//A�adimos pelota a la l�mina
	
	public void add(Pelota b){
		
		pelotas.add(b);
	}
	
        @Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		for(Pelota b: pelotas){
			
			g2.fill(b.getShape());
		}
		
	}
	
	private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}


//Marco con l�mina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{
	
	public MarcoRebote(){
		
		setBounds(600,300,400,350);
		
		setTitle ("Rebotes");
		
		lamina=new LaminaPelota();
		
		add(lamina, BorderLayout.CENTER);
		
		JPanel laminaBotones=new JPanel();
		
		ponerBoton(laminaBotones, "Dale!", new ActionListener(){
			
                        @Override
			public void actionPerformed(ActionEvent evento){
				
				comienza_el_juego();
			}
			
		});
		
		
		ponerBoton(laminaBotones, "Salir", new ActionListener(){
			
                        @Override
			public void actionPerformed(ActionEvent evento){
				
				System.exit(0);
				
			}
			
		});
                
                ponerBoton(laminaBotones, "Detener", new ActionListener(){
			
                        @Override
			public void actionPerformed(ActionEvent evento){
				
				//t.stop();//este metodo no es recomendado, pero lo que hace es detener la ejecucion de uno de los hilos
				t.interrupt();
			}
			
		});
		
		add(laminaBotones, BorderLayout.SOUTH);
	}
	
	
	//Ponemos botones
	
	public void ponerBoton(Container c, String titulo, ActionListener oyente){
		
		JButton boton=new JButton(titulo);
		
		c.add(boton);
		
		boton.addActionListener(oyente);
		
	}
	
	//A�ade pelota y la bota 1000 veces
	
	public void comienza_el_juego (){
		
					
			Pelota pelota=new Pelota();
			
			lamina.add(pelota);
			
			Runnable r=new PelotaHilos(pelota,lamina);//se debe instanciar un objeto que implementa la interfaz Runnable
                        
                        t=new Thread(r);//tambine instanciar la clase Thread para pasarle por parametro el obj Runnable
                        
                        t.start();//y ejecutar el hilo de ejecucion
			
		       
		
	}
        
        class PelotaHilos implements Runnable{//la clase que tendra las multiples tareas debe implementar la interfaz Runnable
          
            
            public PelotaHilos(Pelota laPelota,Component componente){
                this.pelota=laPelota;
                this.componente=componente;
            }

        @Override
        public void run() {
            
            
            while(!Thread.interrupted()){
				
				pelota.mueve_pelota(componente.getBounds());
				
				componente.paint(componente.getGraphics());
                                
                                 try{
                       //Thread.sleep(4);//esto es para dar tiempo de espera y dar sensacion de movimiento
                       
                                 }catch(Exception e){
                            System.out.println("Ocurrio un error, metodo comienza_el_juego");
                        }
				
			}
            
        }
        
        private Pelota pelota;
        private Component componente;
            
        }
	Thread t;
	private LaminaPelota lamina;
	
	
}


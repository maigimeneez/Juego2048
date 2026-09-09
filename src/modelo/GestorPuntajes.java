package modelo;

import java.util.List;
import java.util.ArrayList;

public class GestorPuntajes {

	//lissta que guarda los putnajes
	private static List<Integer> mejoresPuntajes = new ArrayList<>();
	
	public static void agregarPuntaje(int nuevoPuntaje) {
		mejoresPuntajes.add(nuevoPuntaje);
		
		//metodo de ordenamiento de puntajes de la lista
		int n = mejoresPuntajes.size();
		
		for (int i=0; i< n-1; i++) {
			int mayor = i;
			
			for(int j = i+1; j<n; j++) {
				if(mejoresPuntajes.get(j) > mejoresPuntajes.get(mayor)){
					mayor = j;
				}
			}
			
			//Se van cambiando cuando se encuentra ptje mayor
			int aux = mejoresPuntajes.get(i);
			mejoresPuntajes.set(i, mejoresPuntajes.get(mayor));
			mejoresPuntajes.set(mayor, aux);
		}
		
		//cuando la lista esta llena remueve el ultimo del top
		if(mejoresPuntajes.size() > 10) {
			mejoresPuntajes.remove(mejoresPuntajes.size()-1);
		}
	}
	
	
	public static List<Integer> getMejoresPuntajes(){
		return mejoresPuntajes;
	}
	
	
}

package prueba;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Vehiculo;

public class Programa {

	public static void main(String[] args) {

		// EntityManager permite realizar operaciones con la BD, lo obtenemos a
		// Se obtiene a través del EntityManagerFactory y este a su vez se genera 
		// a partir del nombre de la unidad de persistencia (fichero persistence.xml)
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rentacar");
		EntityManager em = entityManagerFactory.createEntityManager();

		// Se crea la Query a partir del nombre de la NamedQuery de la clase Vehiculo
		Query q = em.createNamedQuery("Vehiculo.findAll");
		// Se ejecuta la consulta con getResultList haciendo un casting
		List<Vehiculo> listaVehiculos = (List<Vehiculo>) q.getResultList();

		// Se imprime la lista 
		for (Vehiculo v : listaVehiculos) {
			System.out.println(v);
		}

		// Se cierra el objeto EntityManager
		em.close(); 

	}

}

package prueba;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Vehiculo;

public class Programa {

	public static void main(String[] args) {

		// Se obtienen todas las intancias
		List<Vehiculo> listaVehiculos = findAll();

		// Se imprime la lista
		for (Vehiculo v : listaVehiculos) {
			System.out.println(v);
		}

		// Se obtiene una entidad
		System.out.println("Buscar Vehiculo de matrícula 0034AAB ------------ ");
		Vehiculo v = findByMatricula("0034AAB");
		System.out.println(v);

	}

	public static List<Vehiculo> findAll() {
		EntityManager em = getEntityManager();
		// Se crea la Query a partir del nombre de la NamedQuery de la clase Vehiculo
		Query q = em.createNamedQuery("Vehiculo.findAll");
		// Se ejecuta la consulta con getResultList haciendo un casting
		List<Vehiculo> listaVehiculos = (List<Vehiculo>) q.getResultList();
		em.close();
		return listaVehiculos;
	}

	public static Vehiculo findByMatricula(String matricula) {
		EntityManager em = getEntityManager();
		// Se crea la Query a partir del nombre de la NamedQuery de la clase Vehiculo
		Query q = em.createNamedQuery("Vehiculo.findMatricula");
		// Se establece el parámetro de la consulta
		q.setParameter("matricula", matricula);
		// Se ejecuta la consulta
		Vehiculo v = (Vehiculo) q.getSingleResult();
		em.close();
		return v;
	}

	private static EntityManager getEntityManager() {
		// EntityManager permite realizar operaciones con la BD, lo obtenemos a
		// Se obtiene a través del EntityManagerFactory y este a su vez se genera
		// a partir del nombre de la unidad de persistencia (fichero persistence.xml)
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rentacar");
		EntityManager em = entityManagerFactory.createEntityManager();
		return em;
	}

}

package prueba;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Vehiculo;

public class Programa {

	public static void main(String[] args) {

		// Se obtienen todas las instancias
		List<Vehiculo> listaVehiculos = findAll();

		// Se imprime la lista
		System.out.println("Todas las entidades ------------ ");
		for (Vehiculo v : listaVehiculos) {
			System.out.println(v);
		}

		// Se obtiene una entidad
		System.out.println("Buscar Vehiculo de matrícula 0034AAB ------------ ");
		Vehiculo aux = findByMatricula("0034AAB");
		System.out.println(aux);

		// Creación de una entidad
		Vehiculo v = new Vehiculo();
		v.setBastidor("6634543Z01");
		v.setMatricula("0998FRR");
		v.setDisponible(true);
		v.setMarca("Renault");
		v.setModelo("Clio");
		v.setPrecio(14.00);
		// createVehiculo(v); // Si está creada lanzará una excepción

		// Se obtienen todas las instancias
		listaVehiculos = findAll();
		System.out.println("Todas las entidades ------------ ");
		listaVehiculos.forEach(System.out::println);

		// Se modifica el precio del vehículo id = 1
		Vehiculo nuevo = findByPK(1);
		if (nuevo!=null) {
			nuevo.setPrecio(134);
			System.out.println("Nuevo " + nuevo);
			modifyVehiculo(nuevo);
		}
		

		// Se obtienen todas las instancias
		listaVehiculos = findAll();
		System.out.println("Todas las entidades ------------ ");
		listaVehiculos.forEach(System.out::println);

	}

	public static void modifyVehiculo(Vehiculo v) {
		EntityManager em = getEntityManager();
		// En este caso es necesario iniciar una transacción en la base de datos
		// porque vamos a persistir información en la misma
		em.getTransaction().begin();
		// merge(Objeto) - Si una entidad con el mismo identificador que v existe en el
		// contexto de persistencia (caché), se actualizan sus atributos y se devuelve como
		// entidad gestionada
		// Si el objeto v no existe en la base de datos, se comporta como persist() y la
		// entidad gestionada es la devuelta por merge()
		em.merge(v);
		em.getTransaction().commit();
		em.close();

	}

	public static void createVehiculo(Vehiculo v) {
		EntityManager em = getEntityManager();
		// En este caso es necesario iniciar una transacción en la base de datos
		// porque vamos a persistir información en la misma
		em.getTransaction().begin();
		// Se guarda el objeto en el contexto de persistencia (caché intermedia)
		em.persist(v);
		// Se vuelca la información del contexto (caché intermedia) en la base de datos
		em.getTransaction().commit();
		// Cierra el entityManager
		em.close();
	}

	public static Vehiculo findByPK(int pk) {
		EntityManager em = getEntityManager();
		Vehiculo aux = null;
		// Se crea el objeto Query a partir de una SQL nativa
		Query q = em.createNativeQuery("Select * from vehiculo where id = ?", Vehiculo.class);
		q.setParameter(1, pk);
		aux = (Vehiculo) q.getSingleResult();
		return aux;
		
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

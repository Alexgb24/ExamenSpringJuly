
/**
 * Instrucciones:
 * 
 *  - Crea un repo privado compartido sólo con dfleta en GitHub.
 *  - Realiza un commit al pasar cada caso test.
 *  - Sin este commit tras cada caso, no corrijo el examen.
 */

package org.elsmancs.practica;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.elsmancs.practica.controlador.Controlador;
import org.elsmancs.practica.dominio.MagicalItem;
import org.elsmancs.practica.dominio.Order;
import org.elsmancs.practica.dominio.Wizard;
import org.elsmancs.practica.repositorio.NotEnoughDexterityException;
import org.elsmancs.practica.repositorio.Repositorio;
import org.elsmancs.practica.servicio.Servicio;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Construye una aplicacion que maneja la base de datos
 * de la tienda Ollivander's,
 * con las personas magas (wizards) de la tienda 
 * y los items disponibles (items), que son todos
 * del tipo MagicalItem.
 * Las magas realizan pedidos (orders) al servicio
 * para comprar items. 
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(statements = {
		"delete from t_orders",
		"delete from t_items",
		"delete from t_wizards",
		"insert into t_wizards (wizard_name, wizard_dexterity, wizard_person) values ('Marius Black', 15, 'SQUIB')",
		"insert into t_wizards (wizard_name, wizard_dexterity, wizard_person) values ('Hermione', 100, 'MUDBLOOD')",
		"insert into t_items (item_id, item_name, item_quality, item_type) values (1L, '+5 Dexterity Vest', 20, 'MagicalItem')",
		"insert into t_items (item_id, item_name, item_quality, item_type) values (2L, 'Elixir of the Mongoose', 7, 'MagicalItem')",
		"insert into t_orders (ord_id, ord_wizard, ord_item) values (1,'Marius Black',2L)",
})
@AutoConfigureMockMvc

public class OllivandersTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired(required = false)
	Repositorio repo;
	
	@Autowired(required = false)
	Servicio servicio;

	@Autowired
	private MockMvc mockMvc;
									   
	@Autowired(required = false)
	Controlador controlador;

	/**
	 * Tests sobre los mappings
	 * 
	 * Observa el esquema de la base de datos que espera 
	 * la aplicacion en el fichero:
	 * src/main/resources/schema.sql
	 */
	
	// Completa la definicion y el mapping
	// de la clase MagicalItem a la tabla t_items
	// El id de esta clase ha de seguir una estrategia
	// Identity


	@Test
	public void test_mapping_normalItem() {
		MagicalItem elixir = em.find(MagicalItem.class, 2L);
		assertNotNull(elixir);
		assertEquals("Elixir of the Mongoose", elixir.getName());
		assertEquals(7, elixir.getQuality(), 0);  //item_quality
		assertEquals("MagicalItem", elixir.getType());
		assertEquals(2L, elixir.getId());
	}
	

	/** 
	 * Completa la definicion y el mapping
	 * de la clase Wizards a la tabla t_wizards
	 * 
	 * Los Wizards tiene una propiedad "person" de
	 * un tipo enumerado con los valores:
	 * MUGGLE, SQUIB, NOMAJ, MUDBLOOD
	 */
	@Test
	public void test_mapping_wizard() {
		Wizard squib = em.find(Wizard.class, "Marius Black");
		assertNotNull(squib);
		assertEquals("Marius Black", squib.getName());
		assertEquals(15, squib.getDexterity(), 0); //wizard_dexterity
		assertEquals("SQUIB", squib.getPerson().name());
	}

	// Completa la definicion y el mapping
	// de la clase Order a la tabla t_orders
	// El id de esta clase ha de seguir una estrategia
	// Identity
	@Test 
	public void test_mapping_order() {
		Order pedido = em.find(Order.class, 1L);
		assertNotNull(pedido);
		assertEquals("Marius Black", pedido.getWizard().getName());
		assertEquals("Elixir of the Mongoose", pedido.getItem().getName());
		}
	
	/**
	 * Crea una clase llamada Repositorio e indica
	 * que es un repositorio o componente de Spring 
	 */
	@Test
	public void test_repositorio_es_componente() {
		assertNotNull(repo);
	}

	/**
	 * Implementa el metodo loadWizard del repositorio
	 * que devuelve el mago/a con el nombre indicado
	 */
	@Test
	public void test_load_wizard() {
		assertNotNull(repo);
		Wizard squib = repo.loadWizard("Marius Black");
		assertNotNull(squib);
		assertEquals("Marius Black", squib.getName());
		assertEquals(15, squib.getDexterity());
		assertEquals("SQUIB", squib.getPerson().name());
	}

	/**
	 * Implementa el metodo loadItem() del repositorio
	 * que devuelve el Item con el nombre indicado
	 * 
	 * Ojo que el nombre del item no es la clave primaria
	 * y no podras usar el método find del Entity Manager.
	 * 
	 * El metodo devueve el primer item de la base de datos
	 * cuyo nombre coincida con el especificado.
	 */
	@Test
	public void test_load_item() {
		assertNotNull(repo);
		MagicalItem item = (MagicalItem) repo.loadItem("Elixir of the Mongoose");
		assertNotNull(item);
		assertEquals("Elixir of the Mongoose", item.getName());
		assertEquals(7, item.getQuality(), 0);
	}

	/**
	 * Implementa el metodo createItem() del repositorio
	 * que crea un item indicado en la base de datos.
	 * @throws NotEnoughDexterityException 
	 */
	@Test
	@Transactional
	public void test_create_item(){

		repo.createItem("Aged Brie", 2, "MagicalItem");

		MagicalItem brie = repo.loadItem("Aged Brie");
		assertNotNull(brie);
		assertEquals("Aged Brie",brie.getName());
		assertEquals(2, brie.getQuality(), 0);
		assertEquals("MagicalItem", brie.getType());
	}

	/**
	 * Implementa el metodo createItems() del repositorio
	 * que crea diversos items indicados en la base de datos.
	 * 
	 * Asegurate de que el metodo loadItem() anterior
	 * devueve el primer item cuyo nombre
	 * coincida con el especificado, sino, tu codigo
	 * devolvera uno de los pases backstage que no
	 * es el que buscamos.
	 */
	@Test
	@Transactional

	public void test_create_items() {

		List<MagicalItem> items = List.of(new MagicalItem("Aged Brie", 2, "MagicalItem"),
                                         new MagicalItem("Sulfuras, Hand of Ragnaros", 0, "MagicalItem"),
                                         new MagicalItem("Sulfuras, Hand of Ragnaros", -1, "MagicalItem"),
                                         new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 15, "MagicalItem"),
                                         new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 10, "MagicalItem"),
                                         new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 5, "MagicalItem"));
		
		repo.createItems(items);

		MagicalItem backstage = repo.loadItem("Backstage passes to a TAFKAL80ETC concert");
		assertNotNull(backstage);
		assertEquals("Backstage passes to a TAFKAL80ETC concert", backstage.getName());
		assertEquals(15, backstage.getQuality(), 0);
		assertEquals("MagicalItem", backstage.getType());
	}

	/**
	 * Implementa un servicio e indica
	 * que es un componente Spring.
	 */
	@Test
	public void test_listar_ordenes_wizard() {
        // Has de crear el servicio e indicar que es un componente Spring.
		assertNotNull(servicio);
	}

	/**
	 * Añade una clase controlador para hacer peticiones web
	 * a nuestra app. 
	 * Anotala para que sea un controlador de Spring.
     */
    @Test
    public void test_controlador() {
    	assertNotNull(controlador);
	}
	
	/**
     * La peticion /item/id del controlador
     * ha de retornar el nombre, la calidad y el tipo del item 
	 * indicado de la base de datos.
	 * 
	 * La consulta ha de redirigirse al servicio.
	 * El servicio utiliza el repositorio
	 * para hacer la consulta a la base de datos.
	 * No puedes llamar directamente al repositorio
	 * desde el controlador.
     */
    @Test
    public void test_get_item() throws Exception {

		mockMvc.perform(get("/item/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json("{name : 'Elixir of the Mongoose', quality: 7, type: 'MagicalItem'}"));
	}
	
	/**
     * Guarda un item empleando sólo el método POST en la url
     *    /item/create/
     * Los parametros post necesarios son:
	 *  "name" con el nombre del item
	 *  "quality" con la calidad del item
	 *  "type" con el tipo de item.
     * La peticion ha de retornar el texto "Saved" si el item ha sido guardado
	 * y "Not saved" en caso contrario.
	 * 
	 * La consulta ha de redirigirse al servicio.
	 * El servicio utiliza el repositorio
	 * para hacer la consulta a la base de datos.
	 * No puedes llamar directamente al repositorio
	 * desde el controlador.
     */
	@Test
    public void test_post() throws Exception {

		this.mockMvc.perform(post("/item/create")
		.param("name", "Aged Brie")
		.param("quality", "2")
		.param("type", "MagicalItem"))
		.andExpect(status().isOk())
		.andExpect(content().string("Saved"));

		// El item se ha guardado en la BBDD
		
		MagicalItem brie = em.find(MagicalItem.class, 3L);
		assertNotNull(brie);
		assertEquals("Aged Brie", brie.getName());
		assertEquals(2, brie.getQuality(), 0);
		assertEquals("MagicalItem", brie.getType());
		assertEquals(3L, brie.getId());
	}

	/**
     * Si el item no es del tipo MagicalItem
	 * no se guarda en la bbdd 
	 * y la peticion retorna el texto "Not saved"
     */
	@Test
    public void test_post_not_saved() throws Exception {

		this.mockMvc.perform(post("/item/create")
		.param("name", "Aged Brie")
		.param("quality", "2")
		.param("type", "Queso"))
		.andExpect(status().isOk())
		.andExpect(content().string("Not saved"));

		// El item no se ha guardado en la BBDD
		
		MagicalItem brie = em.find(MagicalItem.class, 3L);
		assertNull(brie);
	}

	
    /**
     * Asegurate de que en la URL /item/create
     * solo se reciben peticiones POST
     */
    @Test
    public void test_post_error() throws Exception {
 
		this.mockMvc.perform(get("/item/create")
		.param("name", "Aged Brie")
		.param("quality", "2")
		.param("type", "MagicalItem"))
		.andExpect(status().is4xxClientError());
    }
}

package co.edu.udea.rd.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.rd.bl.DispositivoBL;
import co.edu.udea.rd.dto.Dispositivo;
import co.edu.udea.rd.exception.MyException;
import co.edu.udea.rd.web.dto.DispositivoWS;

/**
 * Clase para el manejo de los servicios web relacionados con un dispositivo dentro 
 * la logica del negocio de el sistema.
 * 
 * @author Sergio Giraldo
 */
 
@Component
@Path("Dispositivo")
public class DispositivoService {

	/**
	 *  Inyección de dependencia del objeto que nos permitirá acceder a la lógica del negocio.
	 */
	@Autowired
	DispositivoBL dispositivoBL;
	
	/**
	 * Configuración del archivo .log que tendrá la información respecto al proyecto.
	 */ 
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Servicio encargado de guardar un nuevo Dispositivo en la lógica de nuestra aplicación.
	 * 
	 * @param idDispositivo identificador unico del dispositivo a guardar.
	 * @param idTipoDispositivo identificador acerca de que tipo de dispositivo es.
	 * @param disponibles se ingresa el numero de dispositivos nuevos de este tipo que se guardarán.
	 * @return mensaje informativo acerca de la transacción a realizar.
	 * @throws MyException.
	 */ 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("registrar")
	public String registrarDispositivo(@QueryParam("idDispositivo") String idDispositivo,
			@QueryParam("idTipoDispositivo") int idTipoDispositivo, @QueryParam("stock") int stock,
			@QueryParam("disponibles") int disponibles) {
		try {
			dispositivoBL.registrarDispositivo(idDispositivo, idTipoDispositivo, stock, disponibles);
		} catch (MyException e) {
			return e.getMessage();
		}
		return "Dispositivo creado correctamente";
	}

	/**
	 * Servicio encargado de actualizar la información de un dispositivo previamente almacenado en la BD.
	 * 
	 * @param idDispositivo nuevo id para el dispositivo.
	 * @param idTipoDispositivo actualiza el nuevo tipo de dispositivo.
	 * @param disponibles actualiza el stock disponible de dispositivos.
	 * @return mensaje informativo sobre el proceso de la transaccion.
	 * @throws MyException.
	 */ 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("modificar")
	public String modificarDispositivo(@QueryParam("idDispositivo") String idDispositivo,
			@QueryParam("idTipoDispositivo") int idTipoDispositivo, @QueryParam("stock") int stock,
			@QueryParam("disponibles") int disponibles) {
		try {
			dispositivoBL.modificarDispositivo(idDispositivo, idTipoDispositivo, stock, disponibles);
		} catch (MyException e) {
			return e.getMessage();
		}
		return "Dispositivo modificado correctamente";
	}
	
	/**
	 * Servicio que esta encargada de obtener la lista de todos los dispositivos 
	 * disponibles en la base de datos del sistema.
	 * 
	 * @return lista de todos los dispositivos almacenados en la BD.
	 * @throws MyException. 
	 */ 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listar")
	public List<DispositivoWS> listarDispositivos() {

		List<DispositivoWS> lista = new ArrayList<>();
		try {
			for (Dispositivo dispositivo : dispositivoBL.listarDispositivos()) {
				DispositivoWS dispositivoWS = new DispositivoWS();
				dispositivoWS.setIdDispositivo(dispositivo.getIdDispositivo());
				dispositivoWS.setIdTipoDispositivo(dispositivo.getTipoDispositivo().getIdTipoDispositivo());
				dispositivoWS.setStock(dispositivo.getStock());
				dispositivoWS.setDisponibles(dispositivo.getDisponibles());
				
				lista.add(dispositivoWS);
			}
		} catch (MyException e) {
			return null;
		}
		return lista;
	}
	
	/**
	 * Servicio encargado de eliminar un dispositivo de la BD del sistema.
	 * 
	 * @param idDispositivo identifica al dispositivo a eliminar.
	 * @return mensaje informativo acerca del proceso de la transacción.
	 * @throws MyException
	 */ 
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("eliminar")
	public String eliminarDispositivo(@QueryParam("idDispositivo") String idDispositivo){
		try {
			dispositivoBL.eliminarDispositivo(idDispositivo);
		} catch (MyException e) {
			return e.getMessage();
		}
		return "Dispositivo eliminado correctamente";
		
	}
	

}

package co.edu.udea.rd.web;

import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.rd.bl.PrestamoBL;
import co.edu.udea.rd.exception.MyException;

/**
 * Clase encargada para el manejo de los sericios web de el prestamo dentro de nuestra 
 * logica del negocio. 
 * 
 * @author Sergio Giraldo.
 */
 
@Component
@Path("Prestamo")
public class PrestamoService {

	
	/**
	 * Inyección de dependencias que nos permite acceder a la logica del negocio de prestamo.
	 */
	@Autowired
	PrestamoBL prestamoBL;
	
	/**
	 * Configuracion del archivo .log que guardara los mensajes sumergientes del sistema.
	 */
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Servicio encargado de actualizar el estado de un prestamo.
	 * 
	 * @param username del usuario que solicita el prestamo.
	 * @param idDispositivo que identifica cual será el dispositivo a prestar.
	 * @param estado del dispositivo, es decir, si está disponible o no para el prestamo.
	 * @retunr mensaje informativo acerca del proceso de la transaccion 
	 * @throws MyException.
	 */ 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("actualizarEstado")
	public String actualizarEstadoPrestamo(@QueryParam("username") String username,
			@QueryParam("idDispositivo") String idDispositivo, @QueryParam("estado") String estado) {
		try {
			prestamoBL.actualizarEstadoPrestamo(username, idDispositivo, estado);
		} catch (MyException e) {
			return e.getMessage();
		}
		return "Estado del prestamo modificado correctamente";
	}

	/**
	 * Servicio encargado de solicitar el prestamo de un dispositivo.
	 * 
	 * @param idDispositivo que se solicita para el prestamo.
	 * @param username del usuario que solicita el prestamo.
	 * @param fechaInicialPrestamo fecha para al cual el usuario solicita un prestamo.
	 * @param fechaFinalPrestamo fecha para la cual el usuario termina el rpestamo.
	 * @return Mensaje informativo acerca del proceso de la transacción.
	 * @trhows MyException
	 */ 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("solicitar")
	public String solicitarPrestamo(@QueryParam("idDipositivo") String idDipositivo,
			@QueryParam("username") String username, @QueryParam("fechaInicialPrestamo") Date fechaInicialPrestamo,
			@QueryParam("fechaFinalPrestamo") Date fechaFinalPrestamo) {
		try {
			prestamoBL.solicitarPrestamo(idDipositivo, username, fechaInicialPrestamo, fechaFinalPrestamo);
		} catch (MyException e) {
			return e.getMessage();
		}
		return "Prestamo solicitado correctamente";
	}
}

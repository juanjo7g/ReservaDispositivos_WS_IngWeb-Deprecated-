package co.edu.udea.rd.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.rd.bl.UsuarioBL;
import co.edu.udea.rd.exception.MyException;

@Component
@Path("Usuario")

public class UsuarioService {

	@Autowired
	UsuarioBL usuarioBL;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String autenticar(@QueryParam("username") String username, @QueryParam("password") String password) {
		try {
			if (!usuarioBL.loginUsuario(username, password)) {
				return "No valido";
			}

		} catch (MyException e) {
			return e.getMessage();
		}
		// return "Valido";
		return "";
	}
}

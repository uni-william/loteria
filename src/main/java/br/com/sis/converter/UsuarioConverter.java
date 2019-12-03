package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Usuario;
import br.com.sis.repository.UsuarioRepository;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario usuario = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			usuario = usuarioRepository.porId(id);
		}
		return usuario;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		return "";
	}

}

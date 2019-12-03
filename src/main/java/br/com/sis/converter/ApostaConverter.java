package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Aposta;
import br.com.sis.repository.ApostaRepository;

@FacesConverter(forClass = Aposta.class)
public class ApostaConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ApostaRepository apostaRepository;

	@Override
	public Aposta getAsObject(FacesContext context, UIComponent component, String value) {
		Aposta aposta = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			aposta = apostaRepository.porId(id);
		}
		return aposta;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Aposta aposta = (Aposta) value;
			return aposta.getId() == null ? null : aposta.getId().toString();
		}
		return "";
	}

}

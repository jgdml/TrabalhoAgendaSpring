package br.ifpr.agenda.dominio;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilterGeneral implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean active;


}

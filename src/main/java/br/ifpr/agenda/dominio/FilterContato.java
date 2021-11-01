package br.ifpr.agenda.dominio;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterContato extends FilterGeneral {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Usuario usuario;

}
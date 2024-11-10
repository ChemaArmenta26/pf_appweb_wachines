package entidades;

import entidades.Municipio;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-09T21:40:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Estado.class)
public class Estado_ { 

    public static volatile ListAttribute<Estado, Municipio> municipios;
    public static volatile SingularAttribute<Estado, Long> id;
    public static volatile SingularAttribute<Estado, String> nombre;

}
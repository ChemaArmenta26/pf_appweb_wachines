package entidades;

import entidades.Estado;
import entidades.Usuario;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-24T14:44:33", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Municipio.class)
public class Municipio_ { 

    public static volatile SingularAttribute<Municipio, Estado> estado;
    public static volatile SingularAttribute<Municipio, Long> id;
    public static volatile ListAttribute<Municipio, Usuario> usuarios;
    public static volatile SingularAttribute<Municipio, String> nombre;

}
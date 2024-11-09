package entidades;

import entidades.Comentario;
import entidades.Post;
import entidades.Usuario;
import java.util.Calendar;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-08T23:45:24", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Comentario.class)
public class Comentario_ { 

    public static volatile SingularAttribute<Comentario, Comentario> comentarioMayor;
    public static volatile SingularAttribute<Comentario, String> contenido;
    public static volatile ListAttribute<Comentario, Comentario> respuestas;
    public static volatile SingularAttribute<Comentario, Post> post;
    public static volatile SingularAttribute<Comentario, Calendar> fechaHora;
    public static volatile SingularAttribute<Comentario, Usuario> usuario;
    public static volatile SingularAttribute<Comentario, Long> id;

}
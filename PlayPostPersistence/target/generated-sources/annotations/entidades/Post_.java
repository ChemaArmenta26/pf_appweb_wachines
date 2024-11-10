package entidades;

import entidades.Comentario;
import entidades.Usuario;
import enums.TipoPost;
import java.util.Calendar;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-09T20:54:50", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Post.class)
public class Post_ { 

    public static volatile SingularAttribute<Post, String> contenido;
    public static volatile SingularAttribute<Post, TipoPost> tipo;
    public static volatile SingularAttribute<Post, String> imagenData;
    public static volatile SingularAttribute<Post, Calendar> fechaHoraCreacion;
    public static volatile SingularAttribute<Post, String> titulo;
    public static volatile SingularAttribute<Post, Usuario> usuario;
    public static volatile SingularAttribute<Post, Long> id;
    public static volatile ListAttribute<Post, Comentario> comentarios;

}
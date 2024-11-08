package auxiliar;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * La clase Encriptar proporciona métodos para encriptar y desencriptar texto utilizando el algoritmo AES.
 * Utiliza una clave secreta proporcionada para la encriptación y desencriptación.
 * 
 * @author Víctor Humberto Encinas Guzmán
 * @author José María Armenta Baca
 * @author José Ángel Huerta Amparán
 */
public class Encriptar {
    
    // Algoritmo utilizado para la encriptación y desencriptación.
    private static final String ALGORITMO = "AES";
    // Clave secreta utilizada para la encriptación y desencriptación.
    private static final String CLAVE_SECRETA = "Contra123";
    // Clave secreta generada a partir de la cadena de clave secreta proporcionada durante la inicialización.
    private SecretKeySpec clave;

    /**
     * Constructor que inicializa la clase EncriptarContra y genera la clave
     * secreta.
     */
    public Encriptar() {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] claveBytes = sha.digest(CLAVE_SECRETA.getBytes());
            claveBytes = Arrays.copyOf(claveBytes, 16);
            this.clave = new SecretKeySpec(claveBytes, ALGORITMO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encripta el texto plano utilizando la clave secreta y devuelve el resultado en formato Base64.
     *
     * @param textoPlano Texto que se desea encriptar.
     * @return Texto encriptado en formato Base64.
     * @throws Exception Si ocurre un error durante la encriptación.
     */
    public String encriptar(String textoPlano) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        byte[] bytesEncriptados = cipher.doFinal(textoPlano.getBytes());
        return Base64.getEncoder().encodeToString(bytesEncriptados);
    }

    /**
     * Desencripta el texto encriptado en formato Base64 utilizando la clave secreta y devuelve el texto plano original.
     *
     * @param textoEncriptado Texto encriptado en formato Base64.
     * @return Texto plano desencriptado.
     * @throws Exception Si ocurre un error durante la desencriptación.
     */
    public String desencriptar(String textoEncriptado) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, clave);
        byte[] bytesDesencriptados = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
        return new String(bytesDesencriptados);
    }

}

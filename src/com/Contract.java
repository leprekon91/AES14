
package com;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * <p>
 * Contract Constant Strings Manager holds: configuration locations +
 * communication language.
 */
public class Contract {
    final public static int DEFAULT_PORT = 5555;// Default fall-back port
    final public static String clientFXML = "/client/gui/fxml/"; // location of FXML files for the client
    final public static String serverFXML = "/server/gui/fxml/"; // location of FXML files for the server
    final public static String css = "/com/style/style.css"; // location of CSS file
    final public static String graphics = "/com/graphics/"; //location of graphics dir

    // Client-Server Communication Language
    final public static int AUTHORIZE = 2; //Autorize User in Message
    final public static int AUTH_YES = 1; // AUTHORIZE positive message
    final public static int AUTH_NO = 0; // AUTHORIZE negative message
    final public static int LOG_OFF = -1; // Logoff user


}


package com;

/**
 * @author Andrey Grabarnick
 * @email reist2009@gmail.com
 * @date 23 May 2018
 * 
 *       Contract Constant Strings Manager holds: configuration locations +
 *       communication language.
 */
public class Contract {
	final public static int DEFAULT_PORT = 5555;// Default fall-back port
	final public static String fxml = "/client/gui/fxml/"; // location of FXML files
	final public static String css = "/client/gui/style/style.css"; // location of CSS file
	final public static String graphics = "/client/gui/graphics/"; //location of graphics dir

	// Client-Server Communication Language
	final public static String GET_ALL_QUESTIONS = "Get All Questions";

}

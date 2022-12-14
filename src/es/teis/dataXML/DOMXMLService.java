/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author maria
 */
public class DOMXMLService implements IXMLService {

    /**
     * *
     *
     * @param ruta ruta al fichero XML que se leerá
     * @return ArrayList<Partido> que superan el umbral
     * @throws LecturaException en caso de que surja cualquier excepción durante
     *                          la lectura
     */
    @Override
    public ArrayList<Partido> leerPartidos(String ruta) throws LecturaException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void escribir(ArrayList<Partido> partidos, String ruta) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            //Crea un document con un elmento raiz
            Document document = implementation.createDocument(null, IXMLService.PARTIDOS_TAG, null);
            Element root = document.getDocumentElement();
            //completar ...
            for (Partido partido : partidos) {
                Element nPartido = document.createElement(PARTIDO_TAG);
                nPartido.setAttribute(PARTIDO_ATT_ID, String.valueOf(partido.getId()));
                addElementConTexto(document, nPartido, PARTIDO_VOTOS_PORC_TAG, String.valueOf(partido.getPorcentaje()));
                addElementConTexto(document, nPartido, PARTIDO_NOMBRE_TAG, partido.getNombre());

                root.appendChild(nPartido);
            }

            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            //Espacios para indentar cada línea
            fabricaTransformador.setAttribute("indent-number", 4);
            Transformer transformador = fabricaTransformador.newTransformer();
            //Insertar saltos de línea al final de cada línea
            //https://docs.oracle.com/javase/8/docs/api/javax/xml/transform/OutputKeys.html
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");

            //completar ...
            Source origen = new DOMSource(document);
            Result destino = new StreamResult(ruta);
            transformador.transform(origen, destino);

        } catch (TransformerConfigurationException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        } catch (TransformerException | ParserConfigurationException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
    }

    private static void addElementConTexto(Document document, Node padre, String tag, String text) {
        //Creamos un nuevo nodo de tipo elemento desde document
        Node node = document.createElement(tag);
        //Creamos un nuevo nodo de tipo texto también desde document
        Node nodeTexto = document.createTextNode(text);
        //añadimos a un nodo padre el nodo elemento
        padre.appendChild(node);
        //Añadimos al nodo elemento su nodo hijo de tipo texto
        node.appendChild(nodeTexto);
    }

}

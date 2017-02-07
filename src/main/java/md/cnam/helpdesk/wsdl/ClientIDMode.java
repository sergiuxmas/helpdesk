//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.06 at 05:18:26 PM EEST 
//


package md.cnam.helpdesk.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientIDMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ClientIDMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Inherit"/>
 *     &lt;enumeration value="AutoID"/>
 *     &lt;enumeration value="Predictable"/>
 *     &lt;enumeration value="Static"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClientIDMode", namespace = "http://schemas.datacontract.org/2004/07/System.Web.UI")
@XmlEnum
public enum ClientIDMode {

    @XmlEnumValue("Inherit")
    INHERIT("Inherit"),
    @XmlEnumValue("AutoID")
    AUTO_ID("AutoID"),
    @XmlEnumValue("Predictable")
    PREDICTABLE("Predictable"),
    @XmlEnumValue("Static")
    STATIC("Static");
    private final String value;

    ClientIDMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientIDMode fromValue(String v) {
        for (ClientIDMode c: ClientIDMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
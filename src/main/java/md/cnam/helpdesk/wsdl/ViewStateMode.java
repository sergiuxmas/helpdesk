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
 * <p>Java class for ViewStateMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ViewStateMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Inherit"/>
 *     &lt;enumeration value="Enabled"/>
 *     &lt;enumeration value="Disabled"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ViewStateMode", namespace = "http://schemas.datacontract.org/2004/07/System.Web.UI")
@XmlEnum
public enum ViewStateMode {

    @XmlEnumValue("Inherit")
    INHERIT("Inherit"),
    @XmlEnumValue("Enabled")
    ENABLED("Enabled"),
    @XmlEnumValue("Disabled")
    DISABLED("Disabled");
    private final String value;

    ViewStateMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ViewStateMode fromValue(String v) {
        for (ViewStateMode c: ViewStateMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.06 at 05:18:26 PM EEST 
//


package md.cnam.helpdesk.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_curentUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SIid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="_pageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wcfTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "curentUsername",
    "sIid",
    "pageName",
    "wcfTicket"
})
@XmlRootElement(name = "GetPlaceHolderMenu")
public class GetPlaceHolderMenu {

    @XmlElementRef(name = "_curentUsername", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> curentUsername;
    @XmlElement(name = "SIid")
    protected Integer sIid;
    @XmlElementRef(name = "_pageName", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> pageName;
    @XmlElementRef(name = "wcfTicket", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> wcfTicket;

    /**
     * Gets the value of the curentUsername property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCurentUsername() {
        return curentUsername;
    }

    /**
     * Sets the value of the curentUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCurentUsername(JAXBElement<String> value) {
        this.curentUsername = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sIid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSIid() {
        return sIid;
    }

    /**
     * Sets the value of the sIid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSIid(Integer value) {
        this.sIid = value;
    }

    /**
     * Gets the value of the pageName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPageName() {
        return pageName;
    }

    /**
     * Sets the value of the pageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPageName(JAXBElement<String> value) {
        this.pageName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the wcfTicket property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getWcfTicket() {
        return wcfTicket;
    }

    /**
     * Sets the value of the wcfTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setWcfTicket(JAXBElement<String> value) {
        this.wcfTicket = ((JAXBElement<String> ) value);
    }

}

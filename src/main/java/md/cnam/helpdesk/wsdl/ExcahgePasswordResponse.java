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
 *         &lt;element name="ExcahgePasswordResult" type="{http://schemas.datacontract.org/2004/07/WcfAutentificationService}WebAuthorization" minOccurs="0"/>
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
    "excahgePasswordResult"
})
@XmlRootElement(name = "ExcahgePasswordResponse")
public class ExcahgePasswordResponse {

    @XmlElementRef(name = "ExcahgePasswordResult", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<WebAuthorization> excahgePasswordResult;

    /**
     * Gets the value of the excahgePasswordResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link WebAuthorization }{@code >}
     *     
     */
    public JAXBElement<WebAuthorization> getExcahgePasswordResult() {
        return excahgePasswordResult;
    }

    /**
     * Sets the value of the excahgePasswordResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link WebAuthorization }{@code >}
     *     
     */
    public void setExcahgePasswordResult(JAXBElement<WebAuthorization> value) {
        this.excahgePasswordResult = ((JAXBElement<WebAuthorization> ) value);
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.29 at 09:03:38 PM EEST 
//


package edu.bonn.AMLGoldStandardGenerator.aml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Shall be used for InterfaceClass definition, provides base structures for an interface class definition.
 * 
 * <p>Java class for InterfaceClassType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterfaceClassType">
 *   &lt;complexContent>
 *     &lt;extension base="{}CAEXObject">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Attribute" type="{}AttributeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RefBaseClassPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceClassType", propOrder = {
    "attribute"
})
public class InterfaceClassType
    extends CAEXObject
{

    @XmlElement(name = "Attribute", required = true)
    protected List<AttributeType> attribute;
    @XmlAttribute(name = "RefBaseClassPath")
    protected String refBaseClassPath;

    /**
     * Gets the value of the attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType }
     * 
     * 
     */
    public List<AttributeType> getAttribute() {
        if (attribute == null) {
            attribute = new ArrayList<AttributeType>();
        }
        return this.attribute;
    }

    /**
     * Gets the value of the refBaseClassPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefBaseClassPath() {
        return refBaseClassPath;
    }

    /**
     * Sets the value of the refBaseClassPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefBaseClassPath(String value) {
        this.refBaseClassPath = value;
    }

}

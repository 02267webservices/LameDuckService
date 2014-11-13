
package dtu.ws.group8.lameduck.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cancelFlightRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cancelFlightRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightBookingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="creditCardInfo" type="{http://types.lameduck.group8.ws.dtu}creditCardInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancelFlightRequestType", propOrder = {
    "flightBookingNumber",
    "flightPrice",
    "creditCardInfo"
})
public class CancelFlightRequestType {

    @XmlElement(required = true)
    protected String flightBookingNumber;
    protected double flightPrice;
    @XmlElement(required = true)
    protected CreditCardInfoType creditCardInfo;

    /**
     * Gets the value of the flightBookingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightBookingNumber() {
        return flightBookingNumber;
    }

    /**
     * Sets the value of the flightBookingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightBookingNumber(String value) {
        this.flightBookingNumber = value;
    }

    /**
     * Gets the value of the flightPrice property.
     * 
     */
    public double getFlightPrice() {
        return flightPrice;
    }

    /**
     * Sets the value of the flightPrice property.
     * 
     */
    public void setFlightPrice(double value) {
        this.flightPrice = value;
    }

    /**
     * Gets the value of the creditCardInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardInfoType }
     *     
     */
    public CreditCardInfoType getCreditCardInfo() {
        return creditCardInfo;
    }

    /**
     * Sets the value of the creditCardInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardInfoType }
     *     
     */
    public void setCreditCardInfo(CreditCardInfoType value) {
        this.creditCardInfo = value;
    }

}

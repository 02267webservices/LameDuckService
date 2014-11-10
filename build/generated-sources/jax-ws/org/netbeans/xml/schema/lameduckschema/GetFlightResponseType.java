
package org.netbeans.xml.schema.lameduckschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getFlightResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getFlightResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightBookingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="flightReservationService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightFlightInfo" type="{http://xml.netbeans.org/schema/LameDuckSchema}flightInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFlightResponseType", propOrder = {
    "flightBookingNumber",
    "flightPrice",
    "flightReservationService",
    "flightFlightInfo"
})
public class GetFlightResponseType {

    @XmlElement(required = true)
    protected String flightBookingNumber;
    protected double flightPrice;
    @XmlElement(required = true)
    protected String flightReservationService;
    @XmlElement(required = true)
    protected FlightInfo flightFlightInfo;

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
     * Gets the value of the flightReservationService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightReservationService() {
        return flightReservationService;
    }

    /**
     * Sets the value of the flightReservationService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightReservationService(String value) {
        this.flightReservationService = value;
    }

    /**
     * Gets the value of the flightFlightInfo property.
     * 
     * @return
     *     possible object is
     *     {@link FlightInfo }
     *     
     */
    public FlightInfo getFlightFlightInfo() {
        return flightFlightInfo;
    }

    /**
     * Sets the value of the flightFlightInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlightInfo }
     *     
     */
    public void setFlightFlightInfo(FlightInfo value) {
        this.flightFlightInfo = value;
    }

}

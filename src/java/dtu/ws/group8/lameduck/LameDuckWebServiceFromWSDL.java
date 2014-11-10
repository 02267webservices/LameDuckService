/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ws.group8.lameduck;

import java.util.Vector;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.group8.lameduck.types.FlightDetails;
import dtu.ws.group8.lameduck.types.FlightInfoListType;
import dtu.ws.group8.lameduck.types.FlightInfoType;
//import dtu.ws.group8.lameduck.types.GetFlightRequestType;

/**
 *
 * @author gravr
 */
@WebService(serviceName = "LameDuckService", portName = "LameDuckPort", endpointInterface = "dtu.ws.group8.lameduck.LameDuckPortType", targetNamespace = "http://lameduck.group8.ws.dtu", wsdlLocation = "WEB-INF/wsdl/LameDuckWebServiceFromWSDL/LameDuckWrapper.wsdl")
public class LameDuckWebServiceFromWSDL {

    static private Vector<FlightInfoType> flightsFromDB = new  Vector<FlightInfoType>();
    static boolean gotFlightsFromDB = false;
    
    public dtu.ws.group8.lameduck.types.FlightInfoListType lameDuck(dtu.ws.group8.lameduck.types.GetFlightRequestType input) {
       
        if(!gotFlightsFromDB){
            getFlightsFromDB();
        }
        
        String start = input.getFlightStartAirport();
        String destination = input.getFlightDestinationAirport();
        XMLGregorianCalendar dateFlight = input.getFlightDate();

        FlightInfoListType returnListOfFlights = new FlightInfoListType();

        //search through the list of flights and build
        // the list of matching flights, and return it
        for(FlightInfoType fits:flightsFromDB){

            if(start.equals(fits.getFlightInfo().getStartAirport()) &&
               destination.equals(fits.getFlightInfo().getDestinationAirport()) &&
               dateFlight.equals(fits.getFlightInfo().getLiftOffDate())
                    ){
                returnListOfFlights.getFlightInformation().add(fits);
            }

        }

        return returnListOfFlights;
    }
    
    private void getFlightsFromDB() {

        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2015-01-01");

        FlightInfoType flightInfo1 = new FlightInfoType();
        
        flightInfo1.setFlightBookingNumber("ABC1234");
        flightInfo1.setFlightPrice(4995.95);
        flightInfo1.setFlightReservationService("Flight Reservation Service INC");
        
        FlightDetails flight1 = new FlightDetails();
        flight1.setStartAirport("Copenhagen");
        flight1.setDestinationAirport("Berlin");
        flight1.setCarrierName("SAS");
        flight1.setLiftOffDate(date);
        flight1.setLandingDate(date);
        flightInfo1.setFlightInfo(flight1);

        flightsFromDB.add(flightInfo1);
        
        gotFlightsFromDB = true;
    }
    
}

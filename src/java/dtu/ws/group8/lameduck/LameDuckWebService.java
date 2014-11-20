/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ws.group8.lameduck;

import dk.dtu.imm.BankService;
import dk.dtu.imm.CreditCardFaultMessage;
import dk.dtu.imm.AccountType;
import dk.dtu.imm.CreditCardInfoType;
import dk.dtu.imm.ExpirationDateType;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.group8.lameduck.types.FlightDetails;
import dtu.ws.group8.lameduck.types.FlightInfoListType;
import dtu.ws.group8.lameduck.types.FlightInfoType;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author gravr
 */
@WebService(serviceName = "LameDuckService", portName = "LameDuckPort", endpointInterface = "dtu.ws.group8.lameduck.LameDuckWSDLPortType", targetNamespace = "http://lameduck.group8.ws.dtu", wsdlLocation = "WEB-INF/wsdl/LameDuckWebService/LameDuckWSDL.wsdl")
public class LameDuckWebService {
    //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service = new BankService();
    
    static private ArrayList<FlightInfoType> flightsFromDB = new  ArrayList<FlightInfoType>();
    static private ArrayList<FlightInfoType> bookedFlights = new  ArrayList<FlightInfoType>();
    static boolean gotFlightsFromDB = false;


    public dtu.ws.group8.lameduck.types.FlightInfoListType getFlights(dtu.ws.group8.lameduck.types.GetFlightRequestType input) {
        
        if(!gotFlightsFromDB){
            getFlightsFromDB();
        }
        
        String start = input.getFlightStartAirport();
        String destination = input.getFlightDestinationAirport();
        XMLGregorianCalendar dateFlight = input.getFlightDate();

        FlightInfoListType returnListOfFlights = new FlightInfoListType();

       for(FlightInfoType flight:flightsFromDB){
            if(start.equals(flight.getFlightInfo().getStartAirport()) &&
               destination.equals(flight.getFlightInfo().getDestinationAirport()) &&
               dateFlight.equals(flight.getFlightInfo().getLiftOffDate())
                    ){
                returnListOfFlights.getFlightInformation().add(flight);
            }

        }

        return returnListOfFlights;
    }

    public boolean bookFlight(dtu.ws.group8.lameduck.types.BookFlightRequestType input) throws BookFlightFault {
        double flightPrice = -1;
        //get price of flight
        for(FlightInfoType flight : flightsFromDB)
            if(flight.getFlightBookingNumber().equals(input.getFlightBookingNumber())){
                bookedFlights.add(flight);
                flightPrice = (double) flight.getFlightPrice();
                break;
            }
        if (flightPrice==-1){
            throw new BookFlightFault("Booking number not found", null);
        }
        
        
        dtu.ws.group8.lameduck.types.CreditCardInfoType cardInfo = input.getCreditCardInfo();
        CreditCardInfoType cardInfoImmFormat = new CreditCardInfoType();
        cardInfoImmFormat.setName(cardInfo.getName());
        cardInfoImmFormat.setNumber(cardInfo.getCardNumber());
            
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(cardInfo.getExpiryDate().getMonth());
        expDate.setYear(cardInfo.getExpiryDate().getYear()% 10);
        
        System.out.println("card month: " +cardInfo.getExpiryDate().getMonth() + "card year: " +cardInfo.getExpiryDate().getYear());    
        cardInfoImmFormat.setExpirationDate(expDate);
        
        AccountType accType = new AccountType();
        accType.setName("LameDuck");
        accType.setNumber("50208812");
        
        
        try {
            
            boolean validate = chargeCreditCard(8,cardInfoImmFormat,(int)flightPrice,accType);
            System.out.println("chargeCreditCard: " +validate);
            if(validate == false)
                throw new CancelFlightFault("FastMoney Failed", null);
        }catch (Exception ex) {
             System.out.println("chargeCreditCard: " +ex.getMessage().toString());
             return false;
        }
        return true;
    }

    public boolean cancelFlight(dtu.ws.group8.lameduck.types.CancelFlightRequestType input) throws CancelFlightFault {
        boolean foundFlight = false;
        double flightPrice = -1;
        
        //check if flight exsists (not a requirement)
        for(FlightInfoType flight : bookedFlights)
            if(flight.getFlightBookingNumber().equals(input.getFlightBookingNumber())){
                bookedFlights.remove(flight);
                flightPrice = (double) flight.getFlightPrice();
                foundFlight = true;
                break;
            }
        if (!foundFlight){
            throw new CancelFlightFault("Booking number not found", null);
        }
        
        //try to refund half of price of flight via fastmoney
        try {
            //Should have used dk.dtu.imm.CreditCardInfoType in CancelFlightRequestType instead of dtu.ws.group8.lameduck.types.CreditCardInfoType
            //solution: http://stackoverflow.com/questions/2079823/importing-two-classes-with-same-name-how-to-handle
            
            dtu.ws.group8.lameduck.types.CreditCardInfoType cardInfo = input.getCreditCardInfo();
            CreditCardInfoType cardInfoImmFormat = new CreditCardInfoType();
            cardInfoImmFormat.setName(cardInfo.getName());
            cardInfoImmFormat.setNumber(cardInfo.getCardNumber());
            
            ExpirationDateType expDate = new ExpirationDateType();
            expDate.setMonth(cardInfo.getExpiryDate().getMonth());
            expDate.setYear(cardInfo.getExpiryDate().getYear()% 10);
            
            cardInfoImmFormat.setExpirationDate(expDate);
            
            AccountType accType = new AccountType();
            accType.setName("LameDuck");
            accType.setNumber("50208812");
            
            boolean refundSuccess = refundCreditCard(8,cardInfoImmFormat,(int)flightPrice/2,accType);
            
            if(refundSuccess == false)
                throw new CancelFlightFault("FastMoney Failed", null);
             
            
        }catch (Exception ex) {
            throw new CancelFlightFault("FastMoney Failed", null);
        }
        return true;
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

    private boolean chargeCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }

    private boolean refundCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    
}

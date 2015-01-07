package univ.evector.shared.formatter;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.Address;
import univ.evector.beans.Country;
import univ.evector.beans.Municipality;
import univ.evector.beans.Residence;
import univ.evector.beans.Street;

public class AddressFormatter {

    public static String toAddressCellFormat(Address address) {
        return toString(address);
    }

    /**
     * DĖL ADRESŲ FORMAVIMO TAISYKLIŲ PATVIRTINIMO
     * http://www.infolex.lt/lite/ta/111602
     */
    public static String toString(Address address) {
        StringBuilder text = new StringBuilder();
        if (address != null) {
            String country = countryAsString(address.getCountry());
            String municipality = municipalityAsString(address.getMunicipality());
            String residence = residenceAsString(address.getResidence());
            String street = streetAsString(address.getStreet());
            String building_no = address.getBuilding_no();
            String house_no = address.getHouse_no();
            String room_no = address.getRoom_no();

            StringFormatter.append(text, ", ", country, municipality, residence, street);
            StringFormatter.append(text, " ", building_no);
            StringFormatter.append(text, " ", formatHouseNo(house_no));
            StringFormatter.append(text, "-", room_no);
        }
        return text.toString();
    }

    private static String countryAsString(Country country) {
        String retVal = "";
        if (country != null && !ConversionHelper.isEmpty(country.getCnt_name())) {
            retVal = country.getCnt_name();
        }
        return retVal;
    }

    private static String municipalityAsString(Municipality municipality) {
        String retVal = "";
        if (municipality != null && !ConversionHelper.isEmpty(municipality.getMncp_name())) {
            retVal = municipality.getMncp_name();
        }
        return retVal;
    }

    private static String residenceAsString(Residence residence) {
        String retVal = "";
        if (residence != null && !ConversionHelper.isEmpty(residence.getRsd_name())) {
            retVal = residence.getRsd_name();
        }
        return retVal;
    }

    private static String streetAsString(Street street) {
        String retVal = "";
        if (street != null && !ConversionHelper.isEmpty(street.getStr_name())) {
            retVal = street.getStr_name();
        }
        return retVal;
    }

    private static String formatHouseNo(String houseNo) {
        if (null != houseNo) {
            if (houseNo.matches("\\d*")) {
                houseNo = "K" + houseNo;
            }
        }
        return houseNo;
    }

}

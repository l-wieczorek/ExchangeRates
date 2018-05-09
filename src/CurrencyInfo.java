import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CurrencyInfo {

    private static double ret = 0;

    public static List<Double> getExchangeRate(String code, String fromDate, String toDate){
        String uri = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+fromDate+"/"+toDate;
        List<Double> currRateList = new ArrayList<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(connection(uri));
            NodeList rootNodes = doc.getElementsByTagName("Rate");
            for(int i=0 ; i<rootNodes.getLength(); i++){
                Node rates = rootNodes.item(i);
                if(rates.getNodeType() == Node.ELEMENT_NODE){
                    NodeList ratesList = rates.getChildNodes();
                    Node rate = ratesList.item(ratesList.getLength()-1);
                    if(rate.getNodeType() == Node.ELEMENT_NODE){
                        Element currencyElement = (Element) rate;
                        String currencyData = currencyElement.getTextContent();
                        currRateList.add(Double.parseDouble(currencyData));
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currRateList;
    }

    public static double averageRate(List<Double>exRate){
        double averageVal = 0;
        for (Double d : exRate){
            averageVal = averageVal + d;
        }
        ret = averageVal/exRate.size();
        ret *= 10000;
        ret = Math.round(ret);
        ret /= 10000;
        return ret;
    }

    public static double standardDeviation(List<Double>exRate, double average){
        double deviation = 0;
        for (Double d : exRate){
            deviation = deviation + pow((d-average),2);
        }
        ret = sqrt(deviation/exRate.size());
        ret *= 10000;
        ret = Math.round(ret);
        ret /= 10000;
        return ret;
    }

    public static InputStream connection(String uri) throws IOException {
        URL xmlUrl = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) xmlUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        InputStream xml = conn.getInputStream();
        return xml;
    }
}

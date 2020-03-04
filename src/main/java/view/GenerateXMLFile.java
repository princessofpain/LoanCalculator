package main.java.view;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import main.java.model.Rate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateXMLFile {
	public void generateXML(String type, Rate[] calculation) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("LoanRepayment");
			doc.appendChild(rootElement);
			String loanType = "abc"; //calculation.[0].getLoanType();
			rootElement.setAttribute("type", loanType);
			DecimalFormat df = new DecimalFormat("0.00");

			for(Rate monthlyCalculation: calculation) {
				Element month = doc.createElement("month");
				rootElement.appendChild(month);
				int currentIndex = Arrays.asList(calculation).indexOf(monthlyCalculation) + 1;
				month.setAttribute("id", String.valueOf(currentIndex));

				Element rest = doc.createElement("rest");
				month.appendChild(rest);
				rest.setTextContent(df.format(monthlyCalculation.getRestBefore()));

				Element rate = doc.createElement("rate");
				month.appendChild(rate);
				rate.setTextContent(df.format(monthlyCalculation.getRate()));

				Element interest = doc.createElement("interest");
				month.appendChild(interest);
				interest.setTextContent(df.format(monthlyCalculation.getInterest()));

				Element totalPay = doc.createElement("totalPay");
				month.appendChild(totalPay);
				totalPay.setTextContent(df.format(monthlyCalculation.getTotal()));

				Element newRest = doc.createElement("newRest");
				month.appendChild(newRest);
				newRest.setTextContent(df.format(monthlyCalculation.getRestAfter()));
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\csperansky\\eclipse-workspace\\newExercises\\loanCalculation\\" + loanType + "LoanCalculation.xml"));

			transformer.transform(source, result);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
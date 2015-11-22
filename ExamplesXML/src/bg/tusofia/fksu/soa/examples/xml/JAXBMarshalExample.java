package bg.tusofia.fksu.soa.examples.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import bg.tusofia.fksu.soa.examples.xml.jaxb.Cinema;
import bg.tusofia.fksu.soa.examples.xml.jaxb.MovieType;
import bg.tusofia.fksu.soa.examples.xml.jaxb.MovieType.Length;

public class JAXBMarshalExample {

	public static void main(String[] args) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Cinema.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // pretty_print

		Length length = new Length();
		length.setUnit("min");
		length.setValue((short) 142);

		MovieType movie = new MovieType();
		movie.setRating(9.3f);
		movie.setSynopsis("Two imprisoned men bond over ...");
		movie.setTitle("The Shawshank Redemption");
		movie.setLength(length);

		Cinema cinema = new Cinema();
		cinema.getMovie().add(movie);

		jaxbMarshaller.marshal(cinema, System.out);

	}
}

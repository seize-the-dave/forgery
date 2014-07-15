package uk.co.adaptivelogic.forgery;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

@Property("firstName")
public class FirstNameStringForger extends AbstractForger<String> {
	private static final Logger LOGGER = LoggerFactory.getLogger(FirstNameStringForger.class);

	//TODO: add Locale parameter?
	@Override
	public String forge() {
		String name = "John";

		//TODO is this a DynamicForger rather than a StaticForger? or a RandomForger?
		//TODO can we make the DataSource be static, random etc? by having a getNextValue() method
		//have a default strategy?
		//can override strategy for a datasource?

		Optional<ForgerDataSource<String>> dataSource = getServiceLocator().lookupDataSource("firstNames");

		if (dataSource.isPresent()) {
			//TODO determine locale at forge time?
			Set<String> firstNames = dataSource.get().getValues(Locale.ENGLISH);
			LOGGER.debug("data source firstNames: {}", name);

			//TODO: choose a random value from Set<T>
			int pos = new Random().nextInt(firstNames.size());
			int i = 0;
			for (String firstName : firstNames) {
				if (i++ == pos) {
					name = firstName;
					break;
				}
			}
		}

		LOGGER.debug("chosen: {}", name);

		return name;
	}
}

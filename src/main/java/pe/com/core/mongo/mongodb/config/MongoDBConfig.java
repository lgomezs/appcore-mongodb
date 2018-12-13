package pe.com.core.mongo.mongodb.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import pe.com.core.mongo.mongodb.exception.CoreException;



public abstract class MongoDBConfig extends AbstractMongoConfiguration {

	private static final String	COMMA	= ",";
	private static final String	COLON	= ":";

	private MongoDbProperties	mongoDbProperties;
	
	
	@Override
	protected String getUri() throws Exception {
		if (mongoDbProperties != null) {
			return mongoDbProperties.getUri();
		}	
		throw new Exception("Error in get getUri()");	
	}

	@Override
	protected String getDatabaseName() throws Exception {
		if (mongoDbProperties != null) {
			return mongoDbProperties.getDatabaseName();
		}	
		throw new Exception("Error in get getDatabaseName()");	
	}

	@Override
	public Mongo mongo() throws Exception {
		if (mongoDbProperties != null) {
			final MongoClientOptions options = MongoClientOptions.builder().socketKeepAlive(mongoDbProperties.getKeepAlive()).connectionsPerHost(mongoDbProperties.getConnections()).writeConcern(
					WriteConcern.ACKNOWLEDGED).build();

			if (mongoDbProperties.getSecurityMechanism() == 1) {
				final String salt = new String(IOUtils.toByteArray(mongoDbProperties.getSalt()), StandardCharsets.UTF_8);
				final String key = new String(IOUtils.toByteArray(mongoDbProperties.getKey()), StandardCharsets.UTF_8);

				final String usernameEncrypted = new String(IOUtils.toByteArray(mongoDbProperties.getUsername()), StandardCharsets.UTF_8);
				final String passwordEncrypted = new String(IOUtils.toByteArray(mongoDbProperties.getPassword()), StandardCharsets.UTF_8);
				final String sourceEncrypted = new String(IOUtils.toByteArray(mongoDbProperties.getSource()), StandardCharsets.UTF_8);

				final String username = decrypt(usernameEncrypted, key, salt);
				final String password = decrypt(passwordEncrypted, key, salt);
				final String source = decrypt(sourceEncrypted, key, salt);

				final MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, source, password.toCharArray());
				return new MongoClient(seeds(mongoDbProperties.getHosts()), Arrays.asList(mongoCredential), options);
			} else {
				final String username = mongoDbProperties.getUsername();
				final String password = mongoDbProperties.getPassword();
				final String source = mongoDbProperties.getSource();

				final MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, source, password.toCharArray());
				return new MongoClient(seeds(mongoDbProperties.getHosts()), Arrays.asList(mongoCredential), options);
			}
		}
		throw new CoreException("Error en mongo()");
	}

	
	public String decrypt(String usernameEncrypted, String key,String salt) {
		return usernameEncrypted;
	}
	
	private List<ServerAddress> seeds(final String seeds) {
		if (!seeds.isEmpty()) {
			final List<ServerAddress> seedList = new ArrayList<>();

			final String[] addresses = seeds.split(COMMA);
			for (String address : addresses) {
				final String[] hostAndPort = address.split(COLON);
				seedList.add(new ServerAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
			}
			return seedList;
		}
		throw new CoreException("Error in seeds()");
	}	
	
	public MongoDbProperties getProperties() {
		return mongoDbProperties;
	}

	public void setProperties(MongoDbProperties mongoDbProperties) {
		this.mongoDbProperties = mongoDbProperties;
	}
	
}

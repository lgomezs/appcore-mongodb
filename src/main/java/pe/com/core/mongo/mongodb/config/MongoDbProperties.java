package pe.com.core.mongo.mongodb.config;

import pe.com.core.mongo.mongodb.security.CredentialsProperties;

public class MongoDbProperties extends CredentialsProperties{

	private String	databaseName;
	private String	hosts;
	private String	source;
	private Boolean	keepAlive;
	private Integer	connections;	
	private String	uri;
	
	
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getKeepAlive() {
		if (keepAlive == null) {
			keepAlive = false;
		}
		return keepAlive;
	}

	public void setKeepAlive(Boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Integer getConnections() {
		if (connections == null) {
			connections = 100;
		}
		return connections;
	}

	public void setConnections(Integer connections) {
		this.connections = connections;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}

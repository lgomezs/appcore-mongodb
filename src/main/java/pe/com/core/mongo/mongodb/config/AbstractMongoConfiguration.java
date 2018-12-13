package pe.com.core.mongo.mongodb.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mapping.context.MappingContextIsNewStrategyFactory;
import org.springframework.data.mapping.model.CamelCaseAbbreviatingFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.support.CachingIsNewStrategyFactory;
import org.springframework.data.support.IsNewStrategyFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.mongodb.ConnectionString;
import com.mongodb.Mongo;

public abstract class AbstractMongoConfiguration {

	protected abstract String getUri() throws Exception;
	
	protected abstract String getDatabaseName() throws Exception;
		
	
	@Deprecated
	protected String getAuthenticationDatabaseName() {
		return null;
	}
	
	public abstract Mongo mongo() throws Exception;

	public ReactiveMongoDatabaseFactory mongoDbFactory() throws Exception {
		ConnectionString _conecctionString = new ConnectionString(getUri());
		return new SimpleReactiveMongoDatabaseFactory(_conecctionString);
	}
	
	public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
		return new ReactiveMongoTemplate(mongoDbFactory());
	}


	@Deprecated
	protected String getMappingBasePackage() {
		Package mappingBasePackage = getClass().getPackage();
		return mappingBasePackage == null ? null : mappingBasePackage.getName();
	}

	protected Collection<String> getMappingBasePackages() {
		return Collections.singleton(getMappingBasePackage());
	}

	
	@Deprecated
	protected UserCredentials getUserCredentials() {
		return null;
	}

	
	@Bean
	public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setInitialEntitySet(getInitialEntitySet());
		mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
		mappingContext.setFieldNamingStrategy(fieldNamingStrategy());
		return mappingContext;
	}

	
	@Bean
	public IsNewStrategyFactory isNewStrategyFactory() throws ClassNotFoundException {
		return new CachingIsNewStrategyFactory(new MappingContextIsNewStrategyFactory(mongoMappingContext()));
	}

	
	@Bean
	public CustomConversions customConversions() {
		return new CustomConversions(Collections.emptyList());
	}
	
	protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
		Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();
		for (String basePackage : getMappingBasePackages()) {
			initialEntitySet.addAll(scanForEntities(basePackage));
		}
		return initialEntitySet;
	}

	
	protected Set<Class<?>> scanForEntities(String basePackage) throws ClassNotFoundException {
		if (!StringUtils.hasText(basePackage)) {
			return Collections.emptySet();
		}
		Set<Class<?>> initialEntitySet = new HashSet<>();
		if (StringUtils.hasText(basePackage)) {
			ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(false);
			componentProvider.addIncludeFilter(new AnnotationTypeFilter(Document.class));
			componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));
			for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {
				initialEntitySet.add(ClassUtils.forName(candidate.getBeanClassName(), AbstractMongoConfiguration.class.getClassLoader()));
			}
		}
		return initialEntitySet;
	}

	
	protected boolean abbreviateFieldNames() {
		return false;
	}
	
	protected FieldNamingStrategy fieldNamingStrategy() {
		return abbreviateFieldNames() ? new CamelCaseAbbreviatingFieldNamingStrategy() : PropertyNameFieldNamingStrategy.INSTANCE;
	}
	
}

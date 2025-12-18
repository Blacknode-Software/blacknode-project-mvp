package software.blacknode.backend.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;

import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.huid.json.deserializer.JacksonHuidDeserializer;
import me.hinsinger.hinz.common.huid.json.serializer.JacksonHuidSerializer;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import me.hinsinger.hinz.common.time.timestamp.json.deserializer.JacksonTimestampDeserializer;
import me.hinsinger.hinz.common.time.timestamp.json.serializer.JacksonTimestampSerializer;

@Configuration
public class JacksonConfig {
	
    @Bean
    public SimpleModule hinzLibrariesModule() {
    	
        SimpleModule module = new SimpleModule();
        module.addSerializer(HUID.class, new JacksonHuidSerializer());
        module.addDeserializer(HUID.class, new JacksonHuidDeserializer());
        
        module.addSerializer(Timestamp.class, new JacksonTimestampSerializer());
        module.addDeserializer(Timestamp.class, new JacksonTimestampDeserializer());
        
        return module;
    }
    
}

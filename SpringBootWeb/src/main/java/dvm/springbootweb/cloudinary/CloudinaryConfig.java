package dvm.springbootweb.cloudinary;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djknejf5i",
                "api_key", "612448547275481",
                "api_secret", "wzIMvKlkfBIJJWCJX9Qa1LzjsZk",
                "secure", true
        ));
        return cloudinary;
    }
}

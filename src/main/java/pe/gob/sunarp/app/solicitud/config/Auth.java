package pe.gob.sunarp.app.solicitud.config;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Map;

@Service
public class Auth {
	private static final Logger LOG = LoggerFactory.getLogger(Auth.class);
	
	@Autowired(required = false)
	private TokenStore tokenstore;

	public AuthModel usuario() {
		String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest()
				.getHeader("Authorization")
				.replace("Bearer ", "")
				.trim();
		Map<String, Object> details = tokenstore.readAccessToken(token).getAdditionalInformation();	
		JSONObject jsonObj = new JSONObject(details);
		AuthModel usuario = new AuthModel();
		usuario.setUserName(getOrNull(jsonObj, "user_name"));
		usuario.setJti(getOrNull(jsonObj, "jti"));

		return usuario;

	}

	private static String getOrNull(JSONObject jsonObj, String key) {
		return jsonObj.optString(key, null);
	}

	public Map<String, Object> getDetail(OAuth2Authentication authentication) {
		OAuth2AuthenticationDetails oauth2authenticationdetails = (OAuth2AuthenticationDetails) authentication.getDetails();
		return tokenstore.readAccessToken(oauth2authenticationdetails.getTokenValue()).getAdditionalInformation();
	}
 
}

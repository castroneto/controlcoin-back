package coltrolcoin.middlewares;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import coltrolcoin.services.UserDetailSercurityService;
import coltrolcoin.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailSercurityService userDetails;
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";

	public AuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailSercurityService userDetails) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetails = userDetails;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if(this.tokenValidator(request)) {
				
				
				String token = this.getToken(request);
				
				if(this.jwtTokenUtil.validateExpirationToken(token)) {
					
				}
				
				UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
			} else {
				SecurityContextHolder.clearContext();
				filterChain.doFilter(request, response);
				return;
			}
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}
	
	
	private String getToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return jwtToken;
	}
	
	private boolean tokenValidator(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

	
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

        String email = this.jwtTokenUtil.decodeToken(token).getSubject();

        if (email == null) {
            return null;
        }
        
        UserDetails userDetails = this.userDetails.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails,null, new ArrayList<>());
    }

}

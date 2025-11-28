package ml.finances.caisff.eboutique.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ml.finances.caisff.eboutique.entities.LoggingRequest;
import ml.finances.caisff.eboutique.repositories.LoggingRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Component
@Order(1)
public class LoginFilter extends OncePerRequestFilter {

    private Logger logger= LoggerFactory.getLogger(LoginFilter.class);

    private final LoggingRequestRepository loggingRequestRepository;

    @Value("${ml.caisff.eboutique.allowedIps}")
    private List<String> allowedIps;

    public LoginFilter(LoggingRequestRepository loggingRequestRepository) {
        this.loggingRequestRepository = loggingRequestRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("demarrage login Filter");
        String ipAddress = request.getRemoteAddr();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        Date debutDate = new Date();
        LoggingRequest loggingRequest=LoggingRequest.builder()
                .ip(ipAddress)
                .uri(requestURI)
                .method(method)
                .debut(debutDate)
                .build();
        loggingRequestRepository.save(loggingRequest);
        boolean autorisee=false;
        for (String allowedIp : allowedIps) {
            if (allowedIp.equals(ipAddress)) {
                autorisee=true;
            }
        }
        if (!autorisee) {
            loggingRequest.setStatus(470);
            loggingRequestRepository.save(loggingRequest);
            response.setStatus(470);
            PrintWriter writer = response.getWriter();
            writer.write("Votre adresse ip n'est pas autorisée a acceder a ce service");
            writer.flush();
            return;
        }
        logger.info("parametre requete {}",loggingRequest);

        filterChain.doFilter(request, response);

        int statusCode = response.getStatus();
        //retour du controller
        Date finDate = new Date();
        loggingRequest.setFin(finDate);
        loggingRequest.setStatus(statusCode);
        logger.info("parametre reponse "+loggingRequest);
        loggingRequestRepository.save(loggingRequest);
        if (statusCode >=400) {
            logger.error("erreur de la requete "+loggingRequest);
        }
    }
}

package vn.huydtg.immunizationservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.huydtg.immunizationservice.domain.ImmunizationUnit;
import vn.huydtg.immunizationservice.repository.ImmunizationUnitRepository;
import vn.huydtg.immunizationservice.security.SecurityUtils;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class ImmunizationUnitActivityFilter extends OncePerRequestFilter {

    private final ImmunizationUnitRepository immunizationUnitRepository;

    public ImmunizationUnitActivityFilter(ImmunizationUnitRepository immunizationUnitRepository) {
        this.immunizationUnitRepository = immunizationUnitRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        UUID uuid = SecurityUtils.getImmunizationUnitId();
        if (uuid != null) {
            Optional<ImmunizationUnit> immunizationUnit = immunizationUnitRepository.findById(uuid);
            Boolean isActive = false;
            if (immunizationUnit.isPresent()){
                isActive = immunizationUnit.get().getIsActive();
            }
            if (!isActive) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"immunization_unit_is_inactive\", \"path\": \"/error\"}");
                return;

            }
        }

        filterChain.doFilter(request, response);
    }

}

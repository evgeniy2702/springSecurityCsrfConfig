package zhurenko.ua.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import zhurenko.ua.model.Owner;
import zhurenko.ua.service.OwnerService;

@Component
public class OwnerDetailsService implements UserDetailsService {

    private OwnerService ownerService;

    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner =ownerService.getOwnerByUsername(username);
        if(owner == null){
            throw new UsernameNotFoundException(String.format("Owner was not found with %s username",username));
        }
        return new OwnerDetails(owner);
    }
}

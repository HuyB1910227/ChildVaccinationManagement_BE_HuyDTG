package vn.huydtg.immunizationservice.service;


import vn.huydtg.immunizationservice.web.rest.vm.JWTAuthResponse;
import vn.huydtg.immunizationservice.web.rest.vm.LoginVM;

public interface AuthService {


    JWTAuthResponse login(LoginVM loginVM);


}

package app.controller.impl;

import app.dto.LoginDTO;
import app.dto.LoginReturnDTO;
import app.model.user.*;
import app.security.TokenUtils;
import app.service.*;
import app.service.impl.FilterUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth/users")
public class UserControllerImpl {
    @Autowired
    private FilterUserDetailsService filterUserDetailsService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    public UserControllerImpl(){
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<LoginReturnDTO> logIn(@RequestBody LoginDTO loginDTO) {
        LoginReturnDTO loginReturnDTO = filterUserDetailsService.getUserForLogIn(loginDTO);
        if(loginReturnDTO != null){
            String jwt = tokenUtils.generateToken(loginReturnDTO.getEmail(), loginReturnDTO.getId(), loginReturnDTO.getType());
            loginReturnDTO.setJwtToken(jwt);
            return new ResponseEntity<>(loginReturnDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package com.fundoo.fundoonotes.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fundoo.fundoonotes.dto.request.LoginReqDTO;
import com.fundoo.fundoonotes.dto.request.RegisterReqDTO;
import com.fundoo.fundoonotes.dto.response.ResponseDTO;
import com.fundoo.fundoonotes.exception.UserException;
import com.fundoo.fundoonotes.model.PasswordResetToken;
import com.fundoo.fundoonotes.model.User;
import com.fundoo.fundoonotes.repository.PasswordResetTokenRepository;
import com.fundoo.fundoonotes.repository.UserRepository;
import com.fundoo.fundoonotes.service.RedisService;
import com.fundoo.fundoonotes.service.UserService;
import com.fundoo.fundoonotes.util.JwtUtil;


@Service
public class UserServiceImple implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encodePassword;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;



    private static final Logger logger = LoggerFactory.getLogger(UserServiceImple.class);

    @Override
    public ResponseDTO userRegister(RegisterReqDTO registerReqDTO){

        logger.info("registration of user started");


        User alreadyRegistered= userRepository.findByEmail(registerReqDTO.getEmail());
        if (alreadyRegistered != null){
            logger.info("User already here");
            throw new UserException("User already exists");
        }

        User user = new User();
        user.setName(registerReqDTO.getName());
        user.setEmail(registerReqDTO.getEmail());
        user.setPassword(encodePassword.encode(registerReqDTO.getPassword()));
        userRepository.save(user);

        logger.info("user registered successfully now");


        return  new ResponseDTO("User Registered",null);

    };

    @Override
    public ResponseDTO userLogin(LoginReqDTO loginReqDTO) {
        logger.info("user login started" );

        User user = userRepository.findByEmail(loginReqDTO.getEmail());

        if (user == null) {
           
            logger.info("User not found in login database");
            throw new UserException("User Not Found");
        }
        boolean isPasswordMatched = encodePassword.matches(loginReqDTO.getPassword(), user.getPassword());

        if ( ! isPasswordMatched ){
            logger.error("wrong password");
            throw new UserException("Your password is wrong");
        }
        String token = jwtUtil.generateToken(user.getEmail());

        logger.info("token generated" );
        redisService.saveToken(user.getEmail(),token);

        logger.info("user login successful");
        return new ResponseDTO("Welcome " + user.getName() , token);
    }

    public  ResponseDTO userLogout(String email){
        logger.info("user logout process started");

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        redisService.deleteToken(email);
        logger.info("logout user complete");

        return new ResponseDTO("User logut successfully" + user.getName() , email);


    }

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public ResponseDTO initiatePasswordReset(String email) {
        logger.info("Initiating password reset for email: " + email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }

        // Check if user already has a token, remove it
        Optional<PasswordResetToken> existingToken = tokenRepository.findByUser(user);
        existingToken.ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiry);
        tokenRepository.save(resetToken);

        // Send Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Fundoo Notes Password Reset Request");
        message.setText("Click the following link to reset your password: \n" 
            + frontendUrl + "/reset-password?token=" + token + "\n\n"
            + "This link will expire in 15 minutes.");
        
        try {
            mailSender.send(message);
        } catch (org.springframework.mail.MailException e) {
            logger.error("Failed to send email. SMTP host or configuration is incorrect: " + e.getMessage());
            throw new UserException("SMTP server connection refused. Ensure your mail server is running on port 1025 or configure a valid SMTP provider in application-dev.properties.");
        }

        return new ResponseDTO("Password reset email sent successfully", null);
    }

    @Override
    public ResponseDTO resetPassword(String token, String newPassword) {
        logger.info("Resetting password with token");
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new UserException("Invalid recovery token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new UserException("Recovery token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(encodePassword.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return new ResponseDTO("Password reset successful", user.getEmail());
    }

}

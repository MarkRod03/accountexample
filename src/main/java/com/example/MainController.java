
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import com.example.entities.UserRepository;
import com.example.entities.User;
import java.util.Base64;

@Controller
public class MainController {
   @Autowired
   UserRepository userRepository;

   //Secure Random Instance for Salt
   SecureRandom random = new SecureRandom();

   //Load login page
   @RequestMapping(value = "/")
   public String index() {
      return "index";
   }

   //Load create account page
   @RequestMapping(value = "/CreateAccount")
   public String Create() {
      return "CreateAccount";
   }

   //load user homepage
   @RequestMapping(value = "/UserHomePage")
   public String Home(@ModelAttribute("user") User user, RedirectAttributes ra) {
      User checkUser = userRepository.findByUsername(user.getUsername());
      //user is not found in database
      if(checkUser == null) {
         ra.addFlashAttribute("error", "Incorrect Username or Password");
         return "redirect:/";
      }
      //if username and password were entered correctly
      byte[] result = null;
      try {
         SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
         PBEKeySpec spec = new PBEKeySpec( user.getPassword().toCharArray(), Base64.getDecoder().decode(checkUser.getSalt()), 600000, 512 );
         SecretKey key = skf.generateSecret(spec);
         result = key.getEncoded( );
     } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
         throw new RuntimeException( e );
     }
      //get result and add to database
      String hashedPassword = Hex.encodeHexString(result);
      if(checkUser.getPassword().equals(hashedPassword)) {
         return "UserHomePage";
      }
         //if password was entered wrong
         ra.addFlashAttribute("error", "Incorrect Username or Password");
         return "redirect:/";
   }


   //submit form to create account
   @RequestMapping(value = "/CreateUser", method=RequestMethod.POST)
   public String CreateUser(@ModelAttribute("user") User user) {
      //Create Salt
      byte[] salt = new byte[64];
      random.nextBytes(salt);
      byte[] result = null;
      //hash password + salt using PBKDF2 with SHA512 (600,000 rounds)
      try {
         SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
         PBEKeySpec spec = new PBEKeySpec( user.getPassword().toCharArray(), salt, 600000, 512 );
         SecretKey key = skf.generateSecret(spec);
         result = key.getEncoded( );
     } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
         throw new RuntimeException( e );
     }
      //get result and add to database
      String hashedPassword = Hex.encodeHexString(result);
      User newuser = new User(user.getUsername(), hashedPassword, Base64.getEncoder().encodeToString(salt));
      userRepository.create(newuser);
      return "redirect:/";
   }
}
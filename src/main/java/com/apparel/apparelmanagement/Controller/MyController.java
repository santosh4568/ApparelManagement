package com.apparel.apparelmanagement.Controller;

import com.apparel.apparelmanagement.Model.Apparel;
import com.apparel.apparelmanagement.Model.User;
import com.apparel.apparelmanagement.Repository.ApparelRepo;
import com.apparel.apparelmanagement.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApparelRepo apparelRepo;

    boolean isLoggedIn = false;
    String whoIsLoggedIn = "";

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        if(isLoggedIn){
            mv.addObject("username", whoIsLoggedIn);
            mv.setViewName("Logged");
            return mv;
        }
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView loadLogin() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView validateLogin(@RequestParam String username, @RequestParam String password) {
        ModelAndView mv = new ModelAndView();
        if(username.equals("admin") && password.equals("admin")){
            List<Apparel> apparels = apparelRepo.findAll();
            mv.addObject("apparels", apparels);
            mv.setViewName("admin");
            return mv;
        }
        if (userRepo.findByUsernameAndPassword(username, password) != null) {
            isLoggedIn = true;
            whoIsLoggedIn = username;
            mv.addObject("username", whoIsLoggedIn);
            mv.setViewName("Logged");
        } else {
            mv.setViewName("error");
        }
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView loadRegister() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("register");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String phone, @RequestParam String username) {
        ModelAndView mv = new ModelAndView();
        if (userRepo.findByUsername(username) == null && userRepo.findByEmail(email) == null) {
            userRepo.save(new User(name, email, password, phone, username));
            mv.setViewName("login");
        } else {
            mv.setViewName("userexists");
        }
        return mv;
    }

    @GetMapping("/addApparel")
    public ModelAndView loadAddApparel() {
        ModelAndView mv = new ModelAndView();
        if(isLoggedIn){
            mv.setViewName("addApparel");
        }
        else{
            mv.setViewName("login");
        }
        return mv;
    }

    @PostMapping("/addApparel")
    public ModelAndView addApparel(@RequestParam String name, @RequestParam String type, @RequestParam String category) {
        ModelAndView mv = new ModelAndView();
        if (isLoggedIn) {
            apparelRepo.save(new Apparel(name, type, category, whoIsLoggedIn, "Pending"));
            mv.addObject("username", whoIsLoggedIn);
            mv.setViewName("Logged");
        } else {
            mv.setViewName("login");
        }
        return mv;
    }

    @GetMapping("/viewApparel")
    public ModelAndView viewApparel(@RequestParam(value = "search", required = false) String search) {
        ModelAndView mv = new ModelAndView();
        if (isLoggedIn) {
            List<Apparel> apparels;
            if (search != null && !search.isEmpty()) {
                apparels = apparelRepo.findByUsernameAndNameContaining(whoIsLoggedIn, search);
            } else {
                apparels = apparelRepo.findByUsername(whoIsLoggedIn);
            }
            mv.addObject("apparels", apparels);
            mv.setViewName("viewApparel");
        } else {
            mv.setViewName("login");
        }
        return mv;
}

    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mv = new ModelAndView();
        isLoggedIn = false;
        whoIsLoggedIn = "";
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/admin")
public ModelAndView adminPage() {
    ModelAndView mv = new ModelAndView();
    List<Apparel> apparels = apparelRepo.findAll();
    mv.addObject("apparels", apparels);
    mv.setViewName("admin");
    return mv;
}

@PostMapping("/admin/updateStatus")
public ModelAndView updateStatus(@RequestParam Long id, @RequestParam String status) {
    ModelAndView mv = new ModelAndView();
    Apparel apparel = apparelRepo.findById(id).orElse(null);
    if (apparel != null) {
        apparel.setStatus(status);
        apparelRepo.save(apparel);
    }
    mv.setViewName("redirect:/admin");
    return mv;
}

}

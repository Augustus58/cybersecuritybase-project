package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.CyberSecurityBaseProjectApplication;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

  @Autowired
  private SignupRepository signupRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @RequestMapping("*")
  public String defaultMapping() {
    return "redirect:/form";
  }

  @RequestMapping("/done")
  public String done(Model model, @RequestParam String id) {
    Signup s = signupRepository.findOne(new Long(id));
    model.addAttribute("name", s.getName());
    model.addAttribute("address", s.getAddress());
    return "done";
  }

  @RequestMapping(value = "/form", method = RequestMethod.GET)
  public String loadForm() {
    return "form";
  }

  @RequestMapping(value = "/formSave", method = RequestMethod.GET)
  public String submitForm(@RequestParam String name, @RequestParam String address, @RequestParam String redirect, RedirectAttributes redirectAttributes) {
    String sql = "INSERT INTO signup (name, address) VALUES ('" + name + "', '" + address + "')";
    jdbcTemplate.execute(sql);
    Long id = signupRepository.findByNameAndAddress(name, address).get(0).getId();
    redirectAttributes.addAttribute("id", id);
    return (redirect != null && !redirect.isEmpty() ? "redirect:" + redirect : "form");
  }

  @RequestMapping(value = "/signups", method = RequestMethod.GET)
  public String signups(Model model) {
    model.addAttribute("signups", signupRepository.findAll());
    return "signups";
  }

  @RequestMapping(value = "/signups/delete", method = RequestMethod.GET)
  public String deleteSignups(Model model) {
    signupRepository.deleteAll();
    return "redirect:/signups";
  }

  @RequestMapping(value = "/csrf", method = RequestMethod.GET)
  public String csrfMe() {
    return "csrf";
  }

}

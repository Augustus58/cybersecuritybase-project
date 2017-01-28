package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

  @Autowired
  private SignupRepository signupRepository;

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
    Signup s = signupRepository.save(new Signup(name, address));
    redirectAttributes.addAttribute("id", s.getId());
    return (redirect != null && !redirect.isEmpty() ? "redirect:" + redirect : "form");
  }

  @RequestMapping(value = "/signups", method = RequestMethod.GET)
  @ResponseBody
  public String signups(Model model) {
    String list = "";
    for (Signup s : signupRepository.findAll()) {
      list = list.concat(s.getName() + " " + s.getAddress() + "<br>");
    }
    return list;
  }

}

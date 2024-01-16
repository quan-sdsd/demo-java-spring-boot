package fpt.project.datn.event.event;

import fpt.project.datn.object.dto.req.UserReq;
import fpt.project.datn.object.entity.UserCode;
import fpt.project.datn.repository.UserCodeRepository;
import fpt.project.datn.repository.UserRepository;
import fpt.project.datn.utility.MailSender;
import fpt.project.datn.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class AccountVerificationEvent extends AbsApplicationEvent {

    public AccountVerificationEvent() {
        super();
    }

    @Override
    public void doProcess() {
        UserRepository userRepository = (UserRepository) getParam("userRepository");
        UserCodeRepository userCodeRepository = (UserCodeRepository) getParam("userCodeRepository");
        UserReq user = (UserReq) getParam("user");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("username", user.getUsername());
        attributes.put("code", Utility.getRanCode(999999));
        MailSender.sendMail(
               "VERIFICATION EMAIL",
               Utility.htmlTemplateReader("/templates/AccountConfirmation.html", attributes),
               user.getEmail());
        UserCode userCode = new UserCode();
        userCode.setCode(attributes.get("code"));
        userCode.setUser(userRepository.findUserByUsername(attributes.get("username")).get());
        userCode.setType("email-confirmation");
        userCodeRepository.save(userCode);
    }
}
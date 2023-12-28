package fpt.project.datn.event.event;

import fpt.project.datn.object.dto.req.UserReq;
import fpt.project.datn.utility.MailSender;
import fpt.project.datn.utility.Utility;

public class AccountVerificationEvent extends AbsApplicationEvent {
    public AccountVerificationEvent() {
        super();
    }

    @Override
    public void doProcess() {
       UserReq user = (UserReq) getParam("user");
       MailSender.sendMail(
               "VERIFICATION EMAIL",
               Utility.htmlTemplateReader("/templates/AccountConfirmation.html"),
               user.getEmail());
    }
}
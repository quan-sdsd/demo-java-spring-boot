package fpt.project.datn.service;

import fpt.project.datn.event.event.AccountVerificationEvent;
import fpt.project.datn.object.dto.req.UserReq;
import fpt.project.datn.object.entity.User;
import fpt.project.datn.repository.UserCodeRepository;
import fpt.project.datn.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbsGeneralCRUDService<User, UserReq, User, Integer>{
    private final BCryptPasswordEncoder encoder;
    private final UserCodeRepository userCodeRepository;

    public UserService(UserRepository repository, UserCodeRepository userCodeRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, ApplicationEventPublisher publisher) {
        super(repository, modelMapper, User.class, User.class, publisher);
        this.encoder = encoder;
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public void save(UserReq user) {
        user.setPassword(encoder.encode(user.getPassword()));
        super.save(user);
        new AccountVerificationEvent()
                .addParam("user", user)
                .addParam("userRepository", super.repository)
                .addParam("userCodeRepository", this.userCodeRepository)
                .trigger(publisher);
    }

    @Override
    protected Pageable getPageable(int page) {
        return PageRequest.of(page, 50);
    }

    @Override
    protected Integer getEId(Object id) {
        return Integer.parseInt((String) id);
    }
}

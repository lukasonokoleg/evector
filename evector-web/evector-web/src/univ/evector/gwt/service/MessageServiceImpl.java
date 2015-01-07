package univ.evector.gwt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.gwt.client.service.MessageService;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Override
    public Long countUnreadMessages() {

        return null;
    }

}

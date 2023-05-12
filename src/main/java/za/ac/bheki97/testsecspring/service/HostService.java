package za.ac.bheki97.testsecspring.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.bheki97.testsecspring.repository.EventRepo;
import za.ac.bheki97.testsecspring.repository.HostRepo;

@Service
public class HostService {

    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private HostRepo hostRepo;

}

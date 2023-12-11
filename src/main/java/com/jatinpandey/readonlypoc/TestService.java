package com.jatinpandey.readonlypoc;

import com.jatinpandey.readonlypoc.readOnlyPocTry2.CustomRoutingDataSource;
import com.jatinpandey.readonlypoc.readOnlyPocTry2.DatasourceContextHolder;
import com.jatinpandey.readonlypoc.readOnlyPocTry2.ReadOnlyOp;
import com.jatinpandey.readonlypoc.readOnlyPocTry2.TestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TestService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    public TestService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void performReadWriteOperation() {


        TestUser newUser = new TestUser();
        newUser.setUsername("Zubain");
        newUser.setEmail("Zubain.Pro@example.com");

        TestUser newUserViaRepo = new TestUser();
        newUserViaRepo.setUsername("Jatin");
        newUserViaRepo.setEmail("Jatin.Pro@example.com");

        this.testRepository.save(newUserViaRepo);
        entityManager.persist(newUser);
    }

    @ReadOnlyOp
    @Transactional(readOnly = true)
    public List<TestUser> performReadOnlyOperation() {
        log.info("performReadOnlyOperation");
        log.info(entityManager.toString());
        log.info(DatasourceContextHolder.getDatasourceType().toString());
        log.info("TO make log injection warning " + entityManager.toString());
        // read via repo

        List<TestUser> users = this.testRepository.findAll();
        log.info("users via repo: {}", users);

        return entityManager.createQuery("SELECT u FROM TestUser u", TestUser.class)
                .getResultList();
    }


}

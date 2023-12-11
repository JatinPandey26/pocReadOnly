package com.jatinpandey.readonlypoc.readOnlyPocTry2;

import com.jatinpandey.readonlypoc.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestUser,Integer> {
}

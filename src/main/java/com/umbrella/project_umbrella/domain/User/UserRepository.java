package com.umbrella.project_umbrella.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByNickName(String nickName); // Long id로 찾아오는 것으로 변환시켜놓기

}

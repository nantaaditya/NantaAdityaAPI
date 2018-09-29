package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.User;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.ChangePasswordCommandRequest;
import com.nantaaditya.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChangePasswordCommandImplTest {

  @InjectMocks
  private ChangePasswordCommandImpl command;

  @Mock
  private UserRepository userRepository;

  private ChangePasswordCommandRequest commandRequest;

  @Before
  public void setUp() {
    commandRequest = ChangePasswordCommandRequest.builder()
        .username("username")
        .oldPassword("oldPassword")
        .newPassword("newPassword")
        .build();
  }

  @Test
  public void test(){
    this.mockFind(User.builder().build());

    EmptyResponse emptyResponse = command.doExecute(commandRequest);

    this.verifyFind();
    this.verifySave();
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_error(){
    this.mockFind(null);
    try{
      EmptyResponse emptyResponse = command.doExecute(commandRequest);
    } catch (Exception e){
      this.verifyFind();
      throw e;
    }

  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  private void mockFind(User user){
    when(userRepository.findByUsernameAndPasswordAndFlagDeleteFalse(anyString(), anyString()))
        .thenReturn(user);
  }

  private void verifyFind(){
    verify(userRepository).findByUsernameAndPasswordAndFlagDeleteFalse(anyString(), anyString());
  }

  private void verifySave(){
    verify(userRepository).save(any(User.class));
  }
}

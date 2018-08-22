package com.nantaaditya.service.command.impl;
// @formatter:off
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Image;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.GetImageWebResponse;
import com.nantaaditya.repository.ImageRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner; /**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class GetImageCommandImplTest {

  @InjectMocks
  private GetImageCommandImpl command;

  @Mock
  private ImageRepository imageRepository;

  @Mock
  private MapperHelper mapperHelper;

  @Before
  public void setUp(){
    this.mockFindAll();
    this.mockToResponse();
  }

  @Test
  public void testDoExecute(){
    command.doExecute(EmptyRequest.getInstance());
  }

  @After
  public void tearDown(){
    this.verifyFindAll();
    this.verifyToResponse();
    verifyNoMoreInteractions(imageRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockFindAll(){
    when(imageRepository.findAll()).thenReturn(
        Arrays.asList(Image.builder()
            .name("name")
            .url("url")
            .build()));
  }

  private void verifyFindAll(){
    verify(imageRepository).findAll();
  }

  private void mockToResponse(){
    when(mapperHelper.mapToList(anyCollectionOf(Image.class), eq(GetImageWebResponse.class)))
        .thenReturn(generateWebResponse());
  }

  private void verifyToResponse(){
    verify(mapperHelper).mapToList(anyCollectionOf(Image.class), eq(GetImageWebResponse.class));
  }

  private List<GetImageWebResponse> generateWebResponse(){
    return Arrays.asList(
        GetImageWebResponse.builder()
            .id("1")
            .name("name")
            .url("url")
            .build());
  }
}

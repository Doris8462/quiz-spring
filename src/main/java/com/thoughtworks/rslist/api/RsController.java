package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RsController {
  private  List<RsEvent> rsList =initRsEvent();

  private List<RsEvent> initRsEvent() {
    List<RsEvent> result = new ArrayList<>();
    result.add(new RsEvent("第一条事件", "无分类"));
    result.add(new RsEvent("第二条事件", "无分类"));
    result.add(new RsEvent("第三条事件", "无分类"));
    return result;
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getList(@RequestParam(required = false) Integer start,
                               @RequestParam(required = false) Integer end) {
    if (null == start || null == end) {
      return rsList;
    }
    return rsList.subList(start - 1, end);
  }

  @PostMapping("/rs/add")
  public void addOneRsEvent(@RequestBody String rsEventString) throws JsonProcessingException
  {
    ObjectMapper objectMapper=new ObjectMapper();
    RsEvent rsEvent=objectMapper.readValue(rsEventString,RsEvent.class);
    rsList.add(rsEvent);
  }
}


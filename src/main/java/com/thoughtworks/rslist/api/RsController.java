package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

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
  @GetMapping("/rs/{index}")
  public RsEvent getOne(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getList(@RequestParam(required = false) Integer start,
                               @RequestParam(required = false) Integer end) {
    if (null == start || null == end) {
      return rsList;
    }
    return rsList.subList(start - 1, end);
  }

  @PostMapping("/rs/event")
  public void addOneEvent(@RequestBody String rsEventString) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RsEvent rsEvent = objectMapper.readValue(rsEventString, RsEvent.class);
    rsList.add(rsEvent);
  }
  @PostMapping("/rs/delete/{index}")
  public void deldteOneEvent(@PathVariable int index) throws JsonProcessingException {
    if(index<=rsList.size())
    rsList.remove(index-1);
  }
  @PostMapping("/rs/modify/{index}")
  public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
    if(rsEvent.getEventName()!=null) rsList.get(index - 1).setEventName(rsEvent.getEventName());
    if (rsEvent.getKeyWord() != null) rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
  }
}


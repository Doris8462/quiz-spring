package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private final List<RsEvent> rsList = init();

  private List<RsEvent> init() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类",
            new User("A",18,"male","A@qq.com","1234567890")));
    rsEvents.add(new RsEvent("第二条事件", "无分类",
            new User("B",28,"female","B@qq.com","1234567890")));
    rsEvents.add(new RsEvent("第三条事件", "无分类",
            new User("C",38,"male","C@qq.com","1234567890")));
    return rsEvents;
  }

  @GetMapping("/rs/list/{index}")
  public RsEvent getOneRsEvent(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEvent(@RequestParam(required = false) Integer start,
                                  @RequestParam(required = false) Integer end) {
    if (start == null || end == null) {
      return rsList;
    }
    return rsList.subList(start - 1, end);
  }

  @PostMapping("/rs/add")
  public void addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
  }

  @PostMapping("/rs/update/{index}")
  public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
    if(rsEvent.getEventName()!=null) rsList.get(index - 1).setEventName(rsEvent.getEventName());
    if (rsEvent.getKeyword() != null) rsList.get(index - 1).setKeyword(rsEvent.getKeyword());
  }

  @PostMapping("/rs/delete/{index}")
  public void deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
  }
}
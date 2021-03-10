package com.eomcs.util;

import java.util.List;

public class Request {
  private String command;
  private List<String> dataList;

  @Override
  public String toString() {
    return "Request [command=" + command + ", data=" + dataList + "]";
  }
  public String getCommand() {
    return command;
  }
  public void setCommand(String command) {
    this.command = command;
  }
  public List<String> getData() {
    return dataList;
  }
  public void setData(List<String> data) {
    this.dataList = data;
  }  
}
